package com.example.delicious.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 用户注册请求的数据传输对象
 * 专门用于处理注册API请求，与User模型分离
 */
public class RegisterRequest {
    @SerializedName("username")
    private String username;
    
    @SerializedName("password")
    private String password;
    
    @SerializedName("confirm_password")
    private String confirmPassword;
    
    @SerializedName("email")
    private String email;
    
    // 无参构造函数
    public RegisterRequest() {
    }
    
    // 带参构造函数
    public RegisterRequest(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}