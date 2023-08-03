package com.zhou.demo.security;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.security.KeyPair;
import java.security.SecureRandom;


/**
 * todo: 签名逻辑，加密逻辑，验签逻辑，解密逻辑
 */

public class SM2KeyPairGenerateUtil {

    public static void main(String[] args) {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();

        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(SMConst.DOMAIN_PARAMETERS, new SecureRandom());
        generator.init(keyGenParams);
        keyPairGenerate(generator);
        demohutoolSM2();

    }

    public static void keyPairGenerate(ECKeyPairGenerator generator) {
        // 生成客户端的密钥对
        AsymmetricCipherKeyPair clientKeyPair = generator.generateKeyPair();
        ECPrivateKeyParameters clientPrivateKey = (ECPrivateKeyParameters) clientKeyPair.getPrivate();
        ECPublicKeyParameters clientPublicKey = (ECPublicKeyParameters) clientKeyPair.getPublic();

        System.out.println(HexUtil.encodeHex(clientPrivateKey.getD().toByteArray(), true));
        System.out.println(new String(HexUtil.encodeHex(clientPublicKey.getQ().getAffineXCoord().getEncoded(), false)) + new String(HexUtil.encodeHex(clientPublicKey.getQ().getAffineYCoord().getEncoded(), false)));
    }

    public static void demohutoolSM2() {
        String text = "我是一段测试aaaa";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        System.out.println(HexUtil.encodeHex(privateKey, false));
        System.out.println(HexUtil.encodeHex(publicKey, false));
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
    }
}

/**
 *
 * 008feab4e946f25c02effc666bc812fba539a8fdfce4c6713cdd493d1b499925f9
 * A4D74CE003AFEFA2408D21661BD4328DE9298CAC9626D2B5C3234225FAB4BFABCAA9EF343A6E2A10CCB8440F808E9991DA1A981E9A37C9F86E176AF4298E95B8
 *
 *
 *
 * */