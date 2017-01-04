package com.jd.webkits.resolver;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jd.core.exceptions.XBusinessException;
import com.jd.core.exceptions.XEmptyRequestBodyException;
import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.exceptions.XRuntimeException;
import com.jd.core.output.Result;
import com.jd.core.utils.*;
import com.jd.webkits.context.XContext;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Set;

/**
 * Created by nt on 2015-07-11.
 * updated by Ellen 2016-0
 */
public class XExceptionResolver extends SimpleMappingExceptionResolver {

    private final String WEB_ERROR_LOG_PATTERN = ">>>web请求[%s]发生异常:%s";

    private final String JSON_ERROR_LOG_PATTERN = ">>>[HashCode=%s]请求[%s]发生异常:%s";

    private final SerializerFeature[] serializerFeatures = new SerializerFeature[]{
            SerializerFeature.QuoteFieldNames,
    };

    private String defaultPath;
    private Properties exceptionMappings;
    private Charset charset = Charset.forName("UTF-8");

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public void setExceptionMappings(Properties exceptionMappings) {
        this.exceptionMappings = exceptionMappings;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        boolean isJsonResponse = isJsonRequest(handler);
        if (isJsonResponse) {
            return jsonExceptionHandler(response, ex);
        }
        return generalExceptionHandler(request, response, ex);
    }

    /**
     * 处理非ajax异常
     */
    private ModelAndView generalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String errorLog = String.format(WEB_ERROR_LOG_PATTERN, request.getServletPath(), ex.getMessage());
        logger.error(errorLog, ex);
        Result result = getExceptionResult(response, ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        String name = ex.getClass().getName();
        String path = exceptionMappings.getProperty(name);
        String wrapperPath = StringUtils.isEmpty(path) ? defaultPath : path;
        mav.setViewName(wrapperPath);
        return mav;
    }

    /**
     * 处理ajax异常
     */
    public ModelAndView jsonExceptionHandler(HttpServletResponse response, Exception e) {
        ServletOutputStream stream = null;
        try {
            XContext context = XContext.getCurrentContext();
            try {
                String errorLog = String.format(JSON_ERROR_LOG_PATTERN, context.getRequestHashCode(), context.getServiceUrl(), e.getMessage());
                logger.error(errorLog, e);
            }catch (Exception er){
                logger.error(er.getStackTrace());
            }
            Result result = getExceptionResult(response, e);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            stream = response.getOutputStream();

            byte[] jsonBytes = JSON.toJSONBytes(result, serializerFeatures);

            if (context.isInited() && context.isOpenApiRequest()) {
                setSignToHeader(response, jsonBytes, context);
            }
            stream.write(jsonBytes);
        } catch (Exception e1) {
            //do nothing
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            XContext.clearCurrentContext();
        }
        return new ModelAndView();
    }

    /**
     * 将部分异常处理成BusinessException
     */
    private Result getExceptionResult(HttpServletResponse response, Exception e) {
        Exception ex = e;
        if (e instanceof MethodArgumentNotValidException) {
            ObjectError objectError = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0);
            String validationMsg = objectError.getDefaultMessage();
            ex = handleErrorMessage(validationMsg);
        } else if (e instanceof BindException) {
            ObjectError objectError = ((BindException) e).getBindingResult().getAllErrors().get(0);
            String validationMsg = objectError.getDefaultMessage();
            ex = handleErrorMessage(validationMsg);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            ex = XExceptionFactory.create("F_WEBKITS_COMMON_1004", exception.getMethod());
        } else if (e instanceof XEmptyRequestBodyException) {
            ex = XExceptionFactory.create("F_WEBKITS_COMMON_1001");
        } else if (e instanceof RpcException && !(e instanceof XRuntimeException)) {
            Throwable causeEx = e.getCause();
            if (causeEx instanceof ConstraintViolationException) {
                ConstraintViolationException ve = (ConstraintViolationException) e.getCause();
                Set<ConstraintViolation<?>> violations = ve.getConstraintViolations();
                String validationMsg = "";
                for (ConstraintViolation c : violations) {
                    validationMsg = c.getMessage();
                }
                ex = handleErrorMessage(validationMsg);
            }
        } else if (e instanceof JSONException) {
            ex = XExceptionFactory.create("F_WEBKITS_COMMON_1002", e.getMessage().replace("\"", ""));
        }
        return getFaultResponse(ex);
    }

    /**
     * 判断是否标有responseBody注解请求
     */
    private boolean isJsonRequest(Object handler) {
        HandlerMethod method = (HandlerMethod) handler;
        if (method == null)
            return false;
        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
        return body != null;
    }

    private XBusinessException handleErrorMessage(String validationMsg) {
        if (RegexUtils.isErrorCode(validationMsg)) {
            return XExceptionFactory.create(validationMsg);
        }

        String code = "F_WEBKITS_COMMON_1003";
        String errMsg = validationMsg;
        return XExceptionFactory.create(code, errMsg);
    }

    private Result getFaultResponse(Exception e) {
        if (e instanceof XBusinessException) {
            return ResponseUtils.getXBusinessResult((XBusinessException) e);
        } else if (e instanceof ValidationException) {
            if (e.getCause() instanceof XBusinessException) {
                return ResponseUtils.getXBusinessResult(((XBusinessException) e.getCause()));
            } else {
                return ResponseUtils.getFaultResult();
            }
        } else if (e instanceof Exception) {
            return ResponseUtils.getFaultResult();
        }
        return ResponseUtils.getUnknownResult();
    }

    /**
     * 设置签名相关的http头
     */
    private void setSignToHeader(HttpServletResponse response, byte[] jsonBytes, XContext context) {
        if (!context.isSkipCheckSignature()) {
            try {
                String sign = getResponseSign(jsonBytes, context);
                response.addHeader("sign", sign);
                response.addHeader("signType", context.getSignType().getValue());
            } catch (Exception e) {
                // Do nothing
            }
        }
    }

    /**
     * response返回的签名
     */
    private String getResponseSign(byte[] jsonBytes, XContext context) throws XBusinessException {
        if (jsonBytes == null) {
            throw XExceptionFactory.create("F_WEBKITS_RETURN_1001");
        }

        SignType type = context.getSignType();
        String appId = context.getParameterMap().get("appId");
        String jsonStr = "data=" + new String(jsonBytes, charset);

        return SignUtils.sign(jsonStr, type, "");
    }

}
