package com.jd.webkits.converters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jd.core.context.ApplicationContextHelper;
import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.output.Result;
import com.jd.core.utils.ResponseUtils;
import com.jd.core.utils.SignType;
import com.jd.core.utils.SignUtils;
import com.jd.core.utils.StringUtils;
import com.jd.webkits.context.XContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by Ellen on 2015/5/20.
 */
public class XMessageConverter extends AbstractHttpMessageConverter<Object> {

    private final String NORMAL_LOG_PATTERN = ">>>[HashCode=%s]请求,返回给[%s]数据:{%s}";
    private final String ERROR_LOG_PATTERN = ">>>[HashCode=%s]请求[%s]发生异常:%s";


    public final static Charset UTF8 = Charset.forName("UTF-8");
    private static final Logger logger = LoggerFactory.getLogger(XMessageConverter.class);

    private Charset charset = UTF8;

    private SerializerFeature[] serializerFeature;

    public XMessageConverter(String charset) {
        super(new MediaType("application", "json", Charset.forName(charset)), new MediaType("application", "*+json", Charset.forName(charset)));
        this.charset = Charset.forName(charset);
    }

    public XMessageConverter() {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return serializerFeature;
    }

    public void setFeatures(SerializerFeature... features) {
        this.serializerFeature = features;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String jsonBody = XContext.getCurrentContext().getPostJsonBodyStr();

        if (StringUtils.isEmpty(jsonBody)) {
            jsonBody = "{}";
        }

        return JSON.parseObject(jsonBody, clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        XContext context = XContext.getCurrentContext();
        Result result;

        try {
            logger.info(String.format(NORMAL_LOG_PATTERN, context.getRequestHashCode(), context.getSource(), JSON.toJSONString(obj)));
            result = ResponseUtils.getSuccessResult(obj);
        } catch (Exception ex) {
            result = ResponseUtils.getUnknownResult();
            String errorLog = String.format(ERROR_LOG_PATTERN, context.getRequestHashCode(), context.getServiceUrl(), ex.getMessage());
            logger.error(errorLog, ex);
        }

        byte[] jsonBytes = convertToJsonBytes(result);

        OutputStream out = outputMessage.getBody();
        out.write(jsonBytes);

    }

    private byte[] convertToJsonBytes(Object obj) {
        if (null == obj) {
            return "\"\"".getBytes(charset);
        } else if (obj instanceof String) {
            String value = (String) obj;
            if (value.equals(StringUtils.EMPTY))
                return "\"\"".getBytes(charset);
            else
                return value.getBytes(charset);
        } else {
            if (serializerFeature != null) {
                return JSON.toJSONBytes(obj, serializerFeature);
            } else {
                return JSON.toJSONBytes(obj);
            }
        }
    }

    /**
     * 设置签名相关的http头
     *
     * @param outputMessage
     * @param jsonBytes
     * @param context
     */
    private void setSignToHeader(HttpOutputMessage outputMessage, byte[] jsonBytes, XContext context) {
        try {
            if (!context.isSkipCheckSignature()) {
                String sign = getResponseSign(jsonBytes, context);
                outputMessage.getHeaders().add("sign", sign);
                outputMessage.getHeaders().add("signType", context.getSignType().getValue());
            }
        } catch (Exception e) {
            //do nothing
        }

    }

    /**
     * response返回的签名
     *
     * @return
     */
    private String getResponseSign(byte[] jsonBytes, XContext context) {
        if (jsonBytes == null) {
            throw XExceptionFactory.create("F_WEBKITS_RETURN_1001");
        }

        SignType type = context.getSignType();
        String appId = context.getParameterMap().get("appId");
        String jsonStr = "data=" + new String(jsonBytes, charset);

        return SignUtils.sign(jsonStr, type, "");
    }
}
