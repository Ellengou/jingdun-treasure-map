package com.jd.base;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Comsys-lanny
 * @ClassName: BaseService
 * @Description: 业务层父类
 * @date 2015年5月6日 下午1:28:56
 */

public class BaseService {

    @Resource
    protected MemCachedClient memcachedClient;

    @Resource
    private SockIOPool sip;

    @Autowired
    protected TransactionTemplate transactionTemplate;
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    /* 通用线程池 */
    protected static ThreadPoolExecutor POOL;
    protected static ZSetOperations<String, Object> zSetOps;
    protected static HashOperations<String, String, Object> hashOps;

    public void init() {
        // 初始化线程池
        if (POOL == null) {
            int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2;
            int corePoolSize = maximumPoolSize;
            int capacity = maximumPoolSize * 10;
            POOL = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(capacity));
        }
        if (zSetOps == null) {
//            zSetOps = redisTemplate.opsForZSet();
        }
        if (hashOps == null) {
//            hashOps = redisTemplate.opsForHash();
        }
    }

    public ThreadPoolExecutor getPool() {
        return POOL;
    }

    /**
     * 线程池执行线程
     *
     * @param runnable 线程对象
     */
    public void poolExec(Runnable runnable) {
        init();
        if (runnable != null) {
            POOL.execute(runnable);
        }
    }

}
