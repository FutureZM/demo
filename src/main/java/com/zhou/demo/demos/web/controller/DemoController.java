package com.zhou.demo.demos.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhou.demo.demos.web.config.BaseSM2Config;
import com.zhou.demo.demos.web.config.ClientSM2Config;
import com.zhou.demo.demos.web.config.RefreshConfigProperties;
import com.zhou.demo.demos.web.db.ApiAccessCtx;
import com.zhou.demo.demos.web.event.ConfigUpdateEvent;
import com.zhou.demo.demos.web.mapper.RegisterMapper;
import com.zhou.demo.demos.web.model.RegisterInfo;
import com.zhou.demo.demos.web.result.Result;
import com.zhou.demo.demos.web.spring.AnoValueRefreshPostProcessor;
import com.zhou.demo.security.dto.DemoDto;
import com.zhou.demo.security.enums.ApiSecurityType;
import com.zhou.demo.security.processor.RequestProcessor;
import com.zhou.demo.security.processor.ResponseProcessor;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.security.request.ApiResponse;
import com.zhou.demo.util.HttpUtils;
import com.zhou.demo.util.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author laurence
 */
@Slf4j
@RestController
public class DemoController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private ClientSM2Config clientConfig;

    @Autowired
    private Environment environment;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigurableEnvironment configEnvironment;
    @Autowired
    RefreshConfigProperties refreshConfigProperties;

    @PostMapping("/demo")
    public Result<DemoDto> demo(@RequestBody DemoDto demoDto) {
        return Result.success(demoDto);
    }

    @PostMapping("/api/sm2demo")
    public Result<DemoDto> sm2Demo(@RequestBody DemoDto demoDto) {
        return Result.success(demoDto);
    }

    @SneakyThrows
    @PostMapping("/client/demo")
    public Result<DemoDto> clientDemo(@RequestBody DemoDto demoDto) {

        String url = "http://127.0.0.1:" + serverPort + "/demo";
        String res = HttpUtils.sendPostJsonRequest(url, JsonUtils.toString(demoDto));

        Result<DemoDto> result = JsonUtils.parseWithTypeReference(res, new TypeReference<Result<DemoDto>>() {
        });

        return Result.success(result.getData());
    }

    @SneakyThrows
    @PostMapping("/client/api/demo")
    public Result<DemoDto> clientSm2Demo(@RequestBody DemoDto demoDto) {
        String url = "http://127.0.0.1:" + serverPort + "/api/sm2demo";

        BaseSM2Config config = new BaseSM2Config().setAppId(clientConfig.getAppId()).setOtherSidePublicKey(clientConfig.getServerPublicKey());
        config.setPublicKey(clientConfig.getPublicKey()).setPrivateKey(clientConfig.getPrivateKey());
        config.setApiSecurityType(ApiSecurityType.SM2_SIMPLE);

        ApiRequest apiRequest = RequestProcessor.buildApiRequest(config, demoDto);
        String res = HttpUtils.sendPostJsonRequest(url, JsonUtils.toString(apiRequest));

        ApiResponse apiResponse = JsonUtils.parse(res, ApiResponse.class);
        Result<DemoDto> result = ResponseProcessor.parseApiResponse(apiResponse, config, new TypeReference<Result<DemoDto>>() {
        });

        return Result.success(result.getData());
    }

    @SneakyThrows
    @PostMapping("/client/api/sharedKey/demo")
    public Result<DemoDto> clientSharedKeySm2Demo(@RequestBody DemoDto demoDto) {
        String url = "http://127.0.0.1:" + serverPort + "/api/sm2demo";

        String appId = environment.getProperty("demo.client1.appId");
        BaseSM2Config config = new BaseSM2Config().setAppId(appId)
                .setOtherSidePublicKey(clientConfig.getServerPublicKey());
        config.setPublicKey(clientConfig.getPublicKey()).setPrivateKey(clientConfig.getPrivateKey());
        config.setApiSecurityType(ApiSecurityType.SM2_WITH_SHARED_KEY);

        //模拟从缓存中取出协商密钥的逻辑
        config.setAgreementKey(ApiAccessCtx.ACCESS_CTX_MAP.get(appId).getSharedKey());

        ApiRequest apiRequest = RequestProcessor.buildApiRequest(config, demoDto);
        String res = HttpUtils.sendPostJsonRequest(url, JsonUtils.toString(apiRequest));

        ApiResponse apiResponse = JsonUtils.parse(res, ApiResponse.class);
        Result<DemoDto> result = ResponseProcessor.parseApiResponse(apiResponse, config, new TypeReference<Result<DemoDto>>() {
        });

        return Result.success(result.getData());
    }

    @GetMapping(path = "/dynamic/update")
    public RefreshConfigProperties updateEnvironment(String key, String value) {
//        String name = "applicationConfig: [classpath:/application.properties]";
        String name = "Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'";
        MapPropertySource propertySource = (MapPropertySource) configEnvironment.getPropertySources().get(name);
        assert propertySource != null;
        Map<String, Object> source = propertySource.getSource();
        Map<String, Object> map = new HashMap<>(source.size());
        map.putAll(source);
        map.put(key, value);
        configEnvironment.getPropertySources().replace(name, new MapPropertySource(name, map));

        applicationContext.publishEvent(new ConfigUpdateEvent(this, key));
        return refreshConfigProperties;
    }

    @GetMapping(path = "/refresh/get")
    public RefreshConfigProperties getRefreshConfigProperties() {
        return refreshConfigProperties;
    }

    @Autowired
    private RegisterMapper registerMapper;

    @GetMapping("/db/demo/id")
    public Long insertDbDorGenId(){
        RegisterInfo registerInfo = new RegisterInfo().setName("insertDbDorGenId-demo").setPhone("11111111111").setStatus(999);
        Integer integer = registerMapper.insertDbDorGenId(registerInfo);
        return registerInfo.getId();
    }
}
