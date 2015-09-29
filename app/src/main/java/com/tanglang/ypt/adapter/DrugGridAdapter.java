package com.tanglang.ypt.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.Drug;
import com.tanglang.ypt.utils.BitmapHelper;

import java.util.List;

/**
 * Author： Administrator
 */
public class DrugGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Drug> mData;
    private BitmapUtils bitmapUtils;

    public DrugGridAdapter(Context context, List<Drug> data) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.drug_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.drug_image);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.drug_name);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.drug_price);
            viewHolder.llOther = (LinearLayout) convertView.findViewById(R.id.drug_other);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Drug drug = mData.get(position);
        bitmapUtils.display(viewHolder.ivImage, drug.TitleImg);
        if (!TextUtils.isEmpty(drug.NameCN)) {
            viewHolder.tvText.setText(drug.NameCN);
        }
        if (!TextUtils.isEmpty(drug.AvgPrice)) {
            viewHolder.tvPrice.setText("￥" + drug.AvgPrice);
        }

        viewHolder.llOther.removeAllViews();
        if (drug.NewOTC.equals("1")) {
            viewHolder.llOther.addView(createImageView(R.mipmap.ic_my_otc));
        } else if (drug.NewOTC.equals("2")) {
            viewHolder.llOther.addView(createImageView(R.mipmap.ic_my_otc2));
        } else if (drug.NewOTC.equals("3")) {
            viewHolder.llOther.addView(createImageView(R.mipmap.ic_my_rx));
        }

        if (drug.BaseMed.equals("1")) {
            viewHolder.llOther.addView(createImageView(R.mipmap.ic_my_base));
        }
        if (drug.MedCare.equals("1")) {
            viewHolder.llOther.addView(createImageView(R.mipmap.ic_my_csl));
        }
        return convertView;
    }

    private ImageView createImageView(int imageId) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setPadding(5, 0, 5, 0);
        imageView.setImageResource(imageId);
        return imageView;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvText;
        LinearLayout llOther;
        TextView tvPrice;
    }
}
