package com.zhou.demo.demos.web.result;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author laurence
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {

        return new Result<T>()
                .setCode(RespCodeEnum.SUCCESS.getCode())
                .setMessage(RespCodeEnum.SUCCESS.getMsg())
                .setData(data);
    }


    public static <T> Result<T> success(RespCodeEnum resp) {
        return new Result<T>()
                .setCode(resp.getCode())
                .setMessage(resp.getMsg());
    }

    public static <T> Result<T> success(RespCodeEnum resp, T data) {
        return new Result<T>()
                .setCode(resp.getCode())
                .setMessage(resp.getMsg())
                .setData(data);

    }

    public static <T> Result<T> success() {
        return new Result<T>()
                .setCode(RespCodeEnum.SUCCESS.getCode())
                .setMessage(RespCodeEnum.SUCCESS.getMsg());
    }


    public static <T> Result<T> fail() {
        return new Result<T>()
                .setCode(RespCodeEnum.FAIL.getCode())
                .setMessage(RespCodeEnum.FAIL.getMsg());
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>()
                .setCode(RespCodeEnum.FAIL.getCode())
                .setMessage(msg);
    }

    public static <T> Result<T> fail(RespCodeEnum resp) {
        return new Result<T>()
                .setCode(resp.getCode())
                .setMessage(resp.getMsg());
    }
}
