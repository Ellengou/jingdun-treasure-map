package com.jd.core.orm.cache.flush;


import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Jintao on 2015/5/28.
 */

@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class CacheFlushInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(CacheFlushInterceptor.class);

    private Map<String, CacheFlushHandler> handlerMap = new HashMap<String, CacheFlushHandler>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        //刷新缓存
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String id = mappedStatement.getId();
        CacheFlushHandler cacheFlushHandler = handlerMap.get(id);
        if (cacheFlushHandler != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            Object parameterObject = args[1];
            try {
                cacheFlushHandler.flush(configuration, parameterObject);
            } catch (Exception ex) {
                logger.error("刷新缓存出错", ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof CachingExecutor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public void setHandlerMap(Map<String, CacheFlushHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
