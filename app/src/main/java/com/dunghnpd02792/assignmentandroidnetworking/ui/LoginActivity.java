package com.dunghnpd02792.assignmentandroidnetworking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.api.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    APIService apiService;
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView tvRegisterlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        tvRegisterlg = findViewById(R.id.edPasswordlg);
        btnLogin = findViewById(R.id.btnLoginlg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter field", Toast.LENGTH_SHORT).show();
                } else {
                    callRequest();
                }

            }
        });
        tvRegisterlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void callRequest() {
        apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.loginUser(edEmail.getText().toString(), edPassword.getText().toString()).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        SharedPreferencesToken sharedPreferencesToken = new SharedPreferencesToken(getApplicationContext());
                        sharedPreferencesToken.SavePass(response.body().getUserData().getEmail(), response.body().getUserData().getHash(), response.body().getStatus());
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
}