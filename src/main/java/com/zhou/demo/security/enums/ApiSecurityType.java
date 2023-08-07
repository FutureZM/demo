package com.zhou.demo.security.enums;

/**
 * @author laurence
 */

public enum ApiSecurityType {

    /**
     * 1. 基于SM2的公私钥做加解密和加签、验签(default)
     * 2. 基于SM2的公私钥做加签、验签, 基于协商密钥做加密解密
     */
    SM2_SIMPLE("0100"),
    SM2_WITH_SHARED_KEY("0200"),
    ;

    private String code;


    ApiSecurityType(String code) {
        this.code = code;
    }
}
