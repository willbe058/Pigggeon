package com.me.xpf.pigggeon.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xpf.me.architect.app.AppData;

/**
 * Created by xpf on 2015/8/14.
 */
public class ComfortaaBoldTextView extends TextView {

    public ComfortaaBoldTextView(Context context) {
        super(context);
    }

    public ComfortaaBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComfortaaBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(AppData.getContext().getAssets(), "fonts/Comfortaa-Bold.ttf");
        super.setTypeface(tf);
    }
}
