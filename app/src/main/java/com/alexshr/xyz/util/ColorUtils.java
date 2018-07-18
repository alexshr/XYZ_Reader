package com.alexshr.xyz.util;

import android.graphics.Color;

import timber.log.Timber;

//https://stackoverflow.com/a/11019879/2886841 opacity values

public class ColorUtils {
    public static int withTransparency(int color, String transparency) {
        String colorStr = "#" + transparency + Integer.toHexString(color).toUpperCase().substring(2);

        int tColor = Color.parseColor(colorStr);
        Timber.d("color=%d, transparency=%s, colorStr=%s, tColor=%d", color, transparency, colorStr, tColor);
        return tColor;
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }
}
