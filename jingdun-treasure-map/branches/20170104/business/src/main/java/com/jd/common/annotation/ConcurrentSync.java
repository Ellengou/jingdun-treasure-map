package com.jd.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类ConcurrentSync.java的实现描述：并发同步注解
 * 
 * @author Starty 2015年9月9日 上午12:00:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrentSync {

    String redisKey() default "";

    int timeOut() default 60;

}
