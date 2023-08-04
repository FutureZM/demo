package com.zhou.demo.security;


/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:57
 */

public class DemoDto {
    public String name;
    public Integer age;

    public String getName() {
        return name;
    }

    public DemoDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public DemoDto setAge(Integer age) {
        this.age = age;
        return this;

    }
}
