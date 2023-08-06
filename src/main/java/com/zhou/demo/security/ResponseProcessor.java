package com.zhou.demo.security;

import com.zhou.demo.security.exception.SignatureException;
import com.zhou.demo.security.request.ApiResponse;
import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;
import org.bouncycastle.crypto.CryptoException;

/**
 * ApiRequest 辅助处理类
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:55
 */
public class ResponseProcessor extends BaseApiProcessor {
    /**
     * ApiRequest build
     */
    public static <T> ApiResponse buildApiResponse(String serverPrivateKey, String clientPublicKey, T data) {
        String encryptData = SM2EncryptionAndSignature.encrypt(clientPublicKey, JsonUtils.toString(data));

        ApiResponse apiResponse = new ApiResponse()
                .setData(encryptData)
                .setTimestamp(String.valueOf(System.currentTimeMillis()));


        //拼接参数, 除signature之外的field以字典序号排列为a=av&b=bv&c=cv的形式, 然后对拼接后的结果进行签名
        String concatResult = ObjectUtils.sortByDictOrderAndConcat(apiResponse, "&", "signature");

        //将签名写入dto对象
        try {
            apiResponse.setSignature(SM2EncryptionAndSignature.sign(serverPrivateKey, concatResult));
        } catch (CryptoException e) {
            throw new SignatureException("签名出现异常", e);
        }
        return apiResponse;
    }


    /**
     * ApiRequest parse
     */
    public static <T> T parseApiResponse(ApiResponse apiResponse, String clientPrivateKey, String serverPublicKey, Class<T> beanType) {
        return parse(apiResponse, clientPrivateKey, serverPublicKey, beanType);
    }

}