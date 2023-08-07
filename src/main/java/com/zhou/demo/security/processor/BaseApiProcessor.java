package com.zhou.demo.security.processor;

import com.zhou.demo.demos.web.config.ServerSM2Config;
import com.zhou.demo.security.SM2EncryptionAndSignature;
import com.zhou.demo.security.enums.ApiSecurityType;
import com.zhou.demo.security.exception.SignatureException;
import com.zhou.demo.security.exception.VerifySignatureException;
import com.zhou.demo.security.request.Base;
import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;
import org.bouncycastle.crypto.CryptoException;

/**
 * @author laurence
 */
abstract class BaseApiProcessor {

    /**
     * 验证签名, 验证通过后将响应中的加密数据解密。
     */
    static String parse(Base basePojo, ServerSM2Config serverConfig, ApiSecurityType type) {
        boolean verify = SM2EncryptionAndSignature.verify(serverConfig.getPublicKey(), ObjectUtils.sortByDictOrderAndConcat(basePojo, "&", "signature"), basePojo.getSignature());
        if (!verify) {
            throw new VerifySignatureException("验签失败");
        }

        switch (type) {
            case SM2_SIMPLE:
                return SM2EncryptionAndSignature.decrypt(serverConfig.getPrivateKey(), basePojo.getData());
            case SM2_WITH_SHARED_KEY:
                return SM2EncryptionAndSignature.decryptWithSharedKey(serverConfig.getAgreementKey(), basePojo.getData());
            default:
                return null;
        }

    }

    /**
     * 为apiRequest或者apiResponse对象赋值(加密+加签)
     */
    static <T> void assignment(Base basePojo, String clientPrivateKey, String serverPublicKey, T data) {

        String encryptData = SM2EncryptionAndSignature.encrypt(serverPublicKey, JsonUtils.toString(data));

        //赋值时间和加密数据字段
        basePojo.setTimestamp(String.valueOf(System.currentTimeMillis())).setData(encryptData);

        //拼接参数, 针对baseDTO中除signature之外的field以字典序号排列为a=av&b=bv&c=cv的形式, 然后对拼接后的结果进行签名
        String concatResult = ObjectUtils.sortByDictOrderAndConcat(basePojo, "&", "signature");
        //将签名写入dto对象
        try {
            basePojo.setSignature(SM2EncryptionAndSignature.sign(clientPrivateKey, concatResult));
        } catch (CryptoException e) {
            throw new SignatureException("签名出现异常", e);
        }
    }
}
