package com.dunghnpd02792.assignmentandroidnetworking.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edFullName, edEmail, edPass, edConfirmPass;
    private Button btnRegister;
    private TextView tvGoToLogin;
    private CheckBox cbTerm;
    private ImageView imgBackR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        edFullName = findViewById(R.id.edFullNamesn);
        edEmail = findViewById(R.id.edEmailsn);
        edPass = findViewById(R.id.edPasswordsn);
        imgBackR = findViewById(R.id.imgBackR);
        edConfirmPass = findViewById(R.id.edConfirmPasssn);
        btnRegister = findViewById(R.id.btnRegistersn);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);
        cbTerm = findViewById(R.id.cbTerm);

        btnRegister.setOnClickListener(this);
        tvGoToLogin.setOnClickListener(this);
        imgBackR.setOnClickListener(this);
    }

    private void callRequest() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.createUser(edFullName.getText().toString(), edEmail.getText().toString(), edPass.getText().toString()).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Log.d("DULIEU", "onResponse Register: " + response.body().getMessage());
                    } else {
                        Toast.makeText(RegisterActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistersn:
                if (edFullName.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edPass.getText().toString().isEmpty() || edConfirmPass.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!edPass.getText().toString().equals(edConfirmPass.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
                } else if (!cbTerm.isChecked()) {
                    Toast.makeText(this, "Please read and check the term conditions ", Toast.LENGTH_SHORT).show();
                } else {
                    callRequest();
                }
                break;
            case R.id.tvGoToLogin:
                finish();
                break;
            case R.id.imgBackR:
                finish();
                break;
        }
    }
}