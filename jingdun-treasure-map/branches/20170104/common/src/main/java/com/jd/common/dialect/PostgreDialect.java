package com.jd.common.dialect;

/**
 * 
 * 类文件: PostgreDialect
 * <p>
 * <p>
 * 类描述：Postgre 数据库 方言
 * <p>
 * 作     者： migrant
 * <p>
 * 日     期： 2015年9月8日
 * <p>
 * 时     间： 上午8:39:31
 * <p>
 */
public class PostgreDialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return PostgrePageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return PostgrePageHepler.getCountString(sql);
    }
}
