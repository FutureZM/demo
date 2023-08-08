package com.zhou.demo.security.processor;

import com.zhou.demo.demos.web.config.BaseSM2Config;
import com.zhou.demo.demos.web.config.ClientSM2Config;
import com.zhou.demo.security.SM2EncryptionAndSignature;
import com.zhou.demo.security.exception.VerifySignatureException;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.security.request.Base;
import com.zhou.demo.util.JsonUtils;
import com.zhou.demo.util.ObjectUtils;

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
    public static <T> ApiRequest buildApiRequest(BaseSM2Config config, T data) {
        ApiRequest apiRequest = new ApiRequest().setAppId(config.getAppId())
                //替换掉uuid中的-
                .setNonce(UUID.randomUUID().toString().replace("-", ""));

        //赋值
        assignment(apiRequest, config, data);

        return apiRequest;
    }

    /**
     * ApiRequest parse
     */
    public static <T> T parseApiRequest(ApiRequest apiRequest, BaseSM2Config inServerConfig, Class<T> beanType) {
        return JsonUtils.parse(parse(apiRequest, inServerConfig), beanType);
    }
}