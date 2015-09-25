package com.tanglang.ypt.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
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
import com.tanglang.ypt.adapter.BannerImagePagerAdapter;
import com.tanglang.ypt.adapter.HomeCompanyAdapter;
import com.tanglang.ypt.adapter.TypeGridAdapter;
import com.tanglang.ypt.bean.Company;
import com.tanglang.ypt.bean.HomeLayoutBean;
import com.tanglang.ypt.utils.BitmapHelper;
import com.tanglang.ypt.utils.CacheUtils;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.view.HomeGridView;
import com.tanglang.ypt.view.YButton;

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
public class HomeFragment extends Fragment {
    private FrameLayout mFrameLayout;
    private View loadingView;

    private HomeLayoutBean mLayoutData;
    private List<Company> companyList;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.home_content);

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

            btReLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLayoutData();
                }
            });
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
                companyList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Company company = new Company();
                    company.namecn = jsonArray.optJSONObject(i).optString("namecn");
                    company.titleimg = jsonArray.optJSONObject(i).optString("titleimg");
                    companyList.add(company);
                }
                CacheUtils.saveCache(getActivity(), UrlUtils.BRAND_PATH, data);
                initCompany();
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

        bannerViewPager = (ViewPager) loadSuccesView.findViewById(R.id.homeloaded_vp_banner);
        radioGroup = (RadioGroup) loadSuccesView.findViewById(R.id.homeloaded_rg);
        ivLeftBanner = (ImageView) loadSuccesView.findViewById(R.id.homeloaded_iv_left);
        ivRightBanner = (ImageView) loadSuccesView.findViewById(R.id.homeloaded_iv_right);
        companyView = (HomeGridView) loadSuccesView.findViewById(R.id.homeloaded_rv_company);
        typeView = (LinearLayout) loadSuccesView.findViewById(R.id.homeloaded_lv_drug);

        initBanner();
        initType();
        initCompany();

        startTimer();
        changeView(loadSuccesView);
    }

    private void initType() {
        typeView.addView(createType(mLayoutData.list.types.get(0).type_name, mLayoutData.list.types.get(0).drug_list));
        typeView.addView(createType(mLayoutData.list.types.get(1).type_name, mLayoutData.list.types.get(1).drug_list));
    }

    private View createType(String type, List<HomeLayoutBean.Drug> data) {
        View typeView = View.inflate(getActivity(), R.layout.hometype_view, null);
        YButton button = (YButton) typeView.findViewById(R.id.type_button);
        HomeGridView gridView = (HomeGridView) typeView.findViewById(R.id.tyoe_gridview);
        button.setText(type);
        TypeGridAdapter typeGridAdapter = new TypeGridAdapter(getActivity(), data);
        gridView.setAdapter(typeGridAdapter);
        return typeView;
    }

    private void initBanner() {
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
    }

    private void initCompany() {
        if (companyView != null && companyList != null && companyList.size() > 0) {
            List<Company> homeCompanies;
            if (companyList.size() > 6) {
                homeCompanies = companyList.subList(0, 6);
            } else {
                homeCompanies = companyList;
            }
            HomeCompanyAdapter companyAdapter = new HomeCompanyAdapter(getContext(), homeCompanies);
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
}
