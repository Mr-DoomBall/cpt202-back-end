package com.blog.enums;

/**
 * API 全局状态码
 */
public enum ApiStatus {
    NOT_LOGIN(401, "未登录或登录失效");

    private Integer status;

    private String message;

    ApiStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer status() {
        return this.status;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ApiStatus item : ApiStatus.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ApiStatus item : ApiStatus.values()) {
            if (item.name().equals(name)) {
                return item.status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
