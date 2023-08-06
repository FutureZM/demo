package com.zhou.demo.security.request;

public class Base {
    /**
     * 当前时间戳
     */
    public String timestamp;

    /**
     * 加密后数据
     */
    public String data;

    /**
     * 签名
     */
    public String signature;

    public String getTimestamp() {
        return timestamp;
    }

    public Base setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getData() {
        return data;
    }

    public Base setData(String data) {
        this.data = data;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public Base setSignature(String signature) {
        this.signature = signature;
        return this;
    }
}
