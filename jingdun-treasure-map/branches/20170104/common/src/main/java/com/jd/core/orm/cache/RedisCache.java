package com.jd.core.orm.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jintao on 2015/5/26.
 */
public class RedisCache implements Cache{
    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteClassName,
    };

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;


    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:id={}", id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        Long size = RedisClient.getInstance(RedisClient.MYBATIS_CACHED_POOL).callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.hlen(id);
            }
        });

        return size.intValue();
    }

    @Override
    public void putObject(final Object key, final Object value) {
        RedisClient.getInstance(RedisClient.MYBATIS_CACHED_POOL).callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                String json = JSON.toJSONString(value, features);
                jedis.hset(id, String.valueOf(key.hashCode()), json);
                jedis.expire(id, 60 * 30);
                logger.debug("setObject key={},value={}", key, json);
                return null;
            }
        });
    }

    @Override
    public Object getObject(final Object key) {
        return RedisClient.getInstance(RedisClient.MYBATIS_CACHED_POOL).callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                logger.debug("getObject:{}", key);
                String value = jedis.hget(id, String.valueOf(key.hashCode()));
                return JSONObject.parse(value);
            }
        });
    }

    @Override
    public Object removeObject(final Object key) {
        return RedisClient.getInstance(RedisClient.MYBATIS_CACHED_POOL).callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                jedis.hdel(id, String.valueOf(key.hashCode()));
                logger.debug("removeObject:{},{}", id, key);
                return null;
            }
        });
    }

    @Override
    public void clear() {
        RedisClient.getInstance(RedisClient.MYBATIS_CACHED_POOL).callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                logger.debug("clear:{}", id);
                return jedis.del(id);
            }
        });
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
