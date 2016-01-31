package com.me.xpf.pigggeon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.me.xpf.pigggeon.R;


/**
 * Created by xpf on 2015/7/10.
 */
public class PigggeonLoadAnimationView extends RelativeLayout {

    private final static int DEFAULT_DURATION = 1000;

    private final static float DEFAULT_SCALE = 1.0f;

    private int mDuration;

    private float mScale;
    //the picture that will be rotated

    private Drawable mBackground = getResources().getDrawable(R.drawable.dpreload);

    private static ImageView mLoadView;

    private OnStatueListener mListener;

    private static ScaleAnimation scaleIn;

    private static ScaleAnimation scaleOut;

    private boolean start = true;

    private final static int LOAD_START = 0;

    private final static int LOAD_COMPLETE = 1;


    public void setOnStatusListener(OnStatueListener listener) {
        this.mListener = listener;
    }

    public void reset() {
        start = true;
//        mLoadView.clearAnimation();
    }


    public PigggeonLoadAnimationView(Context context) {
        super(context);
        init(null, 0);
    }

    public PigggeonLoadAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PigggeonLoadAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attributeSet, int defStyle) {
        if (null == attributeSet) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }

        initResource(attributeSet, defStyle);
        initDrawable();
        initAnimation();

        RelativeLayout.LayoutParams wrapLayoutParams = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        wrapLayoutParams.addRule(CENTER_IN_PARENT);

        this.addView(mLoadView, wrapLayoutParams);
    }


    private void initResource(AttributeSet attributeSet, int defStyle) {

        final TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.PigggeonLoadAnimationView, defStyle, 0);

        mDuration = typedArray.getInt(R.styleable.PigggeonLoadAnimationView_animDuration, DEFAULT_DURATION);
        mScale = typedArray.getFloat(R.styleable.PigggeonLoadAnimationView_animScale, DEFAULT_SCALE);

        if (typedArray.hasValue(R.styleable.PigggeonLoadAnimationView_animBackground)) {
            mBackground = typedArray.getDrawable(R.styleable.PigggeonLoadAnimationView_animBackground);
        }

        typedArray.recycle();

    }

    private void initDrawable() {
        mLoadView = new ImageView(getContext());
        mLoadView.setClickable(false);
        mLoadView.setImageDrawable(mBackground);
    }

    private void initAnimation() {
        scaleIn = new ScaleAnimation(0.0f, mScale, 0.0f, mScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleOut = new ScaleAnimation(mScale, 0.0f, mScale, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleIn.setFillAfter(true);
        scaleOut.setFillAfter(true);

        scaleIn.setDuration(mDuration);
        scaleOut.setDuration(mDuration);

        scaleIn.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleOut.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadView.startAnimation(scaleOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadView.startAnimation(scaleIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static void startAnimation() {
        new Handler().postDelayed(() -> {
            mLoadView.startAnimation(scaleOut);
        }, 200);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i("dispatch", String.valueOf(start));
        if (start) {
            startAnimation();
            start = false;
        }

        super.dispatchDraw(canvas);

    }

    public enum Status {START_LOAD, LOAD_COMPLETE}


    public interface OnStatueListener {
        void onStatus(Status status);
    }
}
