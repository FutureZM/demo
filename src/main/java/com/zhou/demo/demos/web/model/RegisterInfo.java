package com.zhou.demo.demos.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/11/8 上午10:34
 */
@Getter
@Setter
@Accessors(chain = true)
public class RegisterInfo {
    private Long id;
    private String name;
    private String phone;
    private Integer status;
}
