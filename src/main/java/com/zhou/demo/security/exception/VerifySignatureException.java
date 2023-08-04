package com.zhou.demo.security.exception;

/**
 * 签名校验失败
 */
public class VerifySignatureException extends RuntimeException {
    public VerifySignatureException() {
    }

    public VerifySignatureException(String message) {
    }

    public VerifySignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifySignatureException(Throwable cause) {
        super(cause);
    }
}
