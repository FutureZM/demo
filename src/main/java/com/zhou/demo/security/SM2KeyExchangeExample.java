package com.zhou.demo.security;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

public class SM2KeyExchangeExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 初始化SM2参数
        ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        SM2P256V1Curve CURVE = new SM2P256V1Curve();
        BigInteger SM2_ECC_N = CURVE.getOrder();
        BigInteger SM2_ECC_H = CURVE.getCofactor();
        BigInteger SM2_ECC_GX = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
        BigInteger SM2_ECC_GY = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
        ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);

        ECDomainParameters domainParameters = new ECDomainParameters(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);

        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(domainParameters, new SecureRandom());
        generator.init(keyGenParams);

        // 生成客户端的密钥对
        AsymmetricCipherKeyPair clientKeyPair = generator.generateKeyPair();
        ECPrivateKeyParameters clientPrivateKey = (ECPrivateKeyParameters) clientKeyPair.getPrivate();
        ECPublicKeyParameters clientPublicKey = (ECPublicKeyParameters) clientKeyPair.getPublic();

        // 生成服务器端的密钥对
        AsymmetricCipherKeyPair serverKeyPair = generator.generateKeyPair();
        ECPrivateKeyParameters serverPrivateKey = (ECPrivateKeyParameters) serverKeyPair.getPrivate();
        ECPublicKeyParameters serverPublicKey = (ECPublicKeyParameters) serverKeyPair.getPublic();

        // 在实际应用中，客户端将自己的公钥发送给服务器，服务器将自己的公钥发送给客户端
        // 这里为了演示方便，直接使用双方生成的公钥进行密钥协商
        // 在实际应用中，需要安全地将公钥交换出去，以免被中间人攻击

        // 客户端根据服务器的公钥生成共享密钥
        byte[] clientSharedSecret = generateSharedSecret(clientPrivateKey, serverPublicKey);

        // 服务器根据客户端的公钥生成共享密钥
        byte[] serverSharedSecret = generateSharedSecret(serverPrivateKey, clientPublicKey);

        // 检查双方生成的共享密钥是否相同
        System.out.println("Client shared secret: " + bytesToHex(clientSharedSecret));
        System.out.println("Server shared secret: " + bytesToHex(serverSharedSecret));
        System.out.println("Shared secrets are " + (Arrays.equals(clientSharedSecret, serverSharedSecret) ? "the same" : "different"));
    }

    private static byte[] generateSharedSecret(ECPrivateKeyParameters privateKey, ECPublicKeyParameters publicKey) {
        ECDHBasicAgreement agreement = new ECDHBasicAgreement();
        agreement.init(privateKey);
        BigInteger sharedSecret = agreement.calculateAgreement(publicKey);
        return sharedSecret.toByteArray();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}
