package com.zhou.demo.security.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhou.demo.demos.web.config.BaseSM2Config;
import com.zhou.demo.security.request.ApiResponse;
import com.zhou.demo.util.JsonUtils;

/**
 * ApiRequest 辅助处理类
 *
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午3:55
 */
public class ResponseProcessor extends BaseApiProcessor {
    /**
     * ApiRequest build
     */
    public static <T> ApiResponse buildApiResponse(BaseSM2Config inServerConfig, T data) {

        ApiResponse apiResponse = new ApiResponse();

        //赋值
        assignment(apiResponse, inServerConfig, data);

        return apiResponse;
    }

    /**
     * 多层范型json对象解析
     */
    public static <T> T parseApiResponse(ApiResponse apiResponse, BaseSM2Config config, TypeReference<T> beanType) {
        return JsonUtils.parseWithTypeReference(parse(apiResponse, config), beanType);
    }

}