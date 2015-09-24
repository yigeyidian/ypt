package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tanglang.ypt.YPTApplication;

public class BaseActivity extends FragmentActivity {

    protected YPTApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (YPTApplication) getApplication();
        app.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.removeActivity(this);
    }
}
