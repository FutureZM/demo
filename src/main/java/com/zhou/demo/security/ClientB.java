package com.zhou.demo.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * @author laurence
 */
public class ClientB {
    private static final String PRIVATE_KEY = "22943448E995AD5B0A8C84442570F286C956256A00658F2B3A60120974048C57";
    private static final String OWN_PUBLIC_KEY = "2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B";
    private static final String OTHER_PUBLIC_KEY = "3F8A58889AC44ACD1AE70A32726323FDF9AF839C347881570746AEB4FDF528626582F55985099727FD425CFB4791AC40CB196E9BD2363A5FCB394EA02975E0F2";

    /**
     * 14FBE6D6B6C75FF3C43B3BCA4D4220FCA9B39A5C82B807DEA125CAD7A0535443
     * 14FBE6D6B6C75FF3C43B3BCA4D4220FCA9B39A5C82B807DEA125CAD7A0535443
     *
     * @param args
     */
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        final ClientA client = new ClientA();
        final String sharedSecret = client.generateSharedSecret(PRIVATE_KEY, OTHER_PUBLIC_KEY);
        System.out.println(sharedSecret);
    }

}
