package com.zhou.demo.demos.web.db;

import com.google.common.collect.ImmutableMap;
import com.zhou.demo.security.SM2EncryptionAndSignature;
import com.zhou.demo.security.enums.ApiSecurityType;

import java.util.Map;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/8 上午9:46
 */
public class ApiAccessCtx {

    public static final Map<String, AccessPair> ACCESS_CTX_MAP = ImmutableMap.of(
            "c-appId-demo-SM2", new AccessPair()
                    .setAppId("c-appId-demo-SM2")
                    .setPublicKey("2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B")
                    .setType(ApiSecurityType.SM2_SIMPLE),
            "c-appId-demo-SM2-sharedKey", new AccessPair()
                    .setAppId("c-appId-demo-SM2-sharedKey")
                    .setPublicKey("2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B")
                    .setType(ApiSecurityType.SM2_WITH_SHARED_KEY)
    );

    static {
        String serverPrivateKey = "4ADDC747BEC7557EDCC15758F85A86E4328A4139B15FEE5529A2136353B5048C";
        ACCESS_CTX_MAP.forEach((appId, pair) -> {
            if (pair.getType().equals(ApiSecurityType.SM2_WITH_SHARED_KEY)) {
                pair.setSharedKey(SM2EncryptionAndSignature.generateSharedSecret(serverPrivateKey, pair.getPublicKey()));
            }
        });
    }
}
