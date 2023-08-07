package com.zhou.demo.demos.web.config;

import com.zhou.demo.security.SM2EncryptionAndSignature;
import com.zhou.demo.security.enums.ApiSecurityType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "demo.server")
public class ServerSM2Config {
    private String publicKey;
    private String privateKey;
    private String clientPublicKey;
    private String agreementKey;
    private ApiSecurityType apiSecurityType;

    public String getPublicKey() {
        return publicKey;
    }

    public ServerSM2Config setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public ServerSM2Config setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public ServerSM2Config setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
        return this;
    }

    public String getAgreementKey() {
        if (agreementKey != null) {
            return agreementKey;
        } else {
            this.agreementKey = SM2EncryptionAndSignature.generateSharedSecret(this.privateKey, this.clientPublicKey);
        }
        return agreementKey;
    }

    public ServerSM2Config setAgreementKey(String agreementKey) {
        this.agreementKey = agreementKey;
        return this;
    }

    public ApiSecurityType getApiSecurityType() {
        return apiSecurityType;
    }

    public ServerSM2Config setApiSecurityType(ApiSecurityType apiSecurityType) {
        this.apiSecurityType = apiSecurityType;
        return this;
    }
}
