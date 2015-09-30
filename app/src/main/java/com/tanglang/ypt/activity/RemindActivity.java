package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tanglang.ypt.R;

/**
 * Author： Administrator
 */
public class RemindActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        ImageButton ivBack = (ImageButton) findViewById(R.id.remind_iv_back);

        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_iv_back:
                finish();
                break;
        }
    }
}
