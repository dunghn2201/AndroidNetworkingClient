package com.dunghnpd02792.assignmentandroidnetworking.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dunghnpd02792.assignmentandroidnetworking.R;

public class TestThoiActivity extends AppCompatActivity {
    Button btnUpdateUser;
    TextView tvStatebottomsheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thoi);
//        btnUpdateUser = findViewById(R.id.imgBtnUpdateUser);
//        tvStatebottomsheet = findViewById(R.id.tvStatebottomsheet);
//        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showUpdateBottomSheetDialog();
//            }
//        });
    }
//
//    private void showUpdateBottomSheetDialog() {
//        UpdateUserBottomSheetDialog updateUserBottomSheetDialog =
//                UpdateUserBottomSheetDialog.newInstance();
//        updateUserBottomSheetDialog.show(getSupportFragmentManager(),
//                UpdateUserBottomSheetDialog.TAG);
//    }
//
//    @Override
//    public void onItemClick(String item) {
//        tvStatebottomsheet.setText("Selected action item is " + item);
//    }
}