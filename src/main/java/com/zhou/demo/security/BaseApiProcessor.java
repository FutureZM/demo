package com.zhou.demo.security;

import com.zhou.demo.security.exception.VerifySignatureException;
import com.zhou.demo.security.request.Base;
import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;

public abstract class BaseApiProcessor {

    /**
     * ApiRequest parse
     */
    public static <T> T parse(Base basePojo, String privateKey, String publicKey, Class<T> beanType) {
        boolean verify = SM2EncryptionAndSignature.verify(publicKey, ObjectUtils.sortByDictOrderAndConcat(basePojo, "&", "signature"), basePojo.getSignature());
        if (!verify) {
            throw new VerifySignatureException("验签失败");
        }

        String plainText = SM2EncryptionAndSignature.decrypt(privateKey, basePojo.getData());
        return JsonUtils.parse(plainText, beanType);
    }
}
