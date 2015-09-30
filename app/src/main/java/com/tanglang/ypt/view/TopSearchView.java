package com.tanglang.ypt.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.activity.ScanActivity;

public class TopSearchView extends LinearLayout {

    private View view;

    public TopSearchView(Context context) {
        super(context);
        initView();
    }

    public TopSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public TopSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.topsearch_view, this);
        //addView(view);
        Button btMenu = (Button) view.findViewById(R.id.topsearch_bt_menu);
        Button btScan = (Button) view.findViewById(R.id.topsearch_bt_scan);

        btScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
