package com.jd.common.dialect;

/**
 * 
 * 类文件: OracleDialect
 * <p>
 * <p>
 * 类描述：Oracle数据库方言实现类
 * <p>
 * 作     者： migrant
 * <p>
 * 日     期： 2015年9月8日
 * <p>
 * 时     间： 上午8:39:01
 * <p>
 */
public class OracleDialect extends Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {

        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        pagingSelect.append(sql);

        pagingSelect.append(" ) row_ ) where rownum_ > " + offset + " and rownum_ <= " + (offset + limit));

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
    }

    @Override
    public String getCountString(String sql) {
        // TODO Oracle分页查询
        return null;
    }
}
