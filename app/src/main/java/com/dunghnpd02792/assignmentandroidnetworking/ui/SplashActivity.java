package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dunghnpd02792.assignmentandroidnetworking.R;

public class SplashActivity extends AppCompatActivity {
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        thread.start();
    }
}