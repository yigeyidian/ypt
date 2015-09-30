package com.tanglang.ypt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tanglang.ypt.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Author： Administrator
 */
public class TypeListAdapter extends BaseAdapter {
    private List<String> mData;
    private Context mContext;
    private final LayoutInflater inflater;

    public TypeListAdapter(Context context, List<String> data) {
        this.mData = data;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
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
        TextView textView = (TextView) inflater.inflate(R.layout.type_item, null, false);
        textView.setText(mData.get(position));
        return textView;
    }
}
