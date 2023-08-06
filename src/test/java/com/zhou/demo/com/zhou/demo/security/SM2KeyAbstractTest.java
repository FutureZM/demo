package com.zhou.demo.com.zhou.demo.security;

import com.zhou.demo.security.SM2KeyAbstract;
import com.zhou.demo.util.HexUtils;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.zhou.demo.com.zhou.demo.security.ConstTest.*;


/**
 * SM2KeyAbstract Test
 */
class SM2KeyAbstractTest extends SM2KeyAbstract {

    @Test
    void generateSharedSecret() {
        String clientSharedSecret = generateSharedSecret(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY);
        String serverSharedSecret = generateSharedSecret(SERVER_PRIVATE_KEY, CLIENT_PUBLIC_KEY);
        Assert.isTrue(clientSharedSecret.equals(serverSharedSecret), "协商密钥失败");
        System.out.println("密钥协商单测完成");
        System.out.println("---> " + clientSharedSecret);
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
