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
public class DrugListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Drug> mData;
    private BitmapUtils bitmapUtils;

    public DrugListViewAdapter(Context context, List<Drug> data) {
        this.mContext = context;
        this.mData = data;
        bitmapUtils = BitmapHelper.getBitmapUtils(context);
    }

    @Override
    public int getCount() {
        return mData.size() / 2;
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
            viewHolder.ivLeftImage = (ImageView) convertView.findViewById(R.id.drug_leftimage);
            viewHolder.tvLeftText = (TextView) convertView.findViewById(R.id.drug_leftname);
            viewHolder.tvLeftPrice = (TextView) convertView.findViewById(R.id.drug_leftprice);
            viewHolder.llLeftOther = (LinearLayout) convertView.findViewById(R.id.drug_leftother);

            viewHolder.ivRightImage = (ImageView) convertView.findViewById(R.id.drug_rightimage);
            viewHolder.tvRightText = (TextView) convertView.findViewById(R.id.drug_rightname);
            viewHolder.tvRightPrice = (TextView) convertView.findViewById(R.id.drug_rightprice);
            viewHolder.llRightOther = (LinearLayout) convertView.findViewById(R.id.drug_rightother);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Drug leftDrug = mData.get(position * 2);
        Drug rightDrug = mData.get(position * 2 + 1);
        bitmapUtils.display(viewHolder.ivLeftImage, leftDrug.titleimg);
        if (!TextUtils.isEmpty(leftDrug.namecn)) {
            viewHolder.tvLeftText.setText(leftDrug.namecn);
        }
        if (!TextUtils.isEmpty(leftDrug.avgprice)) {
            viewHolder.tvLeftPrice.setText("￥" + leftDrug.avgprice);
        }

        bitmapUtils.display(viewHolder.ivRightImage, rightDrug.titleimg);
        if (!TextUtils.isEmpty(leftDrug.namecn)) {
            viewHolder.tvRightText.setText(rightDrug.namecn);
        }
        if (!TextUtils.isEmpty(leftDrug.avgprice)) {
            viewHolder.tvRightPrice.setText("￥" + rightDrug.avgprice);
        }

        addOtherView(viewHolder.llLeftOther, leftDrug);
        addOtherView(viewHolder.llRightOther, rightDrug);
        return convertView;
    }

    private void addOtherView(LinearLayout ll, Drug drug) {
        ll.removeAllViews();
        if (drug.newotc.equals("1")) {
            ll.addView(createImageView(R.mipmap.ic_my_otc));
        } else if (drug.newotc.equals("2")) {
            ll.addView(createImageView(R.mipmap.ic_my_otc2));
        } else if (drug.newotc.equals("3")) {
            ll.addView(createImageView(R.mipmap.ic_my_rx));
        }

        if (drug.basemed.equals("1")) {
            ll.addView(createImageView(R.mipmap.ic_my_base));
        }
        if (drug.medcare.equals("1")) {
            ll.addView(createImageView(R.mipmap.ic_my_csl));
        }
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
        ImageView ivLeftImage;
        TextView tvLeftText;
        LinearLayout llLeftOther;
        TextView tvLeftPrice;

        ImageView ivRightImage;
        TextView tvRightText;
        LinearLayout llRightOther;
        TextView tvRightPrice;
    }
}
