package com.zhou.demo.security;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

public class SM2KeyExchangeExample {


    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 初始化SM2参数
        ECKeyPairGenerator generator = new ECKeyPairGenerator();

        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(SMConst.DOMAIN_PARAMETERS, new SecureRandom());
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
