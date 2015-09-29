package com.tanglang.ypt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Author： Administrator
 */
public class IndexListViewAdapter extends BaseAdapter {
    Context mContext;
    String[] mData;

    public IndexListViewAdapter(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText(mData[position]);
        textView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        textView.setBackgroundColor(Color.parseColor("#ffffff"));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
