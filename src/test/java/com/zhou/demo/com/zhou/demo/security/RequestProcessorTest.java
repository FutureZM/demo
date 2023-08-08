package com.zhou.demo.com.zhou.demo.security;

import com.zhou.demo.demos.web.config.BaseSM2Config;
import com.zhou.demo.security.enums.ApiSecurityType;
import com.zhou.demo.security.processor.RequestProcessor;
import com.zhou.demo.security.dto.DemoDto;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.zhou.demo.com.zhou.demo.security.ConstTest.*;


class RequestProcessorTest {

    private static DemoDto demo;
    private static ApiRequest apiRequest;

    static {
        demo = new DemoDto().setName("2023年了, 谁还用传统的编程方式1").setAge(23);

        final BaseSM2Config config = new BaseSM2Config().setAppId(APP_ID_DEMO).setApiSecurityType(ApiSecurityType.SM2_SIMPLE).setOtherSidePublicKey(SERVER_PUBLIC_KEY);
        config.setPrivateKey(CLIENT_PRIVATE_KEY);
        config.setPublicKey(CLIENT_PUBLIC_KEY);
        apiRequest = RequestProcessor.buildApiRequest(config, demo);
        System.out.println(JsonUtils.toString(apiRequest));
    }

    @Test
    void buildApiRequest() {
        Assert.isTrue(apiRequest != null, "apiRequest构建失败");
    }

    @Test
    void parseApiRequest() {
        final BaseSM2Config config = new BaseSM2Config().setApiSecurityType(ApiSecurityType.SM2_SIMPLE).setOtherSidePublicKey(CLIENT_PUBLIC_KEY);
        config.setPrivateKey(SERVER_PRIVATE_KEY);
        config.setPublicKey(SERVER_PUBLIC_KEY);

        DemoDto demoDto = RequestProcessor.parseApiRequest(apiRequest, config, DemoDto.class);
        Assert.isTrue(demoDto.toString().equals(demo.toString()), "加密后的数据解密后与原始数据不一致");
    }
}
