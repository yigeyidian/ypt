package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.utils.LogUtils;

/**
 * Author： Administrator
 */
public class DoctorActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ivBack;
    private EditText etAge;
    private EditText etText;
    private Button btSend;
    private boolean sex;
    private ImageView ivMan;
    private ImageView ivWoman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        initView();
    }

    private void initView() {
        ivBack = (ImageButton) findViewById(R.id.doctor_iv_back);
        etAge = (EditText) findViewById(R.id.doctor_et_age);
        etText = (EditText) findViewById(R.id.doctor_et_text);
        btSend = (Button) findViewById(R.id.doctor_bt_send);
        ivMan = (ImageView) findViewById(R.id.doctor_iv_man);
        ivWoman = (ImageView) findViewById(R.id.doctor_iv_woman);

        ivBack.setOnClickListener(this);
        btSend.setOnClickListener(this);
        ivMan.setOnClickListener(this);
        ivWoman.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doctor_iv_back:
                finish();
                break;
            case R.id.doctor_bt_send:
                sendText();
                break;
            case R.id.doctor_iv_man:
                if (sex) {
                    sex = false;
                    changeSex();
                }
                break;
            case R.id.doctor_iv_woman:
                if (!sex) {
                    sex = true;
                    changeSex();
                }
                break;
        }
    }

    private void changeSex() {
        if (sex) {
            ivMan.setImageResource(R.mipmap.ic_man_normal);
            ivWoman.setImageResource(R.mipmap.ic_woman_press);
        } else {
            ivMan.setImageResource(R.mipmap.ic_man_press);
            ivWoman.setImageResource(R.mipmap.ic_woman_normal);
        }
    }

    private void sendText() {
        String age = etAge.getText().toString();
        String text = etText.getText().toString();
        if (TextUtils.isEmpty(text) || text.length() < 10) {
            LogUtils.showToast(this, "咨询内容不少于10个字");
        }
    }
}
