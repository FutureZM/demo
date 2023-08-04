package com.zhou.demo.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;

/**
 * @author laurence
 */
public class ClientA extends Client {
    private static final String PRIVATE_KEY = "4ADDC747BEC7557EDCC15758F85A86E4328A4139B15FEE5529A2136353B5048C";
    private static final String OWN_PUBLIC_KEY = "3F8A58889AC44ACD1AE70A32726323FDF9AF839C347881570746AEB4FDF528626582F55985099727FD425CFB4791AC40CB196E9BD2363A5FCB394EA02975E0F2";
    private static final String OTHER_PUBLIC_KEY = "2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B";


    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        final ClientA client = new ClientA();
        final String sharedSecret = client.generateSharedSecret(PRIVATE_KEY, OTHER_PUBLIC_KEY);
        System.out.println(sharedSecret);
    }

//    public static String
}
