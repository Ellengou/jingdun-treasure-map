package com.jd.web.token;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TokenInterceptor
 * 防止重复提交
 *
 * @author zhanghongyuan
 * @date 2015/8/25
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    //token 日志
    static final Logger log = Logger.getLogger(TokenInterceptor.class);

    /**
     * 保存token值的默认命名空间
     */
    public static final String TOKEN_NAMESPACE = "jd.tokens";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();

            Token annotation = method.getAnnotation(Token.class);

            if (annotation != null) {

                boolean needSaveSession = annotation.save();

                if (needSaveSession) {


                    try {
                        String uuid = UUID.randomUUID().toString();
                        String tokenName = buildTokenCacheAttributeName(uuid);
//                        redisService.set(tokenName, uuid, 36000);

                        request.setAttribute("token", uuid);

                    } catch (Exception e) {
                        log.error("token redis 设置出错！", e);
                        //session 管理
                        request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                    }

                }

                boolean needRemoveSession = annotation.remove();

                if (needRemoveSession) {

                    if (isRepeatSubmit(request)) {

                        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
                        String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
                        boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");

                        if (isAjax) {
                            Map<String, Object> json = new HashMap<String, Object>();
                            json.put("isRepeat", true);
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json; charset=utf-8");
                            PrintWriter out = null;
                            try {
                                out = response.getWriter();
                                out.append(JSON.toJSON(json).toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (out != null) {
                                    out.close();
                                }
                            }

                        } else {

                            request.getRequestDispatcher("/sys/token.html").forward(request, response);
                        }
                        return false;

                    }


                    try {
                        String token = getToken(request, "token");
//                        redisService.del(buildTokenCacheAttributeName(token));
                        request.setAttribute("token", null);
                    } catch (Exception e) {
                        log.error("token redis 删除出错！", e);
                        //session 管理
                        request.getSession(false).removeAttribute("token");
                    }

                }

            }

            return true;

        } else {

            return super.preHandle(request, response, handler);

        }

    }


    /**
     * 从请求域中获取给定token名字的token值
     *
     * @param tokenName
     * @return
     */
    public static String getToken(HttpServletRequest request, String tokenName) {
        if (tokenName == null) {
            return null;
        }
        Map params = request.getParameterMap();
        String[] tokens = (String[]) params.get(tokenName);
        String token;
        if ((tokens == null) || (tokens.length < 1)) {
            log.warn("Could not find token mapped to token name " + tokenName);
            return null;
        }

        token = tokens[0];
        return token;
    }


    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = "";
        try {
            String token = getToken(request, "token");
//            serverToken = (String) redisService.get(buildTokenCacheAttributeName(token));
        } catch (Exception e) {
            log.error("token redis 读取出错！", e);
            //session 管理
            serverToken = (String) request.getSession(false).getAttribute("token");
        }


        if (serverToken == null) {

            return true;

        }

        String clinetToken = request.getParameter("token");

        if (clinetToken == null) {

            return true;

        }

        if (!serverToken.equals(clinetToken)) {

            return true;

        }

        return false;

    }

    public static String buildTokenCacheAttributeName(String tokenName) {
        return TOKEN_NAMESPACE + "." + tokenName;
    }


}