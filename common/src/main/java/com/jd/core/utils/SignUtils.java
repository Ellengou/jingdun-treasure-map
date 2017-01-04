package com.jd.core.utils;

import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.exceptions.XSystemException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Jintao on 2015/6/3.
 */
public class SignUtils {

    /**
     * 对参数进行签名
     * @return
     */
    public static String sign(Map<String, String> requestMap, SignType type, String secret) {
        String signingStr = getSigningStr(requestMap);

        return sign(signingStr, type, secret);
    }

    public static String sign(String signingStr, SignType type, String secret){
        try {
            switch(type){
                case RSA:
                     return EnCryptors.RSA.sign(signingStr, secret);
                case DSA:
                    return EnCryptors.DSA.sign(signingStr, secret);
                case MD5:
                default:
                    signingStr = signingStr + "&secret=" + secret;
                    return EnCryptors.MD5.md5(signingStr);
            }

        } catch (Exception e){
            throw  new XSystemException("签名出现异常", e);
        }
    }

    private static String getSigningStr(Map<String, String> requestMap){
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.putAll(requestMap);
        return MapUtils.convertMapToHttpString(treeMap);
    }

    /**
     * 校验签名
     * @return
     */
    public static boolean validateSignature(String signature, Map<String, String> requestMap, SignType type, String secret) throws Exception{
        String signingStr = getSigningStr(requestMap);
        return  validateSignature(signature, signingStr, type, secret);
    }

    public static boolean validateSignature(String signature, String signingStr, SignType type, String secret) throws Exception{

        if(StringUtils.isEmpty(signature)){
            throw XExceptionFactory.create("F_WEBKITS_SIGN_1001");
        }

        switch(type){
            case RSA:
                return EnCryptors.RSA.verify(signingStr, signature, secret);
            case DSA:
                return EnCryptors.DSA.verify(signingStr, signature, secret);
            case MD5:
            default:
                String serverSignature = sign(signingStr, type, secret);
                return StringUtils.equalsIgnoreCase(signature, serverSignature);
        }
    }

}
