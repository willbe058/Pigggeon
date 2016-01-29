package com.me.xpf.pigggeon.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xpf.me.architect.app.AppData;

/**
 * Created by xpf on 2015/8/14.
 */
public class ComfortaaTextView extends TextView {

    public ComfortaaTextView(Context context) {
        super(context);
    }

    public ComfortaaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComfortaaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf");
        super.setTypeface(tf);
    }
}
