package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.utils.UiUtils;


/**
 * Author： Administrator
 */
public class TypeListActivity extends BaseActivity {

    private String[] typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typelist);

        String typename = getIntent().getStringExtra("typename");

        ImageButton btBack = (ImageButton) findViewById(R.id.typelist_back);
        TextView tvTitle = (TextView) findViewById(R.id.typelist_title);
        ListView listView = (ListView) findViewById(R.id.typelist_list);

        tvTitle.setText(typename);

        switch (typename) {
            case "家庭药箱":
                typeList = getResources().getStringArray(R.array.homeType);
                break;
            case "感冒用药":
                typeList = getResources().getStringArray(R.array.coldType);
                break;
            case "维生素/钙":
                typeList = getResources().getStringArray(R.array.vitaminType);
                break;
            case "慢性病药":
                typeList = getResources().getStringArray(R.array.chronicType);
                break;
            case "妇科用药":
                typeList = getResources().getStringArray(R.array.womanType);
                break;
            case "男科用药":
                typeList = getResources().getStringArray(R.array.manType);
                break;
            case "儿童用药":
                typeList = getResources().getStringArray(R.array.childType);
                break;
        }

        if (typeList == null) {
            return;
        }

        ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TypeListActivity.this, DrugsActivity.class);
                intent.putExtra("key", typeList[position]);
                startActivity(intent);
            }
        });


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeListActivity.this.finish();
            }
        });
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return typeList.length;
        }

        @Override
        public Object getItem(int position) {
            return typeList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(TypeListActivity.this);
            textView.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
            textView.setPadding(UiUtils.dip2px(20), UiUtils.dip2px(10), UiUtils.dip2px(20), UiUtils.dip2px(10));
            textView.setText(typeList[position]);
            textView.setTextSize(16);
            return textView;
        }
    }
}
