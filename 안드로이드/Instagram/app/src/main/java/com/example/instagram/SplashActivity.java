package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            //화면전환시 아래에서 위로 올라오는 애니메이션 제거
            overridePendingTransition(0, 0);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run(){
                handler.sendMessageDelayed(handler.obtainMessage(),2000);
            };
        }.start();
    }
}