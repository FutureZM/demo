package com.zhou.demo.demos.web.annotation;

import java.lang.annotation.*;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/10/23 上午9:20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshValue {
}