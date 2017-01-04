package com.jd.common.dialect;

/**
 * 
 * 类文件: MySql5Dialect
 * <p>
 * <p>
 * 类描述：MySQL数据库方言
 * <p>
 * 作     者： migrant
 * <p>
 * 日     期： 2015年9月8日
 * <p>
 * 时     间： 上午8:41:13
 * <p>
 */
public class MySql5Dialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return MySql5PageHepler.getCountString(sql);
    }
}
