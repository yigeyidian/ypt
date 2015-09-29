package com.tanglang.ypt.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.activity.BannerActivity;
import com.tanglang.ypt.activity.BrandActivity;
import com.tanglang.ypt.activity.FindDrugActivity;
import com.tanglang.ypt.adapter.BannerImagePagerAdapter;
import com.tanglang.ypt.adapter.BrandGridViewAdapter;
import com.tanglang.ypt.adapter.DrugGridAdapter;
import com.tanglang.ypt.bean.Brand;
import com.tanglang.ypt.bean.Drug;
import com.tanglang.ypt.bean.HomeLayoutBean;
import com.tanglang.ypt.utils.BitmapHelper;
import com.tanglang.ypt.utils.CacheUtils;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UiUtils;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.view.HomeGridView;
import com.tanglang.ypt.view.TopSearchView;
import com.tanglang.ypt.view.YButton;
import com.tanglang.ypt.view.YPScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private FrameLayout mFrameLayout;
    private View loadingView;

    private HomeLayoutBean mLayoutData;
    private ArrayList<Brand> brandList;
    private RadioGroup radioGroup;
    private View loadingFailView;
    private View loadSuccesView;
    private ViewPager bannerViewPager;

    private static int TIMER_WHAT = 0x111;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIMER_WHAT && bannerViewPager != null) {
                int currentItem = (bannerViewPager.getCurrentItem() + 1) % banners.size();
                bannerViewPager.setCurrentItem(currentItem);
            }
        }
    };
    private List<HomeLayoutBean.Banner> banners;
    private Timer timer;
    private List<HomeLayoutBean.Banner> banners1;
    private HomeGridView companyView;
    private ImageView ivLeftBanner;
    private ImageView ivRightBanner;
    private LinearLayout typeView;
    private TopSearchView topSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.home_content);
        topSearchView = (TopSearchView) view.findViewById(R.id.home_topmenu);

        getLayoutData();
        getBrandData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
        timer = null;
        handler.removeMessages(TIMER_WHAT);

    }

    /**
     * 获取合作品牌数据
     */
    private void getBrandData() {
        String cacheBrandData = CacheUtils.getCache(getContext(), UrlUtils.BRAND_PATH);
        if (!TextUtils.isEmpty(cacheBrandData)) {
            paserBrandData(cacheBrandData);
            return;
        }

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.BRAND_PATH, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                paserBrandData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    /**
     * 获取布局数据
     */
    public void getLayoutData() {
        if (loadingView == null) {
            loadingView = View.inflate(getActivity(), R.layout.loading_view, null);
        }
        changeView(loadingView);

        final String cacheLayoutData = CacheUtils.getCache(getContext(), UrlUtils.HOME_LAYOUT);
        if (!TextUtils.isEmpty(cacheLayoutData)) {
            paserLayoutData(cacheLayoutData);
        }

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.HOME_LAYOUT, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                paserLayoutData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                if (TextUtils.isEmpty(cacheLayoutData)) {
                    loadingFailView();
                }
            }
        });
    }


    /**
     * 加载失败布局
     */
    private void loadingFailView() {
        if (loadingFailView == null) {
            loadingFailView = View.inflate(getContext(), R.layout.loadingfail_view, null);
            Button btReLoad = (Button) loadingFailView.findViewById(R.id.reloading);

            btReLoad.setOnClickListener(this);
        }

        changeView(loadingFailView);
    }

    private void changeView(View view) {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }

    private void paserBrandData(String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            if (dataJson.optString("status").equals("Ok")) {
                JSONArray jsonArray = dataJson.optJSONArray("results");
                brandList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Brand company = new Brand();
                    company.namecn = jsonArray.optJSONObject(i).optString("namecn");
                    company.titleimg = jsonArray.optJSONObject(i).optString("titleimg");
                    brandList.add(company);
                }
                CacheUtils.saveCache(getActivity(), UrlUtils.BRAND_PATH, data);
                initBrand();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析布局数据
     *
     * @param data
     */
    private void paserLayoutData(String data) {
        if (TextUtils.isEmpty(data)) {
            loadingFailView();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.optInt("code") != 0) {
                loadingFailView();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CacheUtils.saveCache(getContext(), UrlUtils.HOME_LAYOUT, data);
        mLayoutData = new Gson().fromJson(data, HomeLayoutBean.class);

        loadSuccesView = View.inflate(getContext(), R.layout.homeloaded_view, null);

        Button btScan = (Button) loadSuccesView.findViewById(R.id.homeloaded_bt_scan);
        Button btDoctor = (Button) loadSuccesView.findViewById(R.id.homeloaded_bt_doctor);
        Button btRemind = (Button) loadSuccesView.findViewById(R.id.homeloaded_bt_remind);
        Button btFind = (Button) loadSuccesView.findViewById(R.id.homeloaded_bt_find);
        btScan.setOnClickListener(this);
        btDoctor.setOnClickListener(this);
        btRemind.setOnClickListener(this);
        btFind.setOnClickListener(this);


        companyView = (HomeGridView) loadSuccesView.findViewById(R.id.homeloaded_rv_company);
        typeView = (LinearLayout) loadSuccesView.findViewById(R.id.homeloaded_lv_drug);
        YPScrollView scrollView = (YPScrollView) loadSuccesView.findViewById(R.id.homeloaded_sv);
        scrollView.setScrollViewListener(new YPScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(YPScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y > UiUtils.dip2px(200)) {
                    topSearchView.setBackgroundColor(Color.parseColor("#aa307FE2"));
                } else {
                    topSearchView.setBackgroundResource(R.drawable.topview_back);
                }
            }
        });
        topSearchView.setBackgroundResource(R.drawable.topview_back);

        initBanner();
        initType();
        initBrand();

        startTimer();
        changeView(loadSuccesView);
    }

    private void initType() {
        typeView.addView(createType(mLayoutData.list.types.get(0).type_name, mLayoutData.list.types.get(0).drug_list));
        typeView.addView(createType(mLayoutData.list.types.get(1).type_name, mLayoutData.list.types.get(1).drug_list));
    }

    private View createType(String type, List<Drug> data) {
        View typeView = View.inflate(getActivity(), R.layout.hometype_view, null);
        YButton button = (YButton) typeView.findViewById(R.id.type_button);
        HomeGridView gridView = (HomeGridView) typeView.findViewById(R.id.tyoe_gridview);
        button.setText(type);
        DrugGridAdapter typeGridAdapter = new DrugGridAdapter(getActivity(), data);
        gridView.setAdapter(typeGridAdapter);
        return typeView;
    }

    private void initBanner() {
        bannerViewPager = (ViewPager) loadSuccesView.findViewById(R.id.homeloaded_vp_banner);
        radioGroup = (RadioGroup) loadSuccesView.findViewById(R.id.homeloaded_rg);
        ivLeftBanner = (ImageView) loadSuccesView.findViewById(R.id.homeloaded_iv_left);
        ivRightBanner = (ImageView) loadSuccesView.findViewById(R.id.homeloaded_iv_right);
        banners = new ArrayList<>();
        banners1 = new ArrayList<>();
        for (int i = 0; i < mLayoutData.list.banner.size(); i++) {
            if (mLayoutData.list.banner.get(i).type.equals("0")) {
                banners.add(mLayoutData.list.banner.get(i));
            } else {
                banners1.add(mLayoutData.list.banner.get(i));
            }
        }
        BannerImagePagerAdapter pagerAdapter = new BannerImagePagerAdapter(getContext(), banners);
        bannerViewPager.setAdapter(pagerAdapter);
        bannerViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((RadioButton) (radioGroup.getChildAt(position))).setChecked(true);
            }
        });

        ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);

        BitmapHelper.getBitmapUtils(getContext()).display(ivLeftBanner, banners1.get(0).image_url);
        BitmapHelper.getBitmapUtils(getContext()).display(ivRightBanner, banners1.get(1).image_url);

        ivLeftBanner.setOnClickListener(this);
        ivRightBanner.setOnClickListener(this);
    }

    private void initBrand() {
        YButton btBrand = (YButton) loadSuccesView.findViewById(R.id.homeloaded_bt_brand);
        btBrand.setOnClickListener(this);
        if (companyView != null && brandList != null && brandList.size() > 0) {
            List<Brand> homeCompanies;
            if (brandList.size() > 6) {
                homeCompanies = brandList.subList(0, 6);
            } else {
                homeCompanies = brandList;
            }
            BrandGridViewAdapter companyAdapter = new BrandGridViewAdapter(getContext(), homeCompanies);
            companyView.setAdapter(companyAdapter);
        }
    }

    private void startTimer() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIMER_WHAT);
            }
        }, 0, 5000);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homeloaded_iv_left:
                intent = new Intent(getActivity(), BannerActivity.class);
                intent.putExtra("banner", banners1.get(0));
                startActivity(intent);
                break;
            case R.id.homeloaded_iv_right:
                intent = new Intent(getActivity(), BannerActivity.class);
                intent.putExtra("banner", banners1.get(1));
                startActivity(intent);
                break;

            case R.id.reloading:
                getLayoutData();
                break;
            case R.id.homeloaded_bt_brand:
                intent = new Intent(getActivity(), BrandActivity.class);
                intent.putExtra("brands", brandList);
                startActivity(intent);
                break;

            case R.id.homeloaded_bt_scan:
                LogUtils.showToast(getActivity(), "扫码找药");
                break;
            case R.id.homeloaded_bt_doctor:
                LogUtils.showToast(getActivity(), "咨询医生");
                break;
            case R.id.homeloaded_bt_remind:
                LogUtils.showToast(getActivity(), "服药提醒");
                break;
            case R.id.homeloaded_bt_find:
                //LogUtils.showToast(getActivity(), "对症找药");
                intent = new Intent(getActivity(), FindDrugActivity.class);
                startActivity(intent);
                break;

        }
    }
}
