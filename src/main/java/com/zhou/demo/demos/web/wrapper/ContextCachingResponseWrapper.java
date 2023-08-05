package com.zhou.demo.demos.web.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * response wrapper
 *
 * @author laurence
 */
public class ContextCachingResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ServletOutputStream servletOutputStream;
    private PrintWriter printWriter;

    public ContextCachingResponseWrapper(HttpServletResponse response) {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
        servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) {
                byteArrayOutputStream.write(b);
            }
        };
        printWriter = new PrintWriter(byteArrayOutputStream);
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    public byte[] toByteArray() {
        return byteArrayOutputStream.toByteArray();
    }
}
