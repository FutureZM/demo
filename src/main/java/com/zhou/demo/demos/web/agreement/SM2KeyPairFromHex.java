package com.zhou.demo.demos.web.agreement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SM2KeyPairFromHex {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        convert2KeyPair(Const.ALICE_PRIVATE_KEY, Const.ALICE_PUBLIC_KEY);
        System.out.println("KeyPair successfully reconstructed from hex strings.");
    }

    public static KeyPair convert2KeyPair(String publicKeyHex, String privateKeyHex) {
        // Convert hex strings to byte arrays
        byte[] privateKeyBytes = Hex.decode(privateKeyHex);
        byte[] publicKeyBytes = Hex.decode(publicKeyHex);

        // Create PrivateKey and PublicKey objects from byte arrays
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            return new KeyPair(publicKey, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
