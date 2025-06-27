# DeliciousApp - Android美食应用

这是一个Android平台的美食应用项目，提供用户注册、登录和美食浏览等功能。

## 项目结构

```
├── Activity - 活动类
│   ├── LoginActivity.java - 用户登录界面
│   └── RegisterActivity.java - 用户注册界面
├── api - 网络API接口
│   ├── RetrofitClient.java - Retrofit客户端配置
│   └── UserApiService.java - 用户相关API接口
├── dto - 数据传输对象
│   └── RegisterRequest.java - 注册请求DTO
└── model - 数据模型
    ├── ApiResponse.java - API响应封装类
    └── User.java - 用户模型类
```

## 主要功能

- 用户注册：支持邮箱、用户名和密码注册
- 用户登录：支持用户名和密码登录
- 安全认证：使用明文密码传输，由后端使用BCrypt进行加密和验证

## 技术栈

- Retrofit2：网络请求库
- OkHttp3：HTTP客户端
- Gson：JSON序列化/反序列化
