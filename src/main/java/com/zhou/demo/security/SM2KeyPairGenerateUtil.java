package com.zhou.demo.security;

import com.zhou.demo.util.HexUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.security.SecureRandom;


/**
 * 生成密钥对
 * - 基于SM2推荐的曲线参数
 * - == 基于SM2的OID构建: 1.2.156.10197.1.301
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

    private static void keyPairGenerate(ECKeyPairGenerator generator) {
        // 生成客户端的密钥对
        AsymmetricCipherKeyPair clientKeyPair = generator.generateKeyPair();
        ECPrivateKeyParameters clientPrivateKey = (ECPrivateKeyParameters) clientKeyPair.getPrivate();
        ECPublicKeyParameters clientPublicKey = (ECPublicKeyParameters) clientKeyPair.getPublic();

        System.out.println(HexUtils.encodeHex(clientPrivateKey.getD().toByteArray(), false));
        System.out.println(new String(HexUtils.encodeHex(clientPublicKey.getQ().getAffineXCoord().getEncoded(), false))
                + new String(HexUtils.encodeHex(clientPublicKey.getQ().getAffineYCoord().getEncoded(), false)));
    }
}