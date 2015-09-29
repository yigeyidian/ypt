package com.tanglang.ypt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.DrugGridAdapter;
import com.tanglang.ypt.bean.BannerDetailBean;
import com.tanglang.ypt.bean.HomeLayoutBean;
import com.tanglang.ypt.utils.BitmapHelper;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.view.HomeGridView;

/**
 *
 */
public class BannerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private FrameLayout mFrameLayout;
    private HomeLayoutBean.Banner banner;
    private View loadingFailView;
    private View loadingView;
    private TextView tvTitle;
    private BannerDetailBean bannerDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        ivBack = (ImageView) findViewById(R.id.banner_iv_back);
        tvTitle = (TextView) findViewById(R.id.banner_tv_title);
        mFrameLayout = (FrameLayout) findViewById(R.id.banner_context);

        Intent intent = getIntent();
        banner = (HomeLayoutBean.Banner) intent.getSerializableExtra("banner");
        if (banner == null) {
            return;
        }
        if (banner.type.equals("0")) {
            initWebView(banner.target);
        } else {
            tvTitle.setText(banner.title);
            getLayoutData();
        }

        ivBack.setOnClickListener(this);
    }

    private void initWebView(String url) {
        if (TextUtils.isEmpty(url)) {
            loadingFailView();
            return;
        }
        WebView mWebView = new WebView(this);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        WebSettings settings = mWebView.getSettings();
        //设置开启JavaScript
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
        changeView(mWebView);
    }

    /**
     * 加载失败布局
     */
    private void loadingFailView() {
        if (loadingFailView == null) {
            loadingFailView = View.inflate(this, R.layout.loadingfail_view, null);
            Button btReLoad = (Button) loadingFailView.findViewById(R.id.reloading);

            btReLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLayoutData();
                }
            });
        }
        changeView(loadingFailView);
    }


    /**
     * 改变布局
     *
     * @param view
     */
    private void changeView(View view) {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }

    public void getLayoutData() {
        if (banner.target.equals("0")) {
            return;
        }
        if (loadingView == null) {
            loadingView = View.inflate(this, R.layout.loading_view, null);
        }
        changeView(loadingView);
        String url;
        if (banner.target.equals("1")) {
            url = UrlUtils.FAMILY_BOX;
        } else if (banner.target.equals("2")) {
            url = UrlUtils.WEI_GAI;
        } else {
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                paserLayoutData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingFailView();
                e.printStackTrace();
            }
        });
    }

    private void paserLayoutData(String data) {
        if (TextUtils.isEmpty(data)) {
            loadingFailView();
            return;
        }

        bannerDetailData = new Gson().fromJson(data, BannerDetailBean.class);

        View loadSuccesView = View.inflate(this, R.layout.banner_loadingsucc, null);
        ImageView ivImage = (ImageView) loadSuccesView.findViewById(R.id.banner_image);
        HomeGridView gridView = (HomeGridView) loadSuccesView.findViewById(R.id.banner_gv);
        BitmapHelper.getBitmapUtils(this).display(ivImage, bannerDetailData.list.banner_image_url);
        DrugGridAdapter adapter = new DrugGridAdapter(this, bannerDetailData.list.drug_list);
        gridView.setAdapter(adapter);

        changeView(loadSuccesView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_iv_back:
                BannerActivity.this.finish();
                break;
        }
    }
}
