package com.zhou.demo.security;

/**
 * 三方对接的基础DTO对象
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:26
 */
public class BaseDTO {
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 当前时间戳
     */
    private String timestamp;

    /**
     * 加密后数据
     */
    private String data;

    /**
     * 签名: 对appId, nonce, timestamp, data 按照字典序排序后, 使用自身私钥进行签名并赋值
     */
    private String signature;

    public String getAppId() {
        return appId;
    }

    public BaseDTO setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public BaseDTO setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BaseDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getData() {
        return data;
    }

    public BaseDTO setData(String data) {
        this.data = data;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public BaseDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "appId='" + appId + '\'' +
                ", nonce='" + nonce + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data='" + data + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
