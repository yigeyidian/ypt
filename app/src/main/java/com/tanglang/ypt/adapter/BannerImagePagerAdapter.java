package com.tanglang.ypt.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tanglang.ypt.activity.BannerActivity;
import com.tanglang.ypt.bean.HomeLayoutBean;
import com.tanglang.ypt.utils.BitmapHelper;

import java.util.List;

/**
 * Author： Administrator
 */
public class BannerImagePagerAdapter extends PagerAdapter {
    private List<HomeLayoutBean.Banner> mData;
    private Context mContext;

    public BannerImagePagerAdapter(Context context, List<HomeLayoutBean.Banner> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final HomeLayoutBean.Banner banner = mData.get(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        BitmapHelper.getBitmapUtils(mContext).display(imageView, banner.image_url);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!banner.target.equals("2")) {
                    Intent intent = new Intent(mContext, BannerActivity.class);
                    intent.putExtra("banner", banner);
                    mContext.startActivity(intent);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
