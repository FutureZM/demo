package com.zhou.demo.security;

import cn.hutool.core.util.HexUtil;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.security.SecureRandom;


/**
 * todo: 签名逻辑，加密逻辑，验签逻辑，解密逻辑
 *
 * @author laurence
 */
public class SM2KeyPairGenerateUtil {

    public static void main(String[] args) {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();

        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(SMConst.DOMAIN_PARAMETERS, new SecureRandom());
        generator.init(keyGenParams);
        keyPairGenerate(generator);
    }

    public static void keyPairGenerate(ECKeyPairGenerator generator) {
        // 生成客户端的密钥对
        AsymmetricCipherKeyPair clientKeyPair = generator.generateKeyPair();
        ECPrivateKeyParameters clientPrivateKey = (ECPrivateKeyParameters) clientKeyPair.getPrivate();
        ECPublicKeyParameters clientPublicKey = (ECPublicKeyParameters) clientKeyPair.getPublic();

        System.out.println(HexUtil.encodeHex(clientPrivateKey.getD().toByteArray(), false));
        System.out.println(new String(HexUtil.encodeHex(clientPublicKey.getQ().getAffineXCoord().getEncoded(), false))
                + new String(HexUtil.encodeHex(clientPublicKey.getQ().getAffineYCoord().getEncoded(), false)));
    }
}