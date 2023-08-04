package com.zhou.demo.security;

import com.zhou.demo.util.HexUtils;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.util.Strings;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author laurence
 */
public class SM2EncryptionAndSignature extends SM2KeyAbstract {

    /**
     * SM2加密算法
     *
     * @param publicKey 公钥
     * @param data      数据
     */
    public static String encrypt(String publicKey, String data) {
        ECPublicKeyParameters publicKeyParameters = convertECPublicKeyParameters(publicKey);

        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        byte[] arrayOfBytes = null;
        try {
            byte[] in = data.getBytes(StandardCharsets.UTF_8);
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert arrayOfBytes != null;
        return HexUtils.encodeHexStr(arrayOfBytes, false);
    }

    /**
     * SM2解密算法
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     */
    public static String decrypt(String privateKey, String cipherData) {
        byte[] cipherDataByte = HexUtils.decodeHex(cipherData);
        ECPrivateKeyParameters privateKeyParameters = convertECPrivateKeyParameters(privateKey);

        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, privateKeyParameters);
        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            return new String(arrayOfBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥签名
     *
     * @param privateKey 私钥
     * @param content    待签名内容
     */
    public static String sign(String privateKey, String content) throws CryptoException {
        //待签名内容转为字节数组
        byte[] message = HexUtils.encodeHexStr(content).getBytes();

        ECPrivateKeyParameters privateKeyParameters = convertECPrivateKeyParameters(privateKey);

        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();

        //初始化签名实例,带上ID,国密的要求,ID默认值:1234567812345678
        try {
            sm2Signer.init(true, new ParametersWithID(new ParametersWithRandom(privateKeyParameters, SecureRandom.getInstance("SHA1PRNG")), Strings.toByteArray("1234567812345678")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sm2Signer.update(message, 0, message.length);
        //生成签名,签名分为两部分r和s,分别对应索引0和1的数组
        byte[] signBytes = sm2Signer.generateSignature();

        return HexUtils.encodeHexStr(signBytes);
    }

    /**
     * 验证签名
     * @param publicKey     公钥
     * @param content       待签名内容
     * @param sign          签名值
     */
    public static boolean verify(String publicKey, String content, String sign) {
        //待签名内容
        byte[] message = HexUtils.encodeHexStr(content).getBytes();
        byte[] signData =  HexUtils.decodeHex(sign);

        ECPublicKeyParameters publicKeyParameters = convertECPublicKeyParameters(publicKey);
        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();
        ParametersWithID parametersWithID = new ParametersWithID(publicKeyParameters, Strings.toByteArray("1234567812345678"));
        sm2Signer.init(false, parametersWithID);
        sm2Signer.update(message, 0, message.length);
        //验证签名结果
        return sm2Signer.verifySignature(signData);
    }

}
