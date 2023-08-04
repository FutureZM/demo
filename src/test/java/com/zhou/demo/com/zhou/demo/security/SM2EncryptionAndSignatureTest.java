package com.zhou.demo.com.zhou.demo.security;

import com.zhou.demo.security.SM2EncryptionAndSignature;
import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.Test;

import static com.zhou.demo.com.zhou.demo.security.ConstTest.*;

/**
 * SM2EncryptionAndSignature Test
 */
public class SM2EncryptionAndSignatureTest {

    /**
     * 测试公钥加密、私钥解密、私钥签名、公钥验签名
     */
    @Test
    void testEncryptDecryptSignVerify() throws CryptoException {
        // 待加密的数据
        String plainText = "Hello, SM2 Encryption!";
        System.out.println("---> plainText      : " + plainText);
        // client使用server的公钥加密数据
        String cipherText = SM2EncryptionAndSignature.encrypt(SERVER_PUBLIC_KEY, plainText);
        System.out.println("---> encrypt success: " + cipherText);

        // server使用自己的私钥解密数据
        String decryptedData = SM2EncryptionAndSignature.decrypt(SERVER_PRIVATE_KEY, cipherText);
        System.out.println("---> decrypt success: " + decryptedData);


        // 待签名的数据
        String dataToSign = "Data to be signed.";
        System.out.println("---> dataToSign     : " + dataToSign);

        //client使用自己的私钥进行签名
        String signedText = SM2EncryptionAndSignature.sign(CLIENT_PRIVATE_KEY, dataToSign);
        System.out.println("---> signed  success: " + signedText);

        //server使用client的公钥验证签名
        boolean verify = SM2EncryptionAndSignature.verify(CLIENT_PUBLIC_KEY, dataToSign, signedText);
        System.out.println("---> verify  success: " + verify);
    }
}
