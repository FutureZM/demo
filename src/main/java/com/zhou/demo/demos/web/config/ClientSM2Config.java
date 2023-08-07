package com.zhou.demo.demos.web.config;

import com.zhou.demo.security.SM2EncryptionAndSignature;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "demo.client")
public class ClientSM2Config {
    private String appId;
    private String publicKey;
    private String privateKey;
    private String serverPublicKey;
    private String agreementKey;

    public String getAppId() {
        return appId;
    }

    public ClientSM2Config setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public ClientSM2Config setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public ClientSM2Config setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }

    public ClientSM2Config setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
        return this;
    }

    public String getAgreementKey() {
        if (agreementKey != null) {
            return agreementKey;
        } else {
            this.agreementKey = SM2EncryptionAndSignature.generateSharedSecret(this.privateKey, this.serverPublicKey);
        }
        return agreementKey;
    }
}
