package com.jd.common.dialect;

import org.apache.ibatis.session.Configuration;

/**
 * 
 * 类文件: DialectFactory
 * <p>
 * <p>
 * 类描述：数据库方言工厂,产生方言对象
 * <p>
 * 作     者： migrant
 * <p>
 * 日     期： 2015年9月8日
 * <p>
 * 时     间： 上午8:40:29
 * <p>
 */
public class DialectFactory {

    public static String dialectClass = null;

    public static Dialect buildDialect(Configuration configuration) {
        if (dialectClass == null) {
            synchronized (DialectFactory.class) {
                if (dialectClass == null) {
                    dialectClass = configuration.getVariables().getProperty("dialectClass");
                }
            }
        }
        Dialect dialect = null;
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("请检查 mybatis-config.xml 中  dialectClass 是否配置正确?");
        }
        return dialect;
    }
}
