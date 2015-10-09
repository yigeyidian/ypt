package com.tanglang.ypt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.CommentListAdapter;
import com.tanglang.ypt.bean.DrugCommBean;
import com.tanglang.ypt.bean.DrugDetail;
import com.tanglang.ypt.utils.LogUtils;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.utils.ViewUitls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author： Administrator
 */
public class DrugCommFragment extends Fragment {


    private FrameLayout mFrameLayout;
    private View loadingView;
    private View loadingFailView;
    private DrugDetail drug;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_framelayout, container, false);

        mFrameLayout = (FrameLayout) view.findViewById(R.id.drugcomm_content);
        drug = (DrugDetail) getArguments().getSerializable("drug");
        initData();
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
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getDrugComments(drug.results._id), new RequestCallBack<String>() {
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

        try {
            JSONObject dataJson = new JSONObject(result);
            if (dataJson.optString("status").equals("Err")) {
                loadingFailView();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DrugCommBean drugCommBean = new Gson().fromJson(result, DrugCommBean.class);

        View loadedView = View.inflate(getContext(), R.layout.fragment_drugcomm, null);
        ListView listView = (ListView) loadedView.findViewById(R.id.drugcomm_list);
        CommentListAdapter adapter = new CommentListAdapter(getActivity(), drugCommBean.results.List);
        listView.setAdapter(adapter);
        ViewUitls.setListViewHeightBasedOnChildren(listView);
        changeView(loadedView);
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
