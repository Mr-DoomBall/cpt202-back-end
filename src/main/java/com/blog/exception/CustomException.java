package com.blog.exception;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
    /**
     * 异常返回http状态码
     */
    private int httpStatus = 400;
    /**
     * 异常信息
     */
    private String message;

    public CustomException() {
        super();
    }

    public CustomException(String msg) {
        super(msg);
        this.message = msg;
    }

    public CustomException(int httpStatus, String msg) {
        super();
        this.httpStatus = httpStatus;
        this.message = msg;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
