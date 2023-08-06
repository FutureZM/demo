package com.zhou.demo.demos.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhou.demo.demos.web.result.Result;
import com.zhou.demo.security.processor.RequestProcessor;
import com.zhou.demo.security.processor.ResponseProcessor;
import com.zhou.demo.security.SMConst;
import com.zhou.demo.security.dto.DemoDto;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.security.request.ApiResponse;
import com.zhou.demo.util.HttpUtils;
import com.zhou.demo.util.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author laurence
 */
@Slf4j
@RestController
public class DemoController {

    @Value("${server.port}")
    private String serverPort;

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
        //log.info("---> " + "in client, controller receive: " + JsonUtils.toString(demoDto));
        String url = "http://127.0.0.1:" + serverPort + "/api/sm2demo";
        ApiRequest apiRequest = RequestProcessor.buildApiRequest(SMConst.APP_ID, SMConst.CLIENT_PRIVATE_KEY, SMConst.SERVER_PUBLIC_KEY, demoDto);
        //log.info("---> " + "in client, buildApiRequest   : " + JsonUtils.toString(apiRequest));
        String res = HttpUtils.sendPostJsonRequest(url, JsonUtils.toString(apiRequest));
        //log.info("---> " + "in client, okhttp response   : " + JsonUtils.toString(res));

        ApiResponse apiResponse = JsonUtils.parse(res, ApiResponse.class);
        Result<DemoDto> result = ResponseProcessor.parseApiResponse(apiResponse, SMConst.CLIENT_PRIVATE_KEY, SMConst.SERVER_PUBLIC_KEY, new TypeReference<Result<DemoDto>>() {
        });

        //log.info("---> " + "in client, JsonUtils.parse   : " + JsonUtils.toString(result));
        return Result.success(result.getData());
    }

}
