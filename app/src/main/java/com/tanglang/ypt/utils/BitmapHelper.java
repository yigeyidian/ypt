package com.tanglang.ypt.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.tanglang.ypt.R;

import java.io.File;

/**
 * Author： Administrator
 */
public class BitmapHelper {
    private static BitmapUtils bitmapUtils;

    public static BitmapUtils getBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context, context.getCacheDir().getAbsolutePath() + File.separator + "image", 0.3f);
            bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_default_avatar);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_default_avatar);
        }
        return bitmapUtils;
    }
}
