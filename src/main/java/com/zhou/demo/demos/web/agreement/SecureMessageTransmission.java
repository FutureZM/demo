package com.zhou.demo.demos.web.agreement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class SecureMessageTransmission {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPair aliceKeyPair = SM2KeyPairFromHex.convert2KeyPair(Const.ALICE_PUBLIC_KEY, Const.ALICE_PRIVATE_KEY);
        KeyPair bobKeyPair = SM2KeyPairFromHex.convert2KeyPair(Const.BOB_PUBLIC_KEY, Const.BOB_PRIVATE_KEY);

        Assert.notNull(aliceKeyPair, "keyPair must be not null");
        Assert.notNull(bobKeyPair, "keyPair must be not null");

        // Perform Diffie-Hellman Key Agreement to derive shared secret
        KeyAgreement dhKeyAgreementAlice = KeyAgreement.getInstance("ECDH");
        dhKeyAgreementAlice.init(aliceKeyPair.getPrivate());
        dhKeyAgreementAlice.doPhase(bobKeyPair.getPublic(), true);

        KeyAgreement dhKeyAgreementBob = KeyAgreement.getInstance("ECDH");
        dhKeyAgreementBob.init(bobKeyPair.getPrivate());
        dhKeyAgreementBob.doPhase(aliceKeyPair.getPublic(), true);

        byte[] aliceSharedSecret = dhKeyAgreementAlice.generateSecret();
        byte[] bobSharedSecret = dhKeyAgreementBob.generateSecret();

        System.out.println(Hex.toHexString(aliceSharedSecret));
        System.out.println(Hex.toHexString(bobSharedSecret));

        // Shared secret should be the same for both Alice and Bob
        if (!MessageDigest.isEqual(aliceSharedSecret, bobSharedSecret)) {
            throw new IllegalStateException("Shared secrets do not match.");
        }

        // Use shared secret as key material for encryption
        SecretKey sharedKey = new SecretKeySpec(aliceSharedSecret, 0, 16, "SM4");

        // Message to be transmitted
        String message = "Hello, Bob!";
        byte[] messageBytes = message.getBytes();

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
}
