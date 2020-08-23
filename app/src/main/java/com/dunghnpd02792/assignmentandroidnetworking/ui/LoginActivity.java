package com.dunghnpd02792.assignmentandroidnetworking.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    APIService apiService;
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView tvGoToRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (new SharedPreferencesToken(getApplicationContext()).isCheckSaveLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initView() {
        edEmail = findViewById(R.id.edEmaillg);
        edPassword = findViewById(R.id.edPasswordlg);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);
        btnLogin = findViewById(R.id.btnLoginlg);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvGoToRegister.setOnClickListener(this);
    }

    private void callRequest() {
        apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.loginUser(edEmail.getText().toString(), edPassword.getText().toString()).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        SharedPreferencesToken sharedPreferencesToken = new SharedPreferencesToken(getApplicationContext());
                        sharedPreferencesToken.SavePass(response.body().getUserData().getEmail(), response.body().getUserData().getHashedPassword(), response.body().getStatus());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        Log.d("DULIEU", "onResponse Status: " + response.body().getStatus());
                    } else {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Not response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.d("DULIEU", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginlg:
                if (edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter field", Toast.LENGTH_SHORT).show();
                } else {
                    callRequest();
                }
                break;
            case R.id.tvGoToRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                break;
        }
    }
}