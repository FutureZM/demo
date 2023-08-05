package com.zhou.demo.com.zhou.demo.security;

import com.zhou.demo.security.RequestProcessor;
import com.zhou.demo.security.dto.DemoDto;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.zhou.demo.com.zhou.demo.security.ConstTest.*;


public class RequestProcessorTest {

    private static DemoDto demo;
    private static ApiRequest apiRequest;

    static {
        demo = new DemoDto().setName("2023年了, 谁还用传统的编程方式").setAge(23);
        apiRequest = RequestProcessor.buildApiRequest(APP_ID_DEMO, CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY, demo);
        System.out.println(JsonUtils.toString(apiRequest));
    }

    @Test
    void buildApiRequest() {
        Assert.isTrue(apiRequest != null, "apiRequest构建失败");
    }

    @Test
    void parseApiRequest() {
        DemoDto demoDto = RequestProcessor.parseApiRequest(apiRequest, SERVER_PRIVATE_KEY, CLIENT_PUBLIC_KEY, DemoDto.class);
        Assert.isTrue(demoDto.toString().equals(demo.toString()), "加密后的数据解密后与原始数据不一致");
    }
}
