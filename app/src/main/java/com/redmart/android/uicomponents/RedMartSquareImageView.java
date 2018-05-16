package com.redmart.android.uicomponents;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class RedMartSquareImageView extends AppCompatImageView {

    public RedMartSquareImageView(Context context) {
        super(context);
    }

    public RedMartSquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RedMartSquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

}
