package com.zhou.demo.com.zhou.demo.security;

import com.zhou.demo.security.SM2KeyAbstract;
import com.zhou.demo.util.HexUtils;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.Security;

import static com.zhou.demo.com.zhou.demo.security.ConstTest.*;


/**
 * SM2KeyAbstract Test
 */
class SM2KeyAbstractTest extends SM2KeyAbstract {

    @Test
    void generateSharedSecret() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String clientSharedSecret = generateSharedSecret(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY);
        String serverSharedSecret = generateSharedSecret(SERVER_PRIVATE_KEY, CLIENT_PUBLIC_KEY);
        Assert.isTrue(clientSharedSecret.equals(serverSharedSecret), "协商密钥失败");
        System.out.println("密钥协商单测完成");
        System.out.println("---> " + clientSharedSecret);

        SecretKey sharedKey = new SecretKeySpec(Hex.decode(clientSharedSecret), 0, 16, "SM4");

        byte[] messageBytes = "test 我爱中国".getBytes(StandardCharsets.UTF_8);
        // Encrypt the message using shared key
        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, sharedKey);
        byte[] encryptedMessage = cipher.doFinal(messageBytes);

        System.out.println(Hex.toHexString(encryptedMessage));

        // Decrypt the encrypted message using shared key
        Cipher decryptionCipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
        decryptionCipher.init(Cipher.DECRYPT_MODE, sharedKey);
        byte[] decryptedMessage = decryptionCipher.doFinal(encryptedMessage);

        System.out.println("Decrypted Message: " + new String(decryptedMessage));
    }

    @Test
    void print() {
        ECPrivateKeyParameters ecPrivateKeyParameters = convertECPrivateKeyParameters(CLIENT_PRIVATE_KEY);
        System.out.println(HexUtils.encodeHex(ecPrivateKeyParameters.getD().toByteArray(), false));

        ECPublicKeyParameters ecPublicKeyParameters = convertECPublicKeyParameters(SERVER_PUBLIC_KEY);
        System.out.println(new String(HexUtils.encodeHex(ecPublicKeyParameters.getQ().getAffineXCoord().getEncoded(), false))
                + new String(HexUtils.encodeHex(ecPublicKeyParameters.getQ().getAffineYCoord().getEncoded(), false)));
    }
}
