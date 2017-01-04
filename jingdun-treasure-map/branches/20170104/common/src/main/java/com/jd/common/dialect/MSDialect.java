package com.jd.common.dialect;

/**
 * 
 * 类文件: MSDialect
 * <p>
 * <p>
 * 类描述：MSSQL 数据库方言
 * <p>
 * 作     者： migrant
 * <p>
 * 日     期： 2015年9月8日
 * <p>
 * 时     间： 上午8:40:44
 * <p>
 */
public class MSDialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MSPageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return MSPageHepler.getCountString(sql);
    }
}
