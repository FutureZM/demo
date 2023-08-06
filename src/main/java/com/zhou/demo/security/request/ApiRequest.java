package com.zhou.demo.security.request;

/**
 * 三方对接的基础DTO对象
 * 对appId, nonce, timestamp, data 按照字典序排序后, 使用自身私钥进行签名并赋值
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:26
 */
public class ApiRequest extends Base {
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 随机数
     */
    private String nonce;


    public String getAppId() {
        return appId;
    }

    public ApiRequest setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public ApiRequest setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "nonce='" + nonce + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data='" + data + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
