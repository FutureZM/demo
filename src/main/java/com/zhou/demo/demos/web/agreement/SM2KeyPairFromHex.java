package com.zhou.demo.demos.web.agreement;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SM2KeyPairFromHex {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Hex encoded private key and public key
        String privateKeyHex = "your_private_key_hex_string_here";
        String publicKeyHex = "your_public_key_hex_string_here";

        // Convert hex strings to byte arrays
        byte[] privateKeyBytes = hexToBytes(privateKeyHex);
        byte[] publicKeyBytes = hexToBytes(publicKeyHex);

        // Create PrivateKey and PublicKey objects from byte arrays
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        // Create KeyPair object
        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        System.out.println("KeyPair successfully reconstructed from hex strings.");
    }

    // Helper method to convert hex string to byte array
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
