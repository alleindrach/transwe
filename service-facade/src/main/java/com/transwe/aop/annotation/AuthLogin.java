package com.transwe.aop.annotation;

import java.lang.annotation.*;

/**
 * 申明用户认证登录的注解
 * @author Administrator
 *
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthLogin {

}
