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
import com.example.delicious.dto.RegisterRequest;
import com.example.delicious.model.ApiResponse;
import com.example.delicious.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView textView;
    private EditText etEmail, etUsername, etPassword, etPassword2;
    private Button btnRegister;
    private UserApiService userApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //初始化视图
        initViews();

        //处理点击事件
        setupClickListeners();
    }

    //初始化视图
    private void initViews(){
        textView = findViewById(R.id.to_login);
        etEmail = findViewById(R.id.ed_email);
        etUsername = findViewById(R.id.ed_username);
        etPassword = findViewById(R.id.ed_password);
        etPassword2 = findViewById(R.id.ed_password2);
        btnRegister = findViewById(R.id.m_register);
        
        // 初始化Retrofit服务
        userApiService = RetrofitClient.getInstance().getUserApiService();
    }

    //处理点击事件
    private void setupClickListeners(){
        // 登录页面跳转
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        
        // 注册按钮点击事件
        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String password2 = etPassword2.getText().toString().trim();
            
            // 输入验证
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "请填写所有必填信息", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // 验证两次密码是否一致
            if (!password.equals(password2)) {
                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // 执行注册
            register(email, username, password, password2);
        });
    }
    
    // 注册方法
    private void register(String email, String username, String password, String confirmPassword) {
        // 显示加载提示
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在注册...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        
        // 直接使用明文密码，由后端使用BCrypt进行加密和验证
        // 创建专门的注册请求DTO对象
        RegisterRequest registerRequest = new RegisterRequest(email, username, password, confirmPassword);
        
        // 发起注册请求
        Call<ApiResponse<User>> call = userApiService.registerWithDTO(registerRequest);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                progressDialog.dismiss();
                
                if (response.isSuccessful()) {
                    ApiResponse<User> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.isSuccess()) {
                        // 注册成功
                        Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        
                        // 跳转到登录页面
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 注册失败，显示错误信息
                        String errorMsg = apiResponse != null ? apiResponse.getMessage() : "注册失败，请重试";
                        Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // HTTP请求失败
                    Toast.makeText(RegisterActivity.this, "网络请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}