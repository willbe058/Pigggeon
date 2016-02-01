package com.me.xpf.pigggeon.utils;

import android.graphics.Color;

/**
 * Created by pengfeixie on 16/2/1.
 */
public class ColorUtil {
    public static String changeColorHSB(String color) {
        float[] hsv = new float[3];
        int brandColor = Color.parseColor(color);
        Color.colorToHSV(brandColor, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int argbColor = Color.HSVToColor(hsv);
        String hexColor = String.format("#%08X", argbColor);
        return hexColor;
    }
}
