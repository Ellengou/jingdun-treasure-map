package com.jd.core.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nt on 2015-06-10.
 */
public class MapUtils {

    private static final String HTTP_DELIMITER = "&";

    public static Map<String, String> convertValueToString(Map params){
        Map<String, String> tempMap = new HashMap<>();

        for(Object key : params.keySet()){
            Object value = params.get(key);
            if(value != null && value.getClass().isArray()) {
                tempMap.put(key.toString(), (String) Array.get(value, 0));
            } else if (value != null) {
                tempMap.put(key.toString(), value.toString());
            } else {
                tempMap.put(key.toString(), StringUtils.EMPTY);
            }
        }

        return tempMap;
    }

    public static String convertMapToHttpString(Map<String,String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        for(String key : map.keySet()) {
            String delimiter = (stringBuffer.length()>0) ? HTTP_DELIMITER : StringUtils.EMPTY;
            stringBuffer.append(delimiter).append(key).append("=").append(map.get(key));
        }
        return stringBuffer.toString();
    }

}
