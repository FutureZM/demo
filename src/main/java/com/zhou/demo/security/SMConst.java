package com.zhou.demo.security;

import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import java.math.BigInteger;

/**
 * SM算法相关常量
 */
public interface SMConst {

    /**
     * 以下是SM2推荐的曲线参数
     */
    SM2P256V1Curve CURVE = new SM2P256V1Curve();
    BigInteger SM2_ECC_N = CURVE.getOrder();
    BigInteger SM2_ECC_H = CURVE.getCofactor();
    BigInteger SM2_ECC_GX = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    BigInteger SM2_ECC_GY = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    ECDomainParameters DOMAIN_PARAMETERS = new ECDomainParameters(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);


    /**
     * 以下是模拟server端和client的公私钥，仅仅用于测试
     */
    String APP_ID = "09090909";
    String CLIENT_PRIVATE_KEY = "22943448E995AD5B0A8C84442570F286C956256A00658F2B3A60120974048C57";
    String CLIENT_PUBLIC_KEY = "2AE055E256DB9D1B344497F38A51ECC5916A5D0D8391766D33EE0B1C157AFAE72DFD63B5E5BE63FAFDE8F2311974A1E59538A65C94894C788B6EAC1ED7715B6B";
    String SERVER_PRIVATE_KEY = "4ADDC747BEC7557EDCC15758F85A86E4328A4139B15FEE5529A2136353B5048C";
    String SERVER_PUBLIC_KEY = "3F8A58889AC44ACD1AE70A32726323FDF9AF839C347881570746AEB4FDF528626582F55985099727FD425CFB4791AC40CB196E9BD2363A5FCB394EA02975E0F2";
}
