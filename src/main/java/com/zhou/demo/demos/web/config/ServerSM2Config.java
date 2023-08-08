package com.zhou.demo.demos.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "demo.server")
public class ServerSM2Config extends SM2Config {

    @Override
    public ServerSM2Config setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    @Override
    public ServerSM2Config setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}
