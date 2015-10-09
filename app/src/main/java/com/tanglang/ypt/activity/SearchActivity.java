package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.DrugSearchBean;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author： Administrator
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private DrugSearchBean drugSearchBean;
    private ListView listView;
    private ListViewAdapter adapter;
    private ProgressBar loading;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageView ivBack = (ImageView) findViewById(R.id.search_iv_back);
        etInput = (EditText) findViewById(R.id.search_et_input);
        LinearLayout llScan = (LinearLayout) findViewById(R.id.search_ll_scan);
        loading = (ProgressBar) findViewById(R.id.search_loading);
        listView = (ListView) findViewById(R.id.search_listview);
        ImageView ivSearch = (ImageView) findViewById(R.id.search_iv_search);

        searchFinish();

        ivBack.setOnClickListener(this);
        llScan.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.println("onTextChanged:" + s);
                getData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getData(String key) {
        loading.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getDrugWords(key), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                parseData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                searchFinish();
                e.printStackTrace();
            }
        });
    }

    private void parseData(String data) {
        if (TextUtils.isEmpty(data)) {
            searchFinish();
            return;
        }
        try {
            JSONObject dataJson = new JSONObject(data);
            if (dataJson.optString("status").equals("Err")) {
                searchFinish();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DrugSearchBean resultData = new Gson().fromJson(data, DrugSearchBean.class);
        if (listView != null) {
            drugSearchBean = resultData;
            adapter = new ListViewAdapter();
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String key = drugSearchBean.results.get(position).showname;
                    Intent intent = new Intent(SearchActivity.this, DrugsActivity.class);
                    intent.putExtra("key", key);
                    startActivity(intent);
                }
            });
        } else {
            drugSearchBean.results.clear();
            drugSearchBean.results.addAll(resultData.results);
            adapter.notifyDataSetChanged();
        }
        searchFinish();
    }

    private void searchFinish() {
        loading.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_search:
                getData(etInput.getText().toString());
                break;
            case R.id.search_ll_scan:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
        }
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return drugSearchBean.results.size();
        }

        @Override
        public Object getItem(int position) {
            return drugSearchBean.results.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(SearchActivity.this, R.layout.searchresult_item, null);
            TextView tvText = (TextView) view.findViewById(R.id.searchitem_text);
            TextView tvCount = (TextView) view.findViewById(R.id.searchitem_count);
            tvText.setText(drugSearchBean.results.get(position).showname);
            tvCount.setText(drugSearchBean.results.get(position).recordcount + "");
            return view;
        }
    }
}
