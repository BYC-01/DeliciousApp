package com.example.delicious.model;

import com.google.gson.annotations.SerializedName;

/**
 * 通用API响应类，用于封装后端返回的数据
 * @param <T> 响应数据的类型
 */
public class ApiResponse<T> {
    @SerializedName("code")
    private int code;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private T data;
    
    // 判断请求是否成功
    public boolean isSuccess() {
        return code == 200;
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}