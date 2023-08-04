package com.zhou.demo.security;

import cn.hutool.core.util.HexUtil;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author laurence
 */
public class SM2EncryptionAndSignature extends Client {

    private static final String PRIVATE_KEY = "22943448E995AD5B0A8C84442570F286C956256A00658F2B3A60120974048C57";
    private static final String OWN_PUBLIC_KEY = "2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B";
    private static final String OTHER_PUBLIC_KEY = "3F8A58889AC44ACD1AE70A32726323FDF9AF839C347881570746AEB4FDF528626582F55985099727FD425CFB4791AC40CB196E9BD2363A5FCB394EA02975E0F2";
    private static final String OTHER_PRIVATE_KEY = "4ADDC747BEC7557EDCC15758F85A86E4328A4139B15FEE5529A2136353B5048C";


    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // 待加密的数据
        String plainText = "Hello, SM2 Encryption!";
        System.out.println("---> plainText      : " + plainText);

        // c加密数据
        String cipherText = encrypt(OTHER_PUBLIC_KEY, plainText);
        System.out.println("---> encrypt success: " + cipherText);

        // s解密数据
        String decryptedData = decrypt(OTHER_PRIVATE_KEY, cipherText);
        System.out.println("---> decrypt success: " + decryptedData);


        // 待签名的数据
        String dataToSign = "Data to be signed.";
        System.out.println("---> dataToSign     : " + dataToSign);


        String signedText = sign(PRIVATE_KEY, dataToSign);
        System.out.println("---> signed  success: " + signedText);

        //验证签名
        boolean verify = verify(OWN_PUBLIC_KEY, dataToSign, signedText);
        System.out.println("---> verify  success: " + verify);
    }


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
        return HexUtil.encodeHexStr(arrayOfBytes, false);
    }

    /**
     * SM2解密算法
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     */
    public static String decrypt(String privateKey, String cipherData) {
        byte[] cipherDataByte = HexUtil.decodeHex(cipherData);
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
        byte[] message = HexUtil.encodeHexStr(content).getBytes();

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

        return HexUtil.encodeHexStr(signBytes);
    }

    /**
     * 验证签名
     * @param publicKey     公钥
     * @param content       待签名内容
     * @param sign          签名值
     */
    public static boolean verify(String publicKey, String content, String sign) {
        //待签名内容
        byte[] message = HexUtil.encodeHexStr(content).getBytes();
        byte[] signData =  HexUtil.decodeHex(sign);

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
