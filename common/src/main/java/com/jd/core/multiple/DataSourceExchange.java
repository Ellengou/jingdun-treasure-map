package com.jd.core.multiple;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by ellengou on 16/8/23.
 */
public class DataSourceExchange implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method,
                               Object[] args, Object target) throws Throwable {
        DataSourceHolder.clearDataSource();
    }

    @Override
    public void before(Method method, Object[] args, Object target)
            throws Throwable {
        //处理 TYPE 注解
        if (target.getClass().isAnnotationPresent(DataSource.class)) {
            DataSourceHolder.setDataSource(target.getClass().getAnnotation(DataSource.class).name());
            return;
        }
        /**
         * TYPE 示例
         * @Service
         * @DataSource(name = DataSource.DATASOURCE_ANALYZE)
         * public class OrderStatisticsServiceImpl implements OrderStatisticsService{}
         */

        //处理 METHOD 注解
        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        DataSource datasource = null;
        if (targetMethod == null) return;
        datasource = targetMethod.getAnnotation(DataSource.class);
        if (datasource == null) return;
        DataSourceHolder.setDataSource(datasource.name());

        /**
         * METHOD 示例
         * @Override
         * @DataSource(name = DataSource.DATASOURCE_ANALYZE)
         * public BigDecimal querySpecifiedPeriodOrderItemSum() {
         * return null;
         * }
         */
    }

}