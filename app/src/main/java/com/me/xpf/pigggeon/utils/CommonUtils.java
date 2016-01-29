package com.me.xpf.pigggeon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.util.TypedValue;

/**
 * Created by xgo on 12/9/15.
 */
public class CommonUtils {
    private static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static void checkNotMain() {
        if (isMain()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
    }

    public static void checkMain() {
        if (!isMain()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    /**
     * Get StatusBar Height
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将dip转换为px
     *
     * @param dimen
     * @return px
     */
    public static int convertDimenToPix(float dimen) {
        Resources r = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen,
                r.getDisplayMetrics());
    }
}
