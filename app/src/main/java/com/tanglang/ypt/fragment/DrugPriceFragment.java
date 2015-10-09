package com.tanglang.ypt.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.activity.DrugDetailActivity;
import com.tanglang.ypt.adapter.CommentListAdapter;
import com.tanglang.ypt.adapter.DrugPriceListViewAdapter;
import com.tanglang.ypt.adapter.SameDrugGridAdapter;
import com.tanglang.ypt.bean.DrugCommBean;
import com.tanglang.ypt.bean.DrugDetail;
import com.tanglang.ypt.bean.DrugPriceBean;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.utils.ViewUitls;
import com.tanglang.ypt.view.CheckView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator
 */
public class DrugPriceFragment extends Fragment {

    private DrugDetail drug;
    private GridLayout glTypes;

    private boolean flag;
    private LinearLayout llType;
    private FrameLayout mFrameLayout;
    private View loadingView;
    private View loadingFailView;

    private List<DrugPriceBean.DrugShop> list;
    private DrugPriceBean drugPriceBean;
    private ListView listView;
    private DrugPriceListViewAdapter listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_framelayout, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.drugcomm_content);

        Bundle arguments = getArguments();
        if (arguments != null) {
            drug = (DrugDetail) arguments.getSerializable("drug");
            initData();
        }
        return view;
    }

    private void initData() {
        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.loading_view, null);
        }
        changeView(loadingView);
        if (drug == null) {
            loadingFailView();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getDrugPrice(drug.results._id, "成都市"), new RequestCallBack<String>() {
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
        try {
            JSONObject dataJson = new JSONObject(data);
            if (dataJson.optString("status").equals("Err")) {
                loadingFailView();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        drugPriceBean = new Gson().fromJson(data, DrugPriceBean.class);

        View loadedView = View.inflate(getContext(), R.layout.fragment_drugprice, null);
        changeView(loadedView);
        llType = (LinearLayout) loadedView.findViewById(R.id.drugprice_bt_type);
        glTypes = (GridLayout) loadedView.findViewById(R.id.drugprice_types);
        listView = (ListView) loadedView.findViewById(R.id.drugprice_pricelist);
        GridView gridView = (GridView) loadedView.findViewById(R.id.drugprice_samedrug);


        list = new ArrayList<>();
        listViewAdapter = new DrugPriceListViewAdapter(getContext(), list);
        listView.setAdapter(listViewAdapter);

        showSmallType();


        SameDrugGridAdapter sameDrugGridAdapter = new SameDrugGridAdapter(getContext(), drug.results.SameDrugs);
        gridView.setAdapter(sameDrugGridAdapter);

        llType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    showAllType();
                    flag = false;
                } else {
                    showSmallType();
                    flag = true;
                }
            }
        });
    }

    private void showSmallType() {
        glTypes.removeAllViews();
        for (int i = 0; i < 2; i++) {
            final String str = drug.results.refspecifications.get(i);
            CheckView checkView = new CheckView(getContext());
            checkView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            checkView.setText(str);
            glTypes.addView(checkView);
            checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectType();
                    ((CheckView) v).setCheck(true);
                    showShop(str);
                }
            });
        }
        ((ImageView) llType.getChildAt(2)).setImageResource(R.mipmap.ic_arrow_up);
        ((CheckView) glTypes.getChildAt(0)).setCheck(true);
        showShop(drug.results.refspecifications.get(0));
    }

    private void showAllType() {
        glTypes.removeAllViews();
        for (int i = 0; i < drug.results.refspecifications.size(); i++) {
            final String str = drug.results.refspecifications.get(i);
            CheckView checkView = new CheckView(getContext());
            checkView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            checkView.setText(str);
            glTypes.addView(checkView);
            checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectType();
                    ((CheckView) v).setCheck(true);
                    showShop(str);
                }
            });
        }
        ((ImageView) llType.getChildAt(2)).setImageResource(R.mipmap.ic_arrow_down);
        ((CheckView) glTypes.getChildAt(0)).setCheck(true);
        showShop(drug.results.refspecifications.get(0));
    }

    private void showShop(String str) {
        list.clear();
        float minPrice = 10000000;
        String weburl = "";
        for (int i = 0; i < drugPriceBean.results.Online.size(); i++) {
            if (drugPriceBean.results.Online.get(i).refspecifications.equals(str)) {
                list.add(drugPriceBean.results.Online.get(i));
                if (drugPriceBean.results.Online.get(i).price < minPrice) {
                    minPrice = drugPriceBean.results.Online.get(i).price;
                    weburl = drugPriceBean.results.Online.get(i).saleurl;
                }
            }
        }
        listViewAdapter.notifyDataSetChanged();
        ViewUitls.setListViewHeightBasedOnChildren(listView);
        ((DrugDetailActivity) getActivity()).setBtPrice(minPrice);
        ((DrugDetailActivity) getActivity()).setWebUrl(weburl);
    }

    private void selectType() {
        for (int i = 0; i < glTypes.getChildCount(); i++) {
            ((CheckView) glTypes.getChildAt(i)).setCheck(false);
        }
    }

    private void changeView(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }

    private void loadingFailView() {
        if (loadingFailView == null) {
            loadingFailView = View.inflate(getContext(), R.layout.loadingfail_view, null);
            Button btReLoad = (Button) loadingFailView.findViewById(R.id.reloading);
            btReLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
        }

        changeView(loadingFailView);
    }

}
