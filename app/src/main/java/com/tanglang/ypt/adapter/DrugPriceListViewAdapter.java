package com.tanglang.ypt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.DrugPriceBean;

import java.util.List;
import java.util.zip.Inflater;

import static com.tanglang.ypt.R.id.price_button;

/**
 * Author： Administrator
 */
public class DrugPriceListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<DrugPriceBean.DrugShop> mData;

    public DrugPriceListViewAdapter(Context context, List<DrugPriceBean.DrugShop> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.priceitem, null);
        TextView tvName = (TextView) view.findViewById(R.id.price_text);
        Button button = (Button) view.findViewById(price_button);
        tvName.setText(mData.get(position).refpharmaciesnamecn);
        button.setText("￥" + mData.get(position).price);
        return view;
    }
}
