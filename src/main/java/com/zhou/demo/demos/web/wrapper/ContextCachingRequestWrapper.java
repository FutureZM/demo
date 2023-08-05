package com.zhou.demo.demos.web.wrapper;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * request wrapper, 用于解决request经一次读取后不可再读的问题
 *
 * @author laurence
 */
public class ContextCachingRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 注意: 此处未声明未final仅仅为允许在验证签名无误后，解密为真正的请求报文，覆盖掉原始报文
     */
    private byte[] body;


    public ContextCachingRequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        body = data.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(String s) {
        body = s.getBytes(StandardCharsets.UTF_8);
    }
}
