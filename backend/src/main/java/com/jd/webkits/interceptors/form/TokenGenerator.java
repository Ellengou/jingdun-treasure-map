package com.jd.webkits.interceptors.form;

import com.jd.core.utils.RandomStringUtils;
import com.jd.core.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 令牌生成及验证器
 * Created by Ellen on 2015/7/8.
 */
public class TokenGenerator {

    /**
     * 保存token值的默认命名空间
     */
    public static final String TOKEN_NAMESPACE = "jd.tokens";

    /**
     * 持有token名称的字段名
     */
    public static final String TOKEN_KEY_FIELD = "tokenKey";
    public static final String TOKEN_VALUE_FIELD = "token";

    /**
     * 日志输出
     */
    private static final Logger logger = LoggerFactory.getLogger(TokenGenerator.class);

    /**
     * 生成重复提交token
     *
     * @param request
     * @return
     */
    public static DuplicateSubmissionToken generate(HttpServletRequest request) {
        return generate(request, generateGUID());
    }

    private static DuplicateSubmissionToken generate(HttpServletRequest request, String key) {
        DuplicateSubmissionToken token = new DuplicateSubmissionToken();
        token.setKey(key);
        token.setValue(generateGUID());
        setRedisCacheToken(request, token);

        return token;
    }

    /**
     * token写入request,redis中
     *
     * @param request
     * @param token
     */
    private static void setRedisCacheToken(HttpServletRequest request, DuplicateSubmissionToken token) {
        String fullTokenName = buildTokenAttributeName(token.getKey());
        /** token 信息放到redis*/
        RedisUtils.put(fullTokenName, token.getValue(),60*30);

        /** 请求信息写入到request中*/
        request.setAttribute(TOKEN_KEY_FIELD, token.getKey());
        request.setAttribute(TOKEN_VALUE_FIELD, token.getValue());
    }

    /**
     * 构建一个基于token名字的带有命名空间为前缀的token名字
     *
     * @param key
     * @return 带命名空间的token名字
     */
    private static String buildTokenAttributeName(String key) {
        return TOKEN_NAMESPACE + "." + key;
    }

    /**
     * 从请求域中获取给定token名字的token值
     *
     * @param request
     * @return
     */
    private static String getTokenValue(HttpServletRequest request) {
        Map params = request.getParameterMap();

        if (!params.containsKey(TOKEN_VALUE_FIELD)) {
            logger.warn("Could not find token value in params.");
            return null;
        }

        String[] tokens = (String[]) params.get(TOKEN_VALUE_FIELD);
        String token;

        if (tokens.length < 1) {
            logger.warn("Could not find token mapped to token value ");
            return null;
        }

        token = tokens[0];
        return token;
    }

    /**
     * 从请求参数中获取token的key
     *
     * @param request
     * @return
     */
    private static String getTokenKey(HttpServletRequest request) {
        Map params = request.getParameterMap();

        if (!params.containsKey(TOKEN_KEY_FIELD)) {
            logger.warn("Could not find token name in params.");
            return null;
        }

        String[] tokenNames = (String[]) params.get(TOKEN_KEY_FIELD);
        String tokenName;

        if (tokenNames.length < 1) {
            logger.warn("Got a null or empty token key.");
            return null;
        }

        tokenName = tokenNames[0];
        return tokenName;
    }

    /**
     * 验证当前请求参数中的token是否合法，如果合法的token出现就会删除它，它不会再次成功合法的token
     *
     * @param request
     * @return
     */
    protected static boolean validToken(HttpServletRequest request) {
        String tokenName = getTokenKey(request);

        if (tokenName == null) {
            logger.debug("no token name found -> Invalid token ");
            return false;
        }

        String token = getTokenValue(request);

        if (token == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("no token found for token name " + tokenName + " -> Invalid token ");
            }
            return false;
        }

        String tokenCacheName = buildTokenAttributeName(tokenName);
        String cacheToken = RedisUtils.get(tokenCacheName, String.class);

        if (!token.equals(cacheToken)) {
            logger.warn("jd.internal.invalid.token Form token " + token + " does not match the session token " + cacheToken + ".");
            return false;
        }

        /**删除redis中token */
        RedisUtils.remove(tokenCacheName);
        request.removeAttribute(TOKEN_KEY_FIELD);
        request.removeAttribute(TOKEN_VALUE_FIELD);
        return true;
    }


    /**
     * 生成token随机名称
     */
    private static String generateGUID() {
        return RandomStringUtils.getUUID();
    }
}