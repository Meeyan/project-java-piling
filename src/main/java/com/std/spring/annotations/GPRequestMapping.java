package com.std.spring.annotations;

import java.lang.annotation.*;

/**
 * controller注解
 *
 * @author zhaojy
 * @date 2018-03-28
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
