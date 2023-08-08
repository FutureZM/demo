package com.zhou.demo.demos.web.db;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/8 上午10:11
 */

import com.zhou.demo.security.enums.ApiSecurityType;

public class AccessPair {
    private String appId;
    private String publicKey;
    private ApiSecurityType type;

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
}
