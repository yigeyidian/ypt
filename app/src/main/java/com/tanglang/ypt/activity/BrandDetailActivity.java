package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.DrugListViewAdapter;
import com.tanglang.ypt.adapter.TypeListAdapter;
import com.tanglang.ypt.bean.Brand;
import com.tanglang.ypt.bean.BrandDataBean;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.view.DrugListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class BrandDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llType;
    private ListView listView;
    private FrameLayout frameLayout;
    private View loadingView;

    private static String[] priceTypeStr = {"不限制", "1-20元", "20-50元", "50-100元", "100-200元", "200元以上"};
    private static String[] yibaoTypeStr = {"不限制", "医保", "非医保"};
    private static String[] jibenTypeStr = {"不限制", "基本药物", "非基本药物"};
    private static String[] brandTypeStr = {"不限制", "品牌"};

    private List<String> typeList;
    private TypeListAdapter listAdapter;
    private Brand brand;
    private View loadingFailView;
    private DrugListView drugListView;
    private BrandDataBean data;
    private DrugListViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branddetail);

        brand = (Brand) getIntent().getSerializableExtra("brand");
        brandTypeStr[1] = brand.namecn;

        initView();
    }

    private void initView() {
        ImageButton ivBack = (ImageButton) findViewById(R.id.branddetail_iv_back);
        Button btSearch = (Button) findViewById(R.id.branddetail_bt_search);
        Button btBrand = (Button) findViewById(R.id.branddetail_bt_brand);
        Button btPrice = (Button) findViewById(R.id.branddetail_bt_price);
        Button btYibao = (Button) findViewById(R.id.branddetail_bt_yibao);
        Button btJibenyao = (Button) findViewById(R.id.branddetail_bt_jibenyao);
        TextView tvFind = (TextView) findViewById(R.id.branddetail_tv_find);

        llType = (LinearLayout) findViewById(R.id.type_ll);
        frameLayout = (FrameLayout) findViewById(R.id.drug_context);
        listView = (ListView) findViewById(R.id.type_list);

        typeList = new ArrayList<>();
        addStringArray(brandTypeStr);
        listAdapter = new TypeListAdapter(this, typeList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                llType.setVisibility(View.INVISIBLE);
            }
        });
        tvFind.setText(brand.namecn);
        llType.setVisibility(View.INVISIBLE);

        ivBack.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        btBrand.setOnClickListener(this);
        btPrice.setOnClickListener(this);
        btYibao.setOnClickListener(this);
        btJibenyao.setOnClickListener(this);

        initData();
    }

    private void addStringArray(String[] str) {
        typeList.clear();
        for (int i = 0; i < str.length; i++) {
            typeList.add(str[i]);
        }
    }

    private void initData() {
        if (loadingView == null) {
            loadingView = View.inflate(this, R.layout.loading_view, null);
        }
        changeView(loadingView);

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getBrandPath(brand.namecn), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                parseData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingFailView();
                e.printStackTrace();
            }
        });
    }


    private void parseData(String result) {
        if (TextUtils.isEmpty(result)) {
            loadingFailView();
            return;
        }
        LogUtils.println(result);
        data = new Gson().fromJson(result, BrandDataBean.class);
        if (data.results == null) {
            loadingFailView();
            return;
        }
        drugListView = (DrugListView) View.inflate(this, R.layout.drug_gridview, null);
        //drugListView = (DrugListView) view.findViewById(R.id.gridview);

        gridViewAdapter = new DrugListViewAdapter(this, data.results.List);
        drugListView.setAdapter(gridViewAdapter);
        drugListView.setOnLoadingLishener(new DrugListView.OnLoadingListener() {
            @Override
            public void loadMore() {
                loadMoreData();
            }
        });

        drugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BrandDetailActivity.this, DrugDetailActivity.class);
                intent.putExtra("drug", data.results.List.get(position));
                startActivity(intent);
            }
        });
        changeView(drugListView);
    }

    private void loadMoreData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getBrandPath(brand.namecn), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (drugListView != null) {
                    drugListView.onRefrshComplete();
                }
                parseMoreDate(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                if (drugListView != null) {
                    drugListView.onRefrshComplete();
                }
                e.printStackTrace();
            }
        });
    }

    private void parseMoreDate(String result) {
        BrandDataBean moreData = new Gson().fromJson(result, BrandDataBean.class);
        if (moreData.results == null) {
            loadingFailView();
            return;
        }

        data.results.List.addAll(moreData.results.List);
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 加载失败布局
     */
    private void loadingFailView() {
        if (loadingFailView == null) {
            loadingFailView = View.inflate(this, R.layout.loadingfail_view, null);
            Button btReLoad = (Button) loadingFailView.findViewById(R.id.reloading);

            btReLoad.setOnClickListener(this);
        }
        changeView(loadingFailView);
    }

    private void changeView(View view) {
        frameLayout.removeAllViews();
        frameLayout.addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.branddetail_iv_back:
                finish();
                break;
            case R.id.branddetail_bt_search:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.branddetail_bt_brand:
                showTypeList(brandTypeStr);
                break;
            case R.id.branddetail_bt_price:
                showTypeList(priceTypeStr);
                break;

            case R.id.branddetail_bt_yibao:
                showTypeList(yibaoTypeStr);
                break;

            case R.id.branddetail_bt_jibenyao:
                showTypeList(jibenTypeStr);
                break;
            case R.id.reloading:
                initData();
                break;
        }
    }

    private void showTypeList(String[] list) {
        if (typeList.get(1).equals(list[1])) {
            if (llType.getVisibility() == View.VISIBLE) {
                llType.setVisibility(View.INVISIBLE);
                return;
            }
        }
        addStringArray(list);
        listAdapter.notifyDataSetChanged();
        llType.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (llType.getVisibility() == View.VISIBLE) {
            llType.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
