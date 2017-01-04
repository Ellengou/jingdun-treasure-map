package com.jd.utils;

import redis.clients.jedis.Jedis;

/**
 * Created by ellengou on 2016/11/17.
 */
public interface RedisHandler<T> {
    /**
     * Redis执行方法
     *
     * @param jedis
     * @return
     */
    T execute(Jedis jedis);
}
