package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.OTPEditText;
import com.dunghnpd02792.assignmentandroidnetworking.config.ProgressGenerator;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserResetPass;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, ProgressGenerator.OnCompleteListener {
    private LinearLayout ViewSendEmail, ViewEnterCode, ViewCornfirmPassword;
    private TextInputEditText edResetPassEmail, edResetPass, edConfirmResetPass;
    private OTPEditText edOTPopenCode;
    private ActionProcessButton btnSubmitEmail;
    private Button btnResetPassword, btnActiveCode;
    private String OTPCodeResponse;
    private ImageView imgBackRp;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_reset_password);
        imgBackRp = findViewById(R.id.imgBackRp);
        imgBackRp.setOnClickListener(this);
        initViewSendEmail();
        initViewEnterCode();
        initViewCornfirmPassword();
    }

    private void initViewSendEmail() {
        ViewSendEmail = findViewById(R.id.ViewSendEmail);
        edResetPassEmail = findViewById(R.id.edResetPassEmail);
        btnSubmitEmail = findViewById(R.id.btnSubmitEmail);

        btnSubmitEmail.setOnClickListener(this);
    }

    private void initViewEnterCode() {
        ViewEnterCode = findViewById(R.id.ViewEnterCode);
        edOTPopenCode = findViewById(R.id.edOTPopenCode);
        btnActiveCode = findViewById(R.id.btnActiveCode);
        btnActiveCode.setOnClickListener(this);
    }

    private void initViewCornfirmPassword() {
        ViewCornfirmPassword = findViewById(R.id.ViewCornfirmPassword);
        edResetPass = findViewById(R.id.edResetPass);
        edConfirmResetPass = findViewById(R.id.edConfirmResetPass);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        ProgressGenerator progressGenerator = new ProgressGenerator(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSubmitEmail.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSubmitEmail.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        switch (v.getId()) {
            case R.id.btnSubmitEmail:
                progressGenerator.start(btnSubmitEmail);
                btnSubmitEmail.setEnabled(false);
                edResetPassEmail.setEnabled(false);
                if (edResetPassEmail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập vào email", Toast.LENGTH_SHORT).show();
                } else {
                    apiService.resetPasswordInit(edResetPassEmail.getText().toString()).enqueue(new Callback<UserResetPass>() {
                        @Override
                        public void onResponse(Call<UserResetPass> call, Response<UserResetPass> response) {
                            if (response.isSuccessful()) {
                                OTPCodeResponse = response.body().getMessage();
                                ViewEnterCode.setVisibility(View.VISIBLE);
                                ViewSendEmail.setVisibility(View.GONE);
//                ViewCornfirmPassword.setVisibility(View.GONE);
                                Log.d("DULIEU", "onResponse: " + response.body().getMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<UserResetPass> call, Throwable t) {
                            Log.d("DULIEU", "onFailure: " + t.getMessage());
                            btnSubmitEmail.setEnabled(true);
                            edResetPassEmail.setEnabled(true);
                        }
                    });

                }

                break;
            case R.id.btnActiveCode:
                if (edOTPopenCode.getText().toString().isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng nhập vào trường", Toast.LENGTH_SHORT).show();
                } else if (!edOTPopenCode.getText().toString().equals(OTPCodeResponse)) {
                    Toast.makeText(ResetPasswordActivity.this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    ViewEnterCode.setVisibility(View.GONE);
//                ViewSendEmail.setVisibility(View.GONE);
                    ViewCornfirmPassword.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.btnResetPassword:
                if (edResetPass.getText().toString().isEmpty() || edConfirmResetPass.getText().toString().isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng nhập vào trường", Toast.LENGTH_SHORT).show();

                } else if (!edResetPass.getText().toString().equals(edConfirmResetPass.getText().toString())) {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    apiService.resetPasswordFinish(edResetPassEmail.getText().toString(), edResetPass.getText().toString()).enqueue(new Callback<UserResetPass>() {
                        @Override
                        public void onResponse(Call<UserResetPass> call, Response<UserResetPass> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Reset password successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            Log.d("DULIEU", "onResponse: " + response.body().getMessage());

                        }

                        @Override
                        public void onFailure(Call<UserResetPass> call, Throwable t) {
                            Toast.makeText(ResetPasswordActivity.this, "Reset password successfully", Toast.LENGTH_SHORT).show();
                            Log.d("DULIEU", "onFailure: " + t.getMessage());
                            finish();
                        }
                    });
                }
                break;
            case R.id.imgBackRp:
                finish();
                break;
        }
    }

    @Override
    public void onComplete() {
        Log.d("DULIEU", "process button success");
    }
}