package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.IndexListViewAdapter;

public class FindDrugActivity extends BaseActivity {
    private static String[] indexStr = {"A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "L", "M", " N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_drug);

        ListView listView = (ListView) findViewById(R.id.finddrug_index);
        IndexListViewAdapter adapter = new IndexListViewAdapter(this, indexStr);
        listView.setAdapter(adapter);
    }


}
