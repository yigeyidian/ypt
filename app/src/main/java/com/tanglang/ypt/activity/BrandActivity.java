package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.BrandGridViewAdapter;
import com.tanglang.ypt.bean.Brand;
import com.tanglang.ypt.view.HomeGridView;

import java.util.List;

/**
 * Author： Administrator
 */
public class BrandActivity extends BaseActivity {

    private ImageView ivBack;
    private HomeGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        ivBack = (ImageView) findViewById(R.id.brand_iv_back);
        gridView = (HomeGridView) findViewById(R.id.brand_gv);

        List<Brand> brandList = (List<Brand>) getIntent().getSerializableExtra("brands");
        BrandGridViewAdapter adapter = new BrandGridViewAdapter(this, brandList);
        gridView.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandActivity.this.finish();
            }
        });
    }
}
