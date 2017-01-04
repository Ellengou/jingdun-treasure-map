package com.jd.core.multiple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ellengou on 16/8/23.
 */


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String name() default DataSource.DATASOURCE_COMMON;

    public static String DATASOURCE_COMMON = "dataSource";

    public static String DATASOURCE_BASE = "baseDataSource";

}