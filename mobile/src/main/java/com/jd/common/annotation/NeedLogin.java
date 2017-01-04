package com.jd.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类MustLogin.java的实现描述：必须登录才能调用的方法
 * 
 * @author Starty 2015年9月1日 下午6:35:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {

    boolean isLogin() default true;

}
