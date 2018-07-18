package com.alexshr.xyz.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.alexshr.xyz.R;

import timber.log.Timber;

/**
 * Maintains an aspect ratio based on either width or height
 * and fit in screen
 */

//TODO attribute for fit screen feature
public class AspectRatioImageView extends AppCompatImageView {

    private static final float DEFAULT_ASPECT_RATIO = 1.5f;

    private float ratio;
    private boolean isWidthSpecified;
    private boolean isRatioMultiplier;

    public AspectRatioImageView(Context context) {
        this(context, null);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        ratio = a.getFloat(R.styleable.AspectRatioImageView_ratio, DEFAULT_ASPECT_RATIO);
        isWidthSpecified = a.getBoolean(R.styleable.AspectRatioImageView_is_width_specified,
                true);
        isRatioMultiplier = a.getBoolean(R.styleable.AspectRatioImageView_is_ratio_multiplier,
                false);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWidth;
        int newHeight;
        float rat = isRatioMultiplier ? ratio : 1 / ratio;
        if (isWidthSpecified) {
            newWidth = getMeasuredWidth();
            newHeight = (int) (newWidth * rat);
            newHeight = fitInScreen(newHeight);
        } else {
            newHeight = getMeasuredHeight();
            newWidth = (int) (newHeight * rat);
        }

        setMeasuredDimension(newWidth, newHeight);
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }

    public boolean isWidthSpecified() {
        return isWidthSpecified;
    }

    public void setWidthSpecified(boolean widthSpecified) {
        isWidthSpecified = widthSpecified;
        requestLayout();
    }

    public boolean isRatioMultiplier() {
        return isRatioMultiplier;
    }

    public void setRatioMultiplier(boolean ratioMultiplier) {
        isRatioMultiplier = ratioMultiplier;
        requestLayout();
    }

    private int getStatusBarHeight() {
        final Resources resources = getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        else
            return (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density);
    }

    private int fitInScreen(int height) {
        //ImageView imageView = parent.findViewById(ids.image());
        //Rect imageRect = measure(imageView);
        int statusBarHeight = getStatusBarHeight();
        int newHeight = height;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (height + statusBarHeight > displayMetrics.heightPixels) {
            newHeight = displayMetrics.heightPixels - statusBarHeight;
        }

        Timber.d("old height=%d to newHeight=%d", height, newHeight);
        return newHeight;
    }
}