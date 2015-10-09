package com.tanglang.ypt.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.DrugCommBean;

import java.util.List;

/**
 * Author： Administrator
 */
public class CommentListAdapter extends BaseAdapter {
    private List<DrugCommBean.DrugComm> mData;
    private Context mContext;

    public CommentListAdapter(Context context, List<DrugCommBean.DrugComm> data) {
        mContext = context;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.comment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUser = (TextView) convertView.findViewById(R.id.comment_user);
            viewHolder.tvUserful = (TextView) convertView.findViewById(R.id.comment_useful);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.comment_text);
            viewHolder.btLike = (Button) convertView.findViewById(R.id.comment_like);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DrugCommBean.DrugComm commtent = mData.get(position);
        if(TextUtils.isEmpty(commtent.username)){
            viewHolder.tvUser.setText("用户");
        }else{
            viewHolder.tvUser.setText(commtent.username);
        }

        viewHolder.tvUserful.setText(commtent.useful+"");
        viewHolder.tvText.setText(commtent.content);
        viewHolder.btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvUser;
        TextView tvUserful;
        TextView tvText;
        Button btLike;
    }
}
