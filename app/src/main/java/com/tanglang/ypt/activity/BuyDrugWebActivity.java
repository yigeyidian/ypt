package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.Drug;
import com.tanglang.ypt.bean.DrugDetail;

/**
 * Author： Administrator
 */
public class BuyDrugWebActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydrug);

        String weburl = getIntent().getStringExtra("weburl");
        Drug drug = (Drug) getIntent().getSerializableExtra("drug");

        TextView tvTitle = (TextView) findViewById(R.id.buydrug_title);
        ImageButton btBack = (ImageButton) findViewById(R.id.buydrug_back);
        WebView webView = (WebView) findViewById(R.id.buydrug_webview);

        tvTitle.setText(drug.namecn);

        webView.loadUrl(weburl);
        //浏览器设置
        WebSettings settings = webView.getSettings();
        //设置开启JavaScript
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyDrugWebActivity.this.finish();
            }
        });
    }
}
