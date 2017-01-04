package com.jd.core.orm.cache.flush;

import org.apache.ibatis.session.Configuration;

/**
 * Created by Jintao on 2015/5/27.
 */
public interface CacheFlushHandler {
    void flush(Configuration configuration, Object parameterObject);
}
