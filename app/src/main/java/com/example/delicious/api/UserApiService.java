package com.example.delicious.api;

import com.example.delicious.model.ApiResponse;
import com.example.delicious.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiService {
    
    /**
     * 用户登录
     * @param user 包含用户名和密码的用户对象
     * @return 登录成功返回用户信息
     */
    @POST("api/users/login")
    Call<ApiResponse<User>> login(@Body User user);
    
    /**
     * 用户注册 (使用User对象)
     * @param user 包含注册信息的用户对象
     * @return 注册成功返回用户信息
     * @deprecated 推荐使用 {@link #registerWithDTO(com.example.delicious.dto.RegisterRequest)}
     */
    @Deprecated
    @POST("api/users/register")
    Call<ApiResponse<User>> register(@Body User user);
    
    /**
     * 用户注册 (使用专门的DTO对象)
     * @param registerRequest 包含注册信息的请求对象
     * @return 注册成功返回用户信息
     */
    @POST("api/users/register")
    Call<ApiResponse<User>> registerWithDTO(@Body com.example.delicious.dto.RegisterRequest registerRequest);
    
    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @GET("api/users/{userId}")
    Call<ApiResponse<User>> getUserInfo(@Path("userId") Long userId);
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 检查结果
     */
    @GET("api/users/check-username")
    Call<ApiResponse<Boolean>> checkUsernameExists(@Query("username") String username);
    
    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 检查结果
     */
    @GET("api/users/check-email")
    Call<ApiResponse<Boolean>> checkEmailExists(@Query("email") String email);
}