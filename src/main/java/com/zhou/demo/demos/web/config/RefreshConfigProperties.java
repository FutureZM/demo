package com.zhou.demo.demos.web.config;

import com.zhou.demo.demos.web.annotation.RefreshValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/10/23 上午9:16
 */
@Data
@Component
@RefreshValue
public class RefreshConfigProperties {
    @Value("${demo.dynamic.name}")
    private String name;
    @Value("${demo.dynamic.age}")
    private Integer age;
    @Value("hello ${demo.dynamic.other:test}")
    private String other;

}
