package com.example.delicious.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.delicious.R;
import com.example.delicious.api.RetrofitClient;
import com.example.delicious.api.UserApiService;
import com.example.delicious.model.ApiResponse;
import com.example.delicious.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView textView;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private UserApiService userApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //初始化视图
        initViews();

        //处理点击事件
        setupClickListeners();

    }

    //初始化视图
    private void initViews(){
        textView = findViewById(R.id.to_register);
        etUsername = findViewById(R.id.edusername);
        etPassword = findViewById(R.id.edpassword);
        btnLogin = findViewById(R.id.m_login);
        
        // 初始化Retrofit服务
        userApiService = RetrofitClient.getInstance().getUserApiService();
    }

    //处理点击事件
    private void setupClickListeners(){
        // 注册页面跳转
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        
        // 登录按钮点击事件
        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            
            // 简单的输入验证
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // 执行登录
            login(username, password);
        });
    }
    
    /**
     * 执行登录请求
     * @param username 用户名
     * @param password 密码
     */
    private void login(String username, String password) {
        // 显示加载提示
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        
        // 直接使用明文密码，由后端使用BCrypt进行加密和验证
        // 创建登录请求对象
        User loginUser = new User(username, password);
        
        // 发起登录请求
        Call<ApiResponse<User>> call = userApiService.login(loginUser);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                progressDialog.dismiss();
                
                if (response.isSuccessful()) {
                    ApiResponse<User> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.isSuccess()) {
                        // 登录成功，获取用户信息
                        User user = apiResponse.getData();
                        Toast.makeText(LoginActivity.this, "登录成功: " + user.getUsername(), Toast.LENGTH_SHORT).show();
                        
                        // TODO: 保存用户信息到本地，跳转到主页面
                        // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // startActivity(intent);
                        // finish();
                    } else {
                        // 登录失败，显示错误信息
                        String errorMsg = apiResponse != null ? apiResponse.getMessage() : "登录失败，请重试";
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // HTTP请求失败
                    Toast.makeText(LoginActivity.this, "网络请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}