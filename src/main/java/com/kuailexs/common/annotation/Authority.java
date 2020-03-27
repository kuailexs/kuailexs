package com.kuailexs.common.annotation;

import java.lang.annotation.*;

/**
 * 权限验证标记
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {

	AuthorityCode value() default AuthorityCode.ALL;
}