package com.me.xpf.pigggeon.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.utils.CommonUtils;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseStatusBarTintActivity extends BaseActivity {
    @Override
    protected void tintStatusBarApi21() {
        //do nothing
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void tintStatusBarApi19() {
        Window window = getWindow();
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(windowLayoutParams);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        final int tintColor = typedValue.data;
        final int statusBarHeight = CommonUtils.getStatusBarHeight(this);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        View placeholder = new View(this);
        placeholder.setLayoutParams(layoutParams);
        placeholder.setBackgroundColor(tintColor);
        FrameLayout frame = (FrameLayout) findViewById(android.R.id.content);
        frame.getChildAt(0).setPadding(0, statusBarHeight, 0, 0);
        frame.addView(placeholder);
    }
}
