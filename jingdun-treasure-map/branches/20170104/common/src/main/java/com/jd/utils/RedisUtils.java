package com.jd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by ellengou on 2016/11/17.
 */
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    private static RedisClient client = RedisClient.getInstance("DEFAULT_CACHED_POOL");

    public RedisUtils() {
    }

    public static void put(String key, Object value) {
        put(key, value, Integer.valueOf(3600));
    }

    public static void put(final String key, final Object value, final Integer seconds) {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                String json = JSON.toJSONString(value);
                jedis.set(key, json);
                jedis.expire(key, seconds.intValue());
                RedisUtils.logger.debug("setObject key={},value={}", key, json);
                return null;
            }
        });
    }

    public static <T> Object get(final String key, final Class<T> clazz) {
        return client.callRedis(new RedisHandler() {
            public T execute(Jedis jedis) {
                String value = jedis.get(key);
                return StringUtils.isEmpty(value)?null: JSONObject.parseObject(value, clazz);
            }
        });
    }

    public static Long remove(final String key) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                RedisUtils.logger.debug("removeObject:{},{}", key);
                return jedis.del(key);
            }
        });
    }

    public static Long ttl(final String key) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    public static void setExpire(final String key, final int seconds) {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                jedis.expire(key, seconds);
                RedisUtils.logger.debug("set key={} expire time={}s", key, Integer.valueOf(seconds));
                return null;
            }
        });
    }

    public static void setExpireAt(String key, Date date) {
        int expireTime = Integer.valueOf(String.valueOf(new Date().getTime()- date.getTime()));
        setExpire(key, expireTime);
        logger.debug("set key={} expire at datetime={}", key, date.toString());
    }

    public static void hput(final String key, final String field, final Object value) {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                jedis.hset(key.getBytes(), field.getBytes(), SerializeUtils.serialize(value));
                RedisUtils.logger.debug("set value to field={},key={}", field, key);
                return null;
            }
        });
    }

    public static <T> Object hget(final String key, final String field, Class<T> clazz) {
        return client.callRedis(new RedisHandler() {
            public T execute(Jedis jedis) {
                byte[] value = jedis.hget(key.getBytes(), field.getBytes());
                RedisUtils.logger.debug("hget value from key={},fiedl={}", key, field);
                return value != null && value.length != 0? (T) SerializeUtils.unserialize(value) :null;
            }
        });
    }

    public static void hremove(final String key, final String field) {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                jedis.hdel(key.getBytes(), new byte[][]{field.getBytes()});
                RedisUtils.logger.debug("gremove value from key={},field={}", key, field);
                return null;
            }
        });
    }

    public static <T> Collection<T> hgetAll(final String key, Class<T> clazz) {
        return (Collection)client.callRedis(new RedisHandler() {
            public Collection<T> execute(Jedis jedis) {
                ArrayList values = new ArrayList();
                Map map = jedis.hgetAll(key.getBytes());
                Iterator var4 = map.entrySet().iterator();

                while(var4.hasNext()) {
                    Map.Entry entry = (Map.Entry)var4.next();
                    if(entry.getKey() != null && ((byte[])entry.getKey()).length > 0 && entry.getValue() != null && ((byte[])entry.getValue()).length > 0) {
                        values.add(SerializeUtils.unserialize((byte[])entry.getValue()));
                    }
                }

                RedisUtils.logger.debug("hget all value from key={}", key);
                return values;
            }
        });
    }

    public static Long rpush(final String key, final String... value) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                Long result = jedis.rpush(key, value);
                RedisUtils.logger.debug("rpush value with key={},field={}", key, value);
                return result;
            }
        });
    }

    public static List<String> lrange(final String key, final int start, final int end) {
        return (List)client.callRedis(new RedisHandler() {
            public List<String> execute(Jedis jedis) {
                return jedis.lrange(key, (long)start, (long)end);
            }
        });
    }

    public static Long lrem(final String key, final String value, final int count) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                return jedis.lrem(key, (long)count, value);
            }
        });
    }

    public static Boolean exists(final String key) {
        return (Boolean)client.callRedis(new RedisHandler() {
            public Boolean execute(Jedis jedis) {
                RedisUtils.logger.debug("query exist key={}", key);
                return jedis.exists(key);
            }
        });
    }

    public static Long incr(final String key) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                RedisUtils.logger.debug("incrBy key={}", key);
                return jedis.incr(key);
            }
        });
    }

    public static Long incrBy(final String key, final long num) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                RedisUtils.logger.debug("incrBy key={},num={}", key, Long.valueOf(num));
                return jedis.incrBy(key, num);
            }
        });
    }

    public static Double incrByFloat(final String key, final double num) {
        return (Double)client.callRedis(new RedisHandler() {
            public Double execute(Jedis jedis) {
                RedisUtils.logger.debug("incrByFloat key={},num={}", key, Double.valueOf(num));
                return jedis.incrByFloat(key, num);
            }
        });
    }

    public static Long setnx(String key, Object value) {
        return setnx(key, value, Integer.valueOf(3600));
    }

    public static Long setnx(final String key, final Object value, final Integer seconds) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                String json = JSON.toJSONString(value);
                Long ret = jedis.setnx(key, json);
                jedis.expire(key, seconds.intValue());
                RedisUtils.logger.debug("setnx key={},value={}", key, json);
                return ret;
            }
        });
    }

    public static Long decr(final String key) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                RedisUtils.logger.debug("decr key={}", key);
                return jedis.decr(key);
            }
        });
    }

    public static Long decrBy(final String key, final long num) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                RedisUtils.logger.debug("decrBy key={},num={}", key, Long.valueOf(num));
                return jedis.decrBy(key, num);
            }
        });
    }

    public static byte[] get(final byte[] key) {
        return (byte[])client.callRedis(new RedisHandler() {
            public byte[] execute(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public static byte[] set(final byte[] key, final byte[] value, final Integer seconds) {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                jedis.set(key, value);
                jedis.expire(key, seconds.intValue());
                return value;
            }
        });
        return value;
    }

    public static Long del(final byte[] key) {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    public static Set<byte[]> keys(final String pattern) {
        return (Set)client.callRedis(new RedisHandler() {
            public Set<byte[]> execute(Jedis jedis) {
                return jedis.keys(pattern.getBytes());
            }
        });
    }

    public static void flushDB() {
        client.callRedis(new RedisHandler() {
            public Object execute(Jedis jedis) {
                RedisUtils.logger.debug("flushDB");
                jedis.flushDB();
                return null;
            }
        });
    }

    public static Long dbSize() {
        return (Long)client.callRedis(new RedisHandler() {
            public Long execute(Jedis jedis) {
                return jedis.dbSize();
            }
        });
    }
}
