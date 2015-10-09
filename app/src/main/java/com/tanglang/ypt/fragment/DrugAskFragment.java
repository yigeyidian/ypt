package com.tanglang.ypt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.CommentListAdapter;
import com.tanglang.ypt.bean.DrugAskBean;
import com.tanglang.ypt.bean.DrugCommBean;
import com.tanglang.ypt.bean.DrugDetail;
import com.tanglang.ypt.utils.UrlUtils;
import com.tanglang.ypt.utils.ViewUitls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author： Administrator
 */
public class DrugAskFragment extends Fragment {

    private FrameLayout mFrameLayout;
    private View loadingView;
    private View loadingFailView;
    private DrugDetail drug;
    private DrugAskBean drugAskBean;

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
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlUtils.getDrugAsk(drug.results.namecn), new RequestCallBack<String>() {
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

        drugAskBean = new Gson().fromJson(result, DrugAskBean.class);

        View loadedView = View.inflate(getContext(), R.layout.fragment_drugask, null);
        ListView listView = (ListView) loadedView.findViewById(R.id.drugask_listview);
        ListViewAdapter adapter = new ListViewAdapter();
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

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return drugAskBean.results.List.size();
        }

        @Override
        public Object getItem(int position) {
            return drugAskBean.results.List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.askitem, null);
                viewHolder = new ViewHolder();
                viewHolder.tvSex = (TextView) convertView.findViewById(R.id.ask_sex);
                viewHolder.tvAge = (TextView) convertView.findViewById(R.id.ask_age);
                viewHolder.tvAsk = (TextView) convertView.findViewById(R.id.ask_text);
                viewHolder.tvAnswerName = (TextView) convertView.findViewById(R.id.answer_name);
                viewHolder.tvAnswer = (TextView) convertView.findViewById(R.id.answer_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DrugAskBean.DrugAsk ask = drugAskBean.results.List.get(position);
            if (TextUtils.isEmpty(ask.sex)) {
                viewHolder.tvSex.setText("男");
            } else {
                viewHolder.tvSex.setText(ask.sex);
            }

            if (TextUtils.isEmpty(ask.age)) {
                viewHolder.tvAge.setText("0岁");
            } else {
                viewHolder.tvAge.setText(ask.sex + "岁");
            }

            if (TextUtils.isEmpty(ask.mainbody)) {
                viewHolder.tvAsk.setText("未知问题");
            } else {
                viewHolder.tvAsk.setText(ask.mainbody);
            }

            if (TextUtils.isEmpty(ask.doctorname)) {
                viewHolder.tvAnswerName.setText("医生的回答");
            } else {
                viewHolder.tvAnswerName.setText(ask.doctorname + " 医生的回答");
            }

            if (TextUtils.isEmpty(ask.answer)) {
                viewHolder.tvAnswer.setText("");
            } else {
                viewHolder.tvAnswer.setText(ask.answer);
            }

            return convertView;
        }
    }

    class ViewHolder {
        TextView tvSex;
        TextView tvAge;
        TextView tvAsk;
        TextView tvAnswerName;
        TextView tvAnswer;
    }
}
