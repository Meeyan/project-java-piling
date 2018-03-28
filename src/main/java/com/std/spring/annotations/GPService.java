package com.std.spring.annotations;

import java.lang.annotation.*;

/**
 * controller注解
 *
 * @author zhaojy
 * @create-time 2018-03-28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
