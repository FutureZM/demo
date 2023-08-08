package com.zhou.demo.demos.web.config;

public class SM2Config {
    public String publicKey;
    public String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public SM2Config setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public SM2Config setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

}
