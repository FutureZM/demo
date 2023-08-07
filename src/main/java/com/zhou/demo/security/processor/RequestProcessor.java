package com.zhou.demo.security.processor;

import com.zhou.demo.demos.web.config.ServerSM2Config;
import com.zhou.demo.security.enums.ApiSecurityType;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.util.JsonUtils;

import java.util.UUID;

/**
 * ApiRequest 辅助处理类
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:55
 */
public class RequestProcessor extends BaseApiProcessor {
    /**
     * ApiRequest build
     */
    public static <T> ApiRequest buildApiRequest(String appId, String clientPrivateKey, String serverPublicKey, T data) {
        ApiRequest apiRequest = new ApiRequest().setAppId(appId)
                //替换掉uuid中的-
                .setNonce(UUID.randomUUID().toString().replace("-", ""));

        //赋值
        assignment(apiRequest, clientPrivateKey, serverPublicKey, data);

        return apiRequest;
    }


    /**
     * ApiRequest parse
     */
    public static <T> T parseApiRequest(ApiRequest apiRequest, ServerSM2Config serverConfig, Class<T> beanType) {
        return JsonUtils.parse(parse(apiRequest, serverConfig, ApiSecurityType.SM2_WITH_SHARED_KEY), beanType);
    }
}