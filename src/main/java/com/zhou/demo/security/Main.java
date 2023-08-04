package com.zhou.demo.security;

import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;
import org.bouncycastle.crypto.CryptoException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:55
 */
public class Main {

    public static final String DEMO_APP_ID = "21321432432";
    private static final String PRIVATE_KEY = "22943448E995AD5B0A8C84442570F286C956256A00658F2B3A60120974048C57";
    private static final String OWN_PUBLIC_KEY = "2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B";
    private static final String OTHER_PUBLIC_KEY = "3F8A58889AC44ACD1AE70A32726323FDF9AF839C347881570746AEB4FDF528626582F55985099727FD425CFB4791AC40CB196E9BD2363A5FCB394EA02975E0F2";
    private static final String OTHER_PRIVATE_KEY = "4ADDC747BEC7557EDCC15758F85A86E4328A4139B15FEE5529A2136353B5048C";


    public static void main(String[] args) throws CryptoException {
        DemoDto demo = new DemoDto().setName("2023年了, 谁还用传统的编程方式").setAge(23);
        String encryptData = SM2EncryptionAndSignature.encrypt(OTHER_PUBLIC_KEY, JsonUtils.toString(demo));
        BaseDTO baseDTO = new BaseDTO().setAppId(DEMO_APP_ID)
                .setNonce(UUID.randomUUID().toString().replace("-", ""))
                .setTimestamp(String.valueOf(System.currentTimeMillis()))
                .setData(encryptData);

        //拼接参数, 针对baseDTO中除signature之外的field以字典序号排列为a=av&b=bv&c=cv的形式, 然后对拼接后的结果进行签名
        String concatResult = ObjectUtils.sortByDictOrderAndConcat(baseDTO, "&", "signature");

        //将签名写入dto对象
        baseDTO.setSignature(SM2EncryptionAndSignature.sign(PRIVATE_KEY, concatResult));
        //接收到此dto对象后验证签名并解密的逻辑
        System.out.println(SM2EncryptionAndSignature.verify(OWN_PUBLIC_KEY, ObjectUtils.sortByDictOrderAndConcat(baseDTO, "&", "signature"), baseDTO.getSignature()));
        String plainText = SM2EncryptionAndSignature.decrypt(OTHER_PRIVATE_KEY, baseDTO.getData());
        System.out.println(plainText);
        DemoDto demoDto = JsonUtils.parse(plainText, DemoDto.class);
        System.out.println(demoDto);
    }
}