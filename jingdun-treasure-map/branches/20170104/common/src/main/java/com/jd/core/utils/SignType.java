package com.jd.core.utils;

/**
 * Created by Jintao on 2015/11/30.
 */
public enum SignType {

    MD5("MD5"), RSA("RSA"), DSA("DSA");

    private String value;

    SignType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static boolean isSignType(String name){
        for(SignType signType : SignType.values()){
            if(signType.getValue().equals(name)){
                return true;
            }
        }
        return false;
    }
}
