package com.tanglang.ypt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.tanglang.ypt.R;

/**
 * Author： Administrator
 */
public class DrugListView extends ListView implements AbsListView.OnScrollListener {
    private View footView;
    private int footViewHeight;

    private OnLoadingListener mListener;
    private boolean loading;

    public DrugListView(Context context) {
        super(context);
        initFooter();
    }

    private void initFooter() {
        footView = View.inflate(getContext(), R.layout.listloading_view, null);
        //footView.setVisibility(GONE);
        addFooterView(footView);

        try {
            int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            footView.measure(spec, spec);
            footViewHeight = footView.getMeasuredHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (footViewHeight == 0) {
            footViewHeight = 136;
        }

        footView.setPadding(0, -footViewHeight, 0, 0);
        this.setOnScrollListener(this);
    }

    public DrugListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooter();
    }

    public DrugListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFooter();
    }

    /*@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*/

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if (mListener != null && getLastVisiblePosition() == getCount() - 1 && !loading) {
                footView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);
                loading = true;
                mListener.loadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public void setOnLoadingLishener(OnLoadingListener lishener) {
        mListener = lishener;
    }

    public void onRefrshComplete(){
        loading = false;
        footView.setPadding(0, -footViewHeight, 0, 0);
    }

    public interface OnLoadingListener {
        void loadMore();
    }
}
