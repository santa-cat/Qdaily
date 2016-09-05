package com.example.santa.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by santa on 16/6/13.
 */
public class SunView extends ImageView {
    private int mColor = 0xffbb4455;;
    private final static int ANIMOTION_DURATION_TIME = 1000;
    private SunDrawable mSunDrawable;
    private ValueAnimator mAnimator;
    private Animation.AnimationListener mListener;

    public SunView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SunView, defStyleAttr, defStyleRes);
        mColor = array.getColor(R.styleable.SunView_sunview_color, mColor);
        array.recycle();
        mSunDrawable = new SunDrawable(context);
        mSunDrawable.setColor(mColor);
        super.setImageDrawable(mSunDrawable);
        setupAnimotion();
    }


    public void setupAnimotion() {
        mAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        mAnimator.setDuration(ANIMOTION_DURATION_TIME);
        mAnimator.setInterpolator(new LinearInterpolator());
        //mAnimator.setEvaluator(new ArgbEvaluator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    public void startAnimotion() {
        if(!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    public void stopAnimotion() {
        if(mAnimator.isStarted()) {
            mAnimator.cancel();
        }
    }

    private void setColor(int color) {
        mColor = color;
    }

    //是不是监控的是imageview本身的动作???
    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }

}
