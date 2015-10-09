package com.tanglang.ypt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.DrugDetail;
import com.tanglang.ypt.utils.BitmapHelper;

import java.util.List;

/**
 * Author： Administrator
 */
public class SameDrugGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<DrugDetail.SameDrug> mData;
    private final BitmapUtils bitmapUtils;

    public SameDrugGridAdapter(Context context, List<DrugDetail.SameDrug> data) {
        this.mContext = context;
        this.mData = data;
        bitmapUtils = BitmapHelper.getBitmapUtils(context);
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
        View view = View.inflate(mContext, R.layout.samedrug_item, null);
        ImageView ivImage = (ImageView) view.findViewById(R.id.samedrug_item_image);
        TextView tvName = (TextView) view.findViewById(R.id.samedrug_item_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.samedrug_item_price);

        DrugDetail.SameDrug drug = mData.get(position);
        bitmapUtils.display(ivImage, drug.listimg);
        tvName.setText(drug.namecn);
        if (drug.avgprice == 0) {
            tvPrice.setText("暂无报价");
        } else {
            tvPrice.setText("￥" + drug.avgprice);
        }
        return view;
    }

}
