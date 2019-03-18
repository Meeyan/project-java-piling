package com.std.spring.annotations;

import java.lang.annotation.*;

/**
 * controller注解
 *
 * @author zhaojy
 * @date 2018-03-28
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";
}
