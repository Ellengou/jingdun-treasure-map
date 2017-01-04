package com.jd.core.orm.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by Jintao on 2015/5/26.
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
