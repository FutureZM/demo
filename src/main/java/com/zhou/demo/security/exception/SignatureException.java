package com.zhou.demo.security.exception;

/**
 * 签名校验失败
 */
public class SignatureException extends RuntimeException {
    public SignatureException() {
    }

    public SignatureException(String message) {
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }
}
