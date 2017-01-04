package com.jd.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.core.orm.cache.RedisClient;
import com.jd.core.orm.cache.RedisHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by nt on 2015-06-16.
 */
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private static RedisClient client = RedisClient.getInstance(RedisClient.DEFAULT_CACHED_POOL);


    /**
     * put object to redis,key exist by default time(3h)
     *
     * @param key
     * @param value
     */
    public static void put(final String key, final Object value) {
        put(key, value, 60 * 60);
    }

    /**
     * put object to redis,key exist by set time
     *
     * @param key
     * @param value
     */
    public static void put(final String key, final Object value, final Integer seconds) {

        client.callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                String json = JSON.toJSONString(value);
                jedis.set(key, json);
                jedis.expire(key, seconds);
                logger.debug("setObject key={},value={}", key, json);
                return null;
            }
        });

    }

    /**
     * get object by id and key
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(final String key, final Class<T> clazz) {
        return client.callRedis(new RedisHandler<T>() {
            @Override
            public T execute(Jedis jedis) {
                String value = jedis.get(key);
                return StringUtils.isEmpty(value) ? null : JSONObject.parseObject(value, clazz);
            }
        });
    }

    /**
     * remove object by key
     *
     * @param key
     * @return
     */
    public static Long remove(final String key) {
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                logger.debug("removeObject:{},{}", key);
                return jedis.del(key);
            }
        });
    }

    /**
     * get key exist time
     *
     * @param key
     * @return
     */
    public static Long ttl(final String key) {
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    /**
     * 设置此key的生存时间，单位秒(s)
     */
    public static void setExpire(final String key, final int seconds) {
        client.callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                jedis.expire(key, seconds);
                logger.debug("set key={} expire time={}s", key, seconds);
                return null;
            }
        });
    }

    /**
     * 设置过期的日期时间。
     * @param key 键
     * @param date 过期的日期与时间
     */
    public static void setExpireAt(final String key, final Date date) {
        final long millis = DateUtils.getMillis(date);
        client.callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                jedis.expireAt(key, millis);
                logger.debug("set key={} expire at datetime={}", key, date.toString());
                return null;
            }
        });
    }


    //******************以下保存对象采用序列化机制,json形式有很多限制********************

    /**
     * 在指定key的置顶域中设置value
     */
    public static void hput(final String key, final String field, final Object value) {
        client.callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                jedis.hset(key.getBytes(), field.getBytes(), SerializeUtils.serialize(value));
                logger.debug("set value to field={},key={}", field, key);
                return null;
            }
        });
    }

    /**
     * 获取指定key中的指定field中的value
     */
    public static <T> T hget(final String key, final String field, final Class<T> clazz) {
        return client.callRedis(new RedisHandler<T>() {
            @Override
            public T execute(Jedis jedis) {
                byte[] value = jedis.hget(key.getBytes(), field.getBytes());
                logger.debug("hget value from key={},fiedl={}", key, field);
                if (value == null || value.length == 0) {
                    return null;
                }
                return (T) SerializeUtils.unserialize(value);
            }
        });
    }

    /**
     * 移除指定key中的指定field
     */
    public static void hremove(final String key, final String field) {
        client.callRedis(new RedisHandler<Object>() {
            @Override
            public Object execute(Jedis jedis) {
                jedis.hdel(key.getBytes(), field.getBytes());
                logger.debug("gremove value from key={},field={}", key, field);
                return null;
            }
        });
    }

    /**
     * 获取key集合中所有相关的value
     */
    public static <T> Collection<T> hgetAll(final String key, final Class<T> clazz) {
        return client.callRedis(new RedisHandler<Collection<T>>() {
            @Override
            public Collection<T> execute(Jedis jedis) {
                List<T> values = new ArrayList<T>();
                Map<byte[], byte[]> map = jedis.hgetAll(key.getBytes());
                for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
                    if (entry.getKey() != null && entry.getKey().length > 0 && entry.getValue() != null && entry.getValue().length > 0) {
                        values.add((T) SerializeUtils.unserialize(entry.getValue()));
                    }
                }
                logger.debug("hget all value from key={}", key);
                return values;
            }
        });
    }

    public static Long rpush(final String key, final String... value){
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                Long result = jedis.rpush(key, value);
                logger.debug("rpush value with key={},field={}", key, value);
                return result;
            }
        });
    }

    /**
     * 获取rpush中的数据，start下标从0开始，end为-1时，表示最后一个元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(final String key, final int start, final int end) {
        return client.callRedis(new RedisHandler<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    /**
     * 删除rpush中的数据，其中count为0的话，删除所有。假如为-2，则删除从后往前的2个value，为2则从前往后的2个value
     *
     * @param key
     * @param value
     * @param count
     * @return
     */
    public static Long lrem(final String key, final String value, final int count) {
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.lrem(key, count, value);
            }
        });
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public static Boolean exists(final String key) {
        return client.callRedis(new RedisHandler<Boolean>() {
            @Override
            public Boolean execute(Jedis jedis) {
                logger.debug("query exist key={}", key);
                return jedis.exists(key);
            }
        });
    }

    /**
     * 原子增加key的值+1，如果key不存在，则创建并赋值为1。如果存在值但不是integer类型的则会报错
     *
     * @param key 键
     * @return 返回原子+1后的数值
     */
    public static Long incr(final String key) {
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                logger.debug("incrBy key={}", key);
                return jedis.incr(key);
            }
        });
    }

    /**
     * 原子增加key的值+num
     * @param key 键
     * @param num 原子增加的数量
     * @return 返回原子+num后的数值
     */
    public static Long incrBy(final String key, final long num){
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                logger.debug("incrBy key={},num={}", key, num);
                return jedis.incrBy(key, num);
            }
        });
    }

    /**
     * 原子增加key的值+num
     * @param key 键
     * @param num 原子增加的数量
     * @return 返回原子+num后的数值
     */
    public static Double incrByFloat(final String key, final double num){
        return client.callRedis(new RedisHandler<Double>() {
            @Override
            public Double execute(Jedis jedis) {
                logger.debug("incrByFloat key={},num={}", key, num);
                return jedis.incrByFloat(key, num);
            }
        });
    }

    /**
     * 原子增加key的值-1
     *
     * @param key 键
     * @return 返回原子-1后的数值
     */
    public static Long decr(final String key) {
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                logger.debug("decr key={}", key);
                return jedis.decr(key);
            }
        });
    }

    /**
     * 原子增加key的值-num
     * @param key 键
     * @param num 原子增加的数量
     * @return 返回原子+num后的数值
     */
    public static Long decrBy(final String key, final long num){
        return client.callRedis(new RedisHandler<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                logger.debug("decrBy key={},num={}", key, num);
                return jedis.decrBy(key, num);
            }
        });
    }
}
