package com.tanglang.ypt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanglang.ypt.R;

/**
 * Author： Administrator
 */
public class CheckView extends LinearLayout {

    private CheckBox checkBox;
    private TextView textView;

    public CheckView(Context context) {
        super(context);
        initView();
    }

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.typeitem, this);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        textView = (TextView) view.findViewById(R.id.text);
    }

    public boolean isCheck() {
        return checkBox.isChecked();
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setCheck(boolean check) {
        checkBox.setChecked(check);
    }
}
