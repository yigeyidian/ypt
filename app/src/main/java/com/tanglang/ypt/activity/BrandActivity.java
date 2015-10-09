package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.BrandGridViewAdapter;
import com.tanglang.ypt.bean.Brand;
import com.tanglang.ypt.view.HomeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class BrandActivity extends BaseActivity {

    private ImageButton ivBack;
    private HomeGridView gridView;
    private List<Brand> brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        ivBack = (ImageButton) findViewById(R.id.brand_iv_back);
        gridView = (HomeGridView) findViewById(R.id.brand_gv);

        brandList = (ArrayList<Brand>) getIntent().getSerializableExtra("brands");
        BrandGridViewAdapter adapter = new BrandGridViewAdapter(this, brandList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BrandActivity.this, DrugsActivity.class);
                intent.putExtra("brand", brandList.get(position));
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandActivity.this.finish();
            }
        });
    }
}
