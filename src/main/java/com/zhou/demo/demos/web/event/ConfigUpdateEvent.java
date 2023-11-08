package com.zhou.demo.demos.web.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/10/23 上午9:29
 */
public class ConfigUpdateEvent extends ApplicationEvent {
    public String key;

    public ConfigUpdateEvent(Object source, String key) {
        super(source);
        this.key = key;
    }

}