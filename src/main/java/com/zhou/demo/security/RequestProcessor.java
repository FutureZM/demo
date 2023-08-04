package com.zhou.demo.security;

import com.zhou.demo.security.dto.DemoDto;
import com.zhou.demo.security.exception.SignatureException;
import com.zhou.demo.security.exception.VerifySignatureException;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;
import org.bouncycastle.crypto.CryptoException;

import java.util.*;

/**
 * ApiRequest 辅助处理类
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:55
 */
public class RequestProcessor {
    /**
     * ApiRequest build
     */
    public static <T> ApiRequest buildApiRequest(String appId, String clientPrivateKey, String serverPublicKey, T data) {
        String encryptData = SM2EncryptionAndSignature.encrypt(serverPublicKey, JsonUtils.toString(data));
        ApiRequest apiRequest = new ApiRequest().setAppId(appId)
                //替换掉uuid中的-
                .setNonce(UUID.randomUUID().toString().replace("-", ""))
                .setTimestamp(String.valueOf(System.currentTimeMillis()))
                .setData(encryptData);
        //拼接参数, 针对baseDTO中除signature之外的field以字典序号排列为a=av&b=bv&c=cv的形式, 然后对拼接后的结果进行签名
        String concatResult = ObjectUtils.sortByDictOrderAndConcat(apiRequest, "&", "signature");

        //将签名写入dto对象
        try {
            apiRequest.setSignature(SM2EncryptionAndSignature.sign(clientPrivateKey, concatResult));
        } catch (CryptoException e) {
            throw new SignatureException("签名出现异常", e);
        }
        return apiRequest;
    }


    /**
     * ApiRequest parse
     */
    public static <T> T parseApiRequest(ApiRequest apiRequest, String serverPrivateKey, String clientPublicKey, Class<T> beanType) {
        boolean verify = SM2EncryptionAndSignature.verify(clientPublicKey, ObjectUtils.sortByDictOrderAndConcat(apiRequest, "&", "signature"), apiRequest.getSignature());
        if (!verify) {
            throw new VerifySignatureException("验签失败");
        }

        String plainText = SM2EncryptionAndSignature.decrypt(serverPrivateKey, apiRequest.getData());
        return JsonUtils.parse(plainText, beanType);
    }

}