package com.jd.utils;

import com.whalin.MemCached.MemCachedClient;

public class MemcachedClientApp {

    public static void main(String[] args) {
        MemCachedClient memCachedClient = MemcachedClientFactory.getInstance();

        //  小于1000的值，除以1000以后都是0，即永不过期（最大过期时间是一个月）
        boolean success = memCachedClient.set("123", "1hh3");//  十秒后过期

        System.out.println(memCachedClient.get("123"));
    }
}
