package com.tanglang.ypt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.tanglang.ypt.utils.LogUtils;

/**
 * Author： Administrator
 */
public class YPScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public YPScrollView(Context context) {
        super(context);
    }

    public YPScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YPScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        int measuredHeight = getMeasuredHeight();
        //LogUtils.println("--" + measuredHeight + "--" + y);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(YPScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
