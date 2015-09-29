package com.tanglang.ypt.utils;

import android.content.res.Resources;

import com.tanglang.ypt.YPTApplication;

/**
 * Author： Administrator
 */
public class UiUtils {
    public static Resources getResource() {
        return YPTApplication.getApplication().getResources();
    }
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
