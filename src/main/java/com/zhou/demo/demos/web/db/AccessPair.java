package com.zhou.demo.demos.web.db;

import com.zhou.demo.security.enums.ApiSecurityType;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/8 上午10:11
 */
public class AccessPair {
    private String appId;
    private String publicKey;
    private ApiSecurityType type;
    private String sharedKey;

    public String getAppId() {
        return appId;
    }

    public AccessPair setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public AccessPair setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public ApiSecurityType getType() {
        return type;
    }

    public AccessPair setType(ApiSecurityType type) {
        this.type = type;
        return this;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public AccessPair setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
        return this;
    }
}
