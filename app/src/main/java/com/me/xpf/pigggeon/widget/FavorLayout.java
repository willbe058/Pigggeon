package com.me.xpf.pigggeon.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.me.xpf.pigggeon.R;

import java.util.Random;

/**
 * Created by xpf on 2015/8/15.
 */
public class FavorLayout extends FrameLayout {

    private static final String TAG = "FavorLayout";

    private Interpolator line = new LinearInterpolator();

    private Interpolator acc = new AccelerateInterpolator();

    private Interpolator dce = new DecelerateInterpolator();

    private Interpolator accdec = new AccelerateDecelerateInterpolator();

    private Interpolator[] interpolators;

    private int mHeight;
    private int mWidth;

    private LayoutParams lp;

    private Drawable red;

    private Drawable yellow;

    private Drawable blue;

    private Drawable pigggeon1;

    private Drawable pigggeon2;

    private Drawable pigggeon3;

    private Drawable[] drawables;

    private Random random = new Random();

    private int dHeight;
    private int dWidth;


    public FavorLayout(Context context) {

        super(context);
    }

    public FavorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

//    public FavorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    private void init() {

        //  ????????????????
        drawables = new Drawable[6];
        red = getResources().getDrawable(R.drawable.ballred_36dp);
        yellow = getResources().getDrawable(R.drawable.ballred_36dp);
        blue = getResources().getDrawable(R.drawable.ballred_36dp);
        pigggeon1 = getResources().getDrawable(R.drawable.love_36dp);
        pigggeon2 = getResources().getDrawable(R.drawable.love_36dp);
        pigggeon3 = getResources().getDrawable(R.drawable.love_36dp);

        drawables[0] = red;
        drawables[1] = yellow;
        drawables[2] = blue;
        drawables[3] = pigggeon1;
        drawables[4] = pigggeon2;
        drawables[5] = pigggeon3;

        //???????????? ??????????????
        //???? ??????3??????????????????????,????????????????
        dHeight = red.getIntrinsicHeight();
        dWidth = red.getIntrinsicWidth();

        //???? ???? ????????
        lp = new LayoutParams(dWidth, dHeight);
        lp.gravity = Gravity.BOTTOM;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
//        lp.addRule(CENTER_HORIZONTAL, TRUE);//??????TRUE ?????? ????true
//        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        // ????????????
        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //?????????????? ??????????,????????????????
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    public void addFavor() {

        ImageView imageView = new ImageView(getContext());
        //??????????
        imageView.setImageDrawable(drawables[random.nextInt(6)]);
        imageView.setLayoutParams(lp);

        addView(imageView);
        Log.v(TAG, "add????view??:" + getChildCount());

        Animator set = getAnimator(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();

    }

    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimtor(target);

        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);

        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(bezierValueAnimator);
        finalSet.setInterpolator(interpolators[random.nextInt(4)]);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(final View target) {

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target) {

        //??????????????????????- - ????
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));

        //?????????????? ???????? ?????????? ?? ????
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF((mWidth - dWidth) / 2, mHeight - dHeight), new PointF(random.nextInt(getWidth()), 0));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    /**
     * ?????????????? ??
     *
     * @param scale
     */
    private PointF getPointF(int scale) {

        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth - 100));//????100 ?????????? x??????????,?????? ????~~
        //??Y???? ???????????????? ??????????????,????Y?????????????? ??????????????????  ????????????????
        pointF.y = random.nextInt((mHeight - 100)) / scale;
        return pointF;
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //????????????????????????????????x y?? ??????view ??????????????????????????
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // ??????????????alpha????
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }


    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //??????????add ??????view????????????,??????view??????????remove??
            removeView((target));
            Log.v(TAG, "removeView????view??:" + getChildCount());
        }
    }
}
