package com.zhou.demo.demos.web.filter;

import com.zhou.demo.demos.web.wrapper.ContextCachingRequestWrapper;
import com.zhou.demo.demos.web.wrapper.ContextCachingResponseWrapper;
import com.zhou.demo.security.processor.RequestProcessor;
import com.zhou.demo.security.processor.ResponseProcessor;
import com.zhou.demo.security.SMConst;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.security.request.ApiResponse;
import com.zhou.demo.util.JsonUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * filter不可以直接家@Component 否则@WebFilter的urlPatterns配置失效。处理方法去掉@Component注解, 配置类加上@ServletComponentScan, 此处选择添加在DemoApplication上
 *
 * @author laurence
 */
@WebFilter(urlPatterns = "/api/*")
public class SM2ProcessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ContextCachingRequestWrapper requestWrapper;
        ContextCachingResponseWrapper responseWrapper;

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //此处使用包装类, 是为了解决servletRequest、servletResponse一次读取后内容失效的问题
        requestWrapper = new ContextCachingRequestWrapper(request);
        responseWrapper = new ContextCachingResponseWrapper((HttpServletResponse) servletResponse);

        //处理SM2验证签名和解密
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            StringBuilder data = new StringBuilder();
            String line;
            BufferedReader reader;
            reader = requestWrapper.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }

            ApiRequest apiRequest = JsonUtils.parse(data.toString(), ApiRequest.class);
            Map map = RequestProcessor.parseApiRequest(apiRequest, SMConst.SERVER_PRIVATE_KEY, SMConst.CLIENT_PUBLIC_KEY, Map.class);

            //解密后写回request, 用于后续的业务处理
            requestWrapper.setBody(JsonUtils.toString(map));
        }

        filterChain.doFilter(requestWrapper, responseWrapper);

        // 将修改后的响应写回到原始的HttpServletResponse
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        // 针对响应对象进行处理
        String responseCtx = new String(responseWrapper.toByteArray(), StandardCharsets.UTF_8);
        // - 完成加密和签名
        ApiResponse apiResponse = ResponseProcessor.buildApiResponse(SMConst.SERVER_PRIVATE_KEY, SMConst.CLIENT_PUBLIC_KEY, JsonUtils.parse(responseCtx, Map.class));
        // 写回response对象
        servletResponse.getWriter().write(JsonUtils.toString(apiResponse));
    }

    @Override
    public void destroy() {

    }
}
