package com.zhou.demo.security;

import com.zhou.demo.util.HexUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;


/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 上午9:57
 */
public abstract class SM2KeyAbstract {

    public static String generateSharedSecret(String ownPrivateKey, String otherPublicKey) {
        // 创建ECPublicKeyParameters
        ECPublicKeyParameters publicKeyParameters = convertECPublicKeyParameters(otherPublicKey);

        // 构建ECPrivateKeyParameters
        ECPrivateKeyParameters ecPrivateKeyParameters = convertECPrivateKeyParameters(ownPrivateKey);

        //计算协商密钥
        ECDHBasicAgreement agreement = new ECDHBasicAgreement();
        agreement.init(ecPrivateKeyParameters);
        BigInteger sharedSecret = agreement.calculateAgreement(publicKeyParameters);
        return HexUtils.encodeHexStr(sharedSecret.toByteArray(), false);
    }

    /**
     * 辅助方法：根据私钥字节数组创建ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters convertECPrivateKeyParameters(String hexPrivateKey) {
        // 将16进制字符串转换为字节数组
        byte[] privateKeyBytes = HexUtils.decodeHex(hexPrivateKey);
        // 创建ECPrivateKeyParameters
        return new ECPrivateKeyParameters(new BigInteger(1, privateKeyBytes), getECDomainParameters());
    }

    /**
     * 辅助方法：根据公钥字节数组创建ECPublicKeyParameters
     */
    public static ECPublicKeyParameters convertECPublicKeyParameters(String otherHexPublicKey) {
        int len = otherHexPublicKey.length() / 2;
        String hexPublicKeyX = otherHexPublicKey.substring(0, len);
        String hexPublicKeyY = otherHexPublicKey.substring(len);

        // 将16进制字符串转换为字节数组
        byte[] publicKeyXBytes = HexUtils.decodeHex(hexPublicKeyX);
        byte[] publicKeyYBytes = HexUtils.decodeHex(hexPublicKeyY);
        final ECDomainParameters domainParameters = getECDomainParameters();

        // 构建ECPoint
        ECPoint point = domainParameters.getCurve().createPoint(
                new java.math.BigInteger(1, publicKeyXBytes),
                new java.math.BigInteger(1, publicKeyYBytes));

        // 创建ECPrivateKeyParameters
        return new ECPublicKeyParameters(point, domainParameters);
    }

    /**
     * 辅助方法：使用SM2的OID构建ECDomainParameters
     */
    private static ECDomainParameters getECDomainParameters() {
        // 使用SM2的OID
        ASN1ObjectIdentifier curveOID = new ASN1ObjectIdentifier("1.2.156.10197.1.301");
        X9ECParameters ecParameters = ECNamedCurveTable.getByOID(curveOID);

        // 构建ECDomainParameters
        return new ECDomainParameters(
                ecParameters.getCurve(),
                ecParameters.getG(),
                ecParameters.getN(),
                ecParameters.getH());
    }


}
