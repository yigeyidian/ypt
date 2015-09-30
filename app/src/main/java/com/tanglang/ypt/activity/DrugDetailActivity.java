package com.tanglang.ypt.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.DrugPagerAdaper;
import com.tanglang.ypt.bean.Drug;
import com.tanglang.ypt.bean.DrugDetail;
import com.tanglang.ypt.fragment.DrugCommFragment;
import com.tanglang.ypt.fragment.DrugPriceFragment;
import com.tanglang.ypt.utils.BitmapHelper;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Author： Administrator
 */
public class DrugDetailActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout frameLayout;
    private View loadingView;
    private View loadingFailView;
    private Drug drug;
    private RadioGroup tab;
    private ViewPager viewPager;
    private Button btComm;
    private Button btPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugdetail);


        initView();
    }

    private void initView() {
        ImageButton btBack = (ImageButton) findViewById(R.id.drug_iv_back);
        Button btFavor = (Button) findViewById(R.id.drug_iv_favor);
        Button btShare = (Button) findViewById(R.id.drug_iv_share);
        frameLayout = (FrameLayout) findViewById(R.id.drug_context);

        btBack.setOnClickListener(this);
        btFavor.setOnClickListener(this);
        btShare.setOnClickListener(this);

        drug = (Drug) getIntent().getSerializableExtra("drug");
        if (drug == null) {
            loadingFailView();
            return;
        }
        initData();
    }

    private void initData() {
        if (loadingView == null) {
            loadingView = View.inflate(this, R.layout.loading_view, null);
        }
        changeView(loadingView);

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getDrugPath(drug._id), new RequestCallBack<String>() {
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

    private void parseData(String data) {
        if (TextUtils.isEmpty(data)) {
            loadingFailView();
            return;
        }
        LogUtils.println(data);

        DrugDetail drugData = new Gson().fromJson(data, DrugDetail.class);

        View loadedView = View.inflate(this, R.layout.drugdetail_view, null);
        ImageView ivImage = (ImageView) loadedView.findViewById(R.id.drugdetail_image);
        TextView ivName = (TextView) loadedView.findViewById(R.id.drugdetail_name);
        TextView ivCompany = (TextView) loadedView.findViewById(R.id.drugdetail_company);
        TextView ivIntro = (TextView) loadedView.findViewById(R.id.drugdetail_intro);
        RatingBar ratingBar = (RatingBar) loadedView.findViewById(R.id.drugdetail_rate);
        LinearLayout llOther = (LinearLayout) loadedView.findViewById(R.id.drugdetail_other);
        viewPager = (ViewPager) loadedView.findViewById(R.id.drugdetail_pager);
        tab = (RadioGroup) loadedView.findViewById(R.id.drugdetail_tab);
        btPrice = (Button) loadedView.findViewById(R.id.drugdetail_price);
        btComm = (Button) loadedView.findViewById(R.id.drugdetail_zixun);

        BitmapHelper.getBitmapUtils(this).display(ivImage, drugData.results.titleimg);
        ivName.setText(drugData.results.namecn);
        ivCompany.setText(drugData.results.refdrugcompanyname);
        ivIntro.setText(drugData.results.gongneng);
        ratingBar.setRating(drugData.results.score);
        addOtherView(llOther, drugData);
        btPrice.setText("最低价￥" + drugData.results.avgprice);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DrugPriceFragment());

        DrugCommFragment drugCommFragment = new DrugCommFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("drug",drugData);
        drugCommFragment.setArguments(bundle);
        fragments.add(drugCommFragment);

        fragments.add(new DrugPriceFragment());
        fragments.add(new DrugPriceFragment());
        DrugPagerAdaper adaper = new DrugPagerAdaper(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adaper);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((RadioButton) tab.getChildAt(position)).setChecked(true);
                switch (position) {
                    case 0:
                        btComm.setVisibility(View.GONE);
                        break;
                    case 1:
                        btComm.setVisibility(View.VISIBLE);
                        btComm.setText("我要点评");
                        break;
                    case 2:
                        btComm.setVisibility(View.VISIBLE);
                        btComm.setText("我要咨询");
                        break;
                    case 3:
                        btComm.setVisibility(View.GONE);
                        break;
                }
            }
        });
        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (((RadioButton) tab.getChildAt(i)).isChecked()) {
                        viewPager.setCurrentItem(i);
                        return;
                    }
                }
            }
        });
        ((RadioButton) tab.getChildAt(0)).setChecked(true);
        btComm.setVisibility(View.GONE);

        changeView(loadedView);
    }

    private void addOtherView(LinearLayout ll, DrugDetail drug) {
        ll.removeAllViews();
        if (drug.results.newotc == 1) {
            ll.addView(createImageView(R.mipmap.ic_my_otc));
        } else if (drug.results.newotc == 2) {
            ll.addView(createImageView(R.mipmap.ic_my_otc2));
        } else if (drug.results.newotc == 3) {
            ll.addView(createImageView(R.mipmap.ic_my_rx));
        }

        if (drug.results.basemed) {
            ll.addView(createImageView(R.mipmap.ic_my_base));
        }
        if (drug.results.medcare == 1) {
            ll.addView(createImageView(R.mipmap.ic_my_csl));
        }
    }

    private ImageView createImageView(int imageId) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setPadding(5, 0, 5, 0);
        imageView.setImageResource(imageId);
        return imageView;
    }

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
            case R.id.drug_iv_back:
                finish();
                break;
            case R.id.drug_iv_favor:
                LogUtils.showToast(this, "收藏成功");
                break;
            case R.id.drug_iv_share:
                LogUtils.showToast(this, "分享成功");
                break;
            case R.id.reloading:
                initData();
                break;
        }
    }
}
