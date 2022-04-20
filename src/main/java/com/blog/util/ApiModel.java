package com.blog.util;

import com.blog.enums.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 接口响应json数据对象
 */
public class ApiModel {
    private int status;              //状态码
    private String message;             //返回的文字消息
    private Object data;                //返回的数据

    /**
     * 请求成功
     */
    public static ResponseEntity<ApiModel> success() {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(HttpStatus.OK.value());
        apiModel.setMessage("SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(apiModel);
    }

    /**
     * 请求成功
     */
    public static ResponseEntity<ApiModel> success(Object data) {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(HttpStatus.OK.value());
        apiModel.setMessage("SUCCESS");
        apiModel.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(apiModel);
    }

    /**
     * 请求错误-返回自定义http status
     */
    public static ResponseEntity<ApiModel> fail(ApiStatus apiStatus) {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(apiStatus.status());
        apiModel.setMessage(apiStatus.message());
        return ResponseEntity.status(apiStatus.status()).body(apiModel);
    }

    /**
     * 请求错误-返回400
     */
    public static ResponseEntity<ApiModel> fail(String message) {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(HttpStatus.BAD_REQUEST.value());
        apiModel.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiModel);
    }

    /**
     * 请求错误-返回400加自定义错误码
     */
    public static ResponseEntity<ApiModel> fail(String message, Object data) {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(HttpStatus.BAD_REQUEST.value());
        apiModel.setMessage(message);
        apiModel.setData(data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiModel);
    }

    /**
     * 请求异常
     */
    public static ResponseEntity<ApiModel> error() {
        ApiModel apiModel = new ApiModel();
        apiModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiModel.setMessage("系统异常，请联系管理员");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiModel);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
