package com.tanglang.ypt.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanglang.ypt.R;

/**
 * Author： Administrator
 */
public class YButton extends LinearLayout {
    private static String NAMESPACE = "http://schemas.android.com/apk/res_aotu/com.tanglang.ypt";
    private View view;

    private boolean isShowImage;
    private String text;
    private int textColor;
    private int imageId;
    private TextView tvText;
    private ImageView ivImage;

    public YButton(Context context) {
        super(context);
        initView();
    }

    public YButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        isShowImage = attrs.getAttributeBooleanValue(NAMESPACE, "showimage", false);
        imageId = attrs.getAttributeIntValue(NAMESPACE, "image", 0);
        text = attrs.getAttributeValue(NAMESPACE, "text");
        textColor = attrs.getAttributeIntValue(NAMESPACE, "textColor", 0);
        initView();
    }

    public YButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isShowImage = attrs.getAttributeBooleanValue(NAMESPACE, "showimage", false);
        imageId = attrs.getAttributeIntValue(NAMESPACE, "image", 0);
        text = attrs.getAttributeValue(NAMESPACE, "text");
        textColor = attrs.getAttributeIntValue(NAMESPACE, "textColor", 0);
        initView();
    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.ybutton_view, this);
        ivImage = (ImageView) view.findViewById(R.id.ybutton_image);
        tvText = (TextView) view.findViewById(R.id.ybutton_text);
        setImageVisible(isShowImage);
        setImageId(imageId);
        setText(text);
        System.out.println(textColor + "--");
        //tvText.setTextColor(getResources().getColor(textColor));
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        tvText.setText(text);
    }

    public void setImageVisible(boolean visible) {
        if (visible) {
            ivImage.setVisibility(VISIBLE);
        } else {
            ivImage.setVisibility(GONE);
        }
    }

    public void setImageId(int imageId) {
        if (imageId == 0) {
            return;
        }
        ivImage.setImageResource(imageId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(0xdddddd);
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(0xffffff);
                break;
        }
        return super.onTouchEvent(event);
    }
}
