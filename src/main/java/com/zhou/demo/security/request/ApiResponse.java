package com.zhou.demo.security.request;

/**
 * 三方对接的基础DTO对象
 * 签名: timestamp, data 按照字典序排序后, 使用自身私钥进行签名并赋值
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:26
 */
public class ApiResponse extends Base {

    @Override
    public ApiResponse setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public ApiResponse setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "timestamp='" + timestamp + '\'' +
                ", data='" + data + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
