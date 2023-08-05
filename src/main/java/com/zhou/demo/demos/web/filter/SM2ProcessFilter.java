package com.zhou.demo.demos.web.filter;

import com.zhou.demo.demos.web.wrapper.ContextCachingRequestWrapper;
import com.zhou.demo.security.RequestProcessor;
import com.zhou.demo.security.SMConst;
import com.zhou.demo.security.request.ApiRequest;
import com.zhou.demo.util.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author laurence
 */
@Component
@WebFilter(urlPatterns = "/api/*")
public class SM2ProcessFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ContextCachingRequestWrapper requestWrapper;

        if (!(servletRequest instanceof HttpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        requestWrapper = new ContextCachingRequestWrapper(request);
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

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
