package com.jd.core.orm.cache;

import com.jd.core.context.ApplicationContextHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jintao on 2015/5/26.
 */
public class RedisClient {
    /**
     * MyBatis 二级缓存池
     */
    public static final String DEFAULT_CACHED_POOL = "DEFAULT_CACHED_POOL";

    /**
     * 默认缓存池
     */
    public static final String MYBATIS_CACHED_POOL = "MY_BATIS_CACHED_POOL";

    /**
     * redis客户端连接池
     */
    private JedisPool jedisPool;

    private static Map<String, RedisClient> redisUtilsHashMap = new HashMap<String, RedisClient>();

    private RedisClient(String cachePool){
        jedisPool = ApplicationContextHelper.getContext().getBean(cachePool, JedisPool.class);
    }

    /**
     * 调用Redis
     *
     * @param handler
     * @return
     */
    public <T> T callRedis(RedisHandler<T> handler) {
        Jedis jedis = jedisPool.getResource();
        try {
            return (T) handler.execute(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 获得默认缓存池
     *
     * @return
     */
    public static RedisClient getInstance() {
        return getInstance(DEFAULT_CACHED_POOL);
    }

    /**
     * <pre>
     * 获取指定的缓存池,详细RedisUtils常量
     * </pre>
     *
     * @param cachedPool 指定的缓存连接池id
     * @return
     */
    public static RedisClient getInstance(String cachedPool) {
        RedisClient redisUtils = redisUtilsHashMap.get(cachedPool);
        if(redisUtils != null){
            return redisUtils;
        }
        if (redisUtils == null) {
            redisUtils = new RedisClient(cachedPool);
            synchronized (redisUtilsHashMap) {
                redisUtilsHashMap.put(cachedPool, redisUtils);
            }
        }
        return redisUtils;
    }

}
