package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tanglang.ypt.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplanActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splan);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplanActivity.this, MainActivity.class);
                        startActivity(intent);
                        SplanActivity.this.finish();
                    }
                });
            }
        }, 2000);
    }


}
