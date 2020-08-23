package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dunghnpd02792.assignmentandroidnetworking.R;

public class SplashActivity extends AppCompatActivity {
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);
        intentActivity();
    }

    private void intentActivity() {
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 1000) {
                    try {
                        sleep(100);
                        ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        };
        thread.start();
    }
}