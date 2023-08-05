package com.zhou.demo.demos.web.result;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2021/12/13 下午6:28
 */

public enum RespCodeEnum {
    /**
     * success
     */
    SUCCESS(1200, "success"),
    PAYING(1000, "paying"),
    FAIL(1500, "fail");

    private Integer code;
    private String msg;

    RespCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
