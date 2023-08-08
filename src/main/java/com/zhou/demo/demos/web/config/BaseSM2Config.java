package com.zhou.demo.demos.web.config;

import com.zhou.demo.security.SM2EncryptionAndSignature;
import com.zhou.demo.security.enums.ApiSecurityType;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/8 上午9:59
 */
public class BaseSM2Config extends SM2Config {

    private String appId;
    /**
     * 存放对方公钥
     */
    private String otherSidePublicKey;
    private String agreementKey;
    private ApiSecurityType apiSecurityType;

    public String getAppId() {
        return appId;
    }

    public BaseSM2Config setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getOtherSidePublicKey() {
        return otherSidePublicKey;
    }

    public BaseSM2Config setOtherSidePublicKey(String otherSidePublicKey) {
        this.otherSidePublicKey = otherSidePublicKey;
        return this;
    }

    public String getAgreementKey() {
        if (!ApiSecurityType.SM2_WITH_SHARED_KEY.equals(apiSecurityType)) {
            return null;
        }
        if (agreementKey != null) {
            return agreementKey;
        } else {
            this.agreementKey = SM2EncryptionAndSignature.generateSharedSecret(this.privateKey, this.otherSidePublicKey);
        }
        return agreementKey;
    }

    public BaseSM2Config setAgreementKey(String agreementKey) {
        this.agreementKey = agreementKey;
        return this;
    }

    public ApiSecurityType getApiSecurityType() {
        return apiSecurityType;
    }

    public BaseSM2Config setApiSecurityType(ApiSecurityType apiSecurityType) {
        this.apiSecurityType = apiSecurityType;
        return this;
    }
}
