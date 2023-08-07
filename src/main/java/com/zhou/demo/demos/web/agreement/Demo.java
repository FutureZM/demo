package com.zhou.demo.demos.web.agreement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.*;

/**
 * alice-public   : 3059301306072a8648ce3d020106082a8648ce3d030107034200046acc163fb07d5738db5fe5590017a0f8a9a5604172e577eb0139960f10e0dd8e46a6223c48d5b412c6c95557c2d80b698fdc6ab9b353180ca0e04e301e9630ae
 * alice-private  : 308193020100301306072a8648ce3d020106082a8648ce3d0301070479307702010104205d68ba6238279fbe0c1d91ece8f5ef794ce00cac2bd304bb135a116ba38feb62a00a06082a8648ce3d030107a144034200046acc163fb07d5738db5fe5590017a0f8a9a5604172e577eb0139960f10e0dd8e46a6223c48d5b412c6c95557c2d80b698fdc6ab9b353180ca0e04e301e9630ae
 *
 * bob-public     : 3059301306072a8648ce3d020106082a8648ce3d030107034200046630285d116512f6248b8cff9c3f079da59638bb5df190c06290185a32323febf9c006cb9f47dab3e9dcad23b2bf259644fd6c7c51f4ea24b98772f0142492e8
 * bob-private    : 308193020100301306072a8648ce3d020106082a8648ce3d030107047930770201010420673491c7b01d201676e13117d535bda7b952748db71fdf447bdf336a145f8898a00a06082a8648ce3d030107a144034200046630285d116512f6248b8cff9c3f079da59638bb5df190c06290185a32323febf9c006cb9f47dab3e9dcad23b2bf259644fd6c7c51f4ea24b98772f0142492e8
 * @author laurence
 */
public class Demo {
    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
//        generateKeyPair();



    }

    private static void generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());

        // Generate SM2 Key Pair for Alice
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
        kpGen.initialize(256);
        KeyPair aliceKeyPair = kpGen.generateKeyPair();

        // Generate SM2 Key Pair for Bob
        KeyPair bobKeyPair = kpGen.generateKeyPair();

        System.out.println(Hex.toHexString(aliceKeyPair.getPublic().getEncoded()));
        System.out.println(Hex.toHexString(aliceKeyPair.getPrivate().getEncoded()));


        System.out.println(Hex.toHexString(bobKeyPair.getPublic().getEncoded()));
        System.out.println(Hex.toHexString(bobKeyPair.getPrivate().getEncoded()));
    }
}
