package com.dunghnpd02792.assignmentandroidnetworking.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edFullName, edEmail, edPass, edConfirmPass;
    private Button btnRegister;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        edFullName = findViewById(R.id.edFullNamesn);
        edEmail = findViewById(R.id.edEmailsn);
        edPass = findViewById(R.id.edPasswordsn);
        edConfirmPass = findViewById(R.id.edConfirmPasssn);
        btnRegister = findViewById(R.id.btnRegistersn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edFullName.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edPass.getText().toString().isEmpty() || edConfirmPass.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                } else if (!edPass.getText().toString().equals(edConfirmPass.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đúng mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    callRequest();
                }

            }
        });
    }

    private void callRequest() {
        apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.createUser(edFullName.getText().toString(), edEmail.getText().toString(), edPass.getText().toString()).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                        finish();
                        Log.d("DULIEU", "onResponse Register: " + response.body().getMessage());
                    } else {
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        Log.d("DULIEU", "onResponse Register: " + response.body().getMessage());

                    }
                }


            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.d("DULIEU", "onFailure Register: " + t.getMessage());

            }
        });
    }
}