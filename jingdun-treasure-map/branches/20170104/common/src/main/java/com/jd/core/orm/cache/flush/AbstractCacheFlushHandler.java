package com.jd.core.orm.cache.flush;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;
import java.util.Map;

/**
 * Created by Jintao on 2015/5/27.
 */
public abstract class AbstractCacheFlushHandler implements CacheFlushHandler {
    /**
     * 创建一个CacheKey 用于删除缓存数据
     *
     * @param ms
     * @param parameterObject
     * @param boundSql
     * @return
     */
    private CacheKey createCacheKey(MappedStatement ms, Object parameterObject, BoundSql boundSql) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(ms.getId());
        cacheKey.update(RowBounds.NO_ROW_OFFSET);
        cacheKey.update(RowBounds.NO_ROW_LIMIT);
        cacheKey.update(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Configuration configuration = ms.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
        for (int i = 0; i < parameterMappings.size(); i++) { // mimic DefaultParameterHandler logic
            ParameterMapping parameterMapping = parameterMappings.get(i);
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                cacheKey.update(value);
            }
        }
        return cacheKey;
    }


    /**
     * get data for parameterObject
     *
     * @param parameterObject
     * @param key
     * @return
     */
    protected Object getParameter(Object parameterObject, String key) {
        if (parameterObject instanceof Map) {
            return ((Map) parameterObject).get(key);
        } else {
            return parameterObject;
        }
    }

    /**
     * @param configuration
     * @param id
     * @param parameterObject
     */
    protected void doFlush(Configuration configuration, String id, Object parameterObject) {
        MappedStatement mappedStatement = configuration.getMappedStatement(id);
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

        CacheKey cacheKey = createCacheKey(mappedStatement, parameterObject, boundSql);
        mappedStatement.getCache().removeObject(cacheKey);
    }
}

