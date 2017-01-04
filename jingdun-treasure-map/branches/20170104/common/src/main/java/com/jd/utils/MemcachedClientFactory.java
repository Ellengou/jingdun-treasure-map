package com.jd.utils;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedClientFactory {

    private static MemCachedClient memCachedClient;

    private MemcachedClientFactory(){
    }

    static {
        /* 初始化SockIOPool，管理memcached的连接池 */

        String[] servers = { "127.0.0.1:11211" };
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(2500);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        // 设置hash算法,使用CRC32兼容hash算法,查找cache服务器使用余数方法
        pool.setHashingAlg(2);
        pool.initialize();

        /* 建立MemcachedClient实例 */
        memCachedClient = new MemCachedClient();
    }

    public static MemCachedClient getInstance() {
        return memCachedClient;
    }
}
