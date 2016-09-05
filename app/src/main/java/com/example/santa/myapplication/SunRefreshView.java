package com.example.santa.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

/**
 * Created by santa on 16/7/4.
 */
public class SunRefreshView extends RelativeLayout implements PullHeader{
    private SunView mSunView;
    private float mScreenDensity;
    private int mDefaultSizeOfSun = 40;
    private boolean isCanRefresh = false;

    public SunRefreshView(Context context) {
        this(context, null);
    }

    public SunRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenDensity = metrics.density;

        RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(getDpSize(mDefaultSizeOfSun), getDpSize(mDefaultSizeOfSun));
        l.addRule(RelativeLayout.CENTER_HORIZONTAL);
        l.addRule(RelativeLayout.CENTER_VERTICAL);

        mSunView = new SunView(context);
        mSunView.setLayoutParams(l);
        addView(mSunView);
    }


    private int getDpSize(int px) {
        return (int)(mScreenDensity * px);
    }


    @Override
    public boolean isMoveWithContent() {
        return true;
    }

    @Override
    public boolean isCanRefresh() {
        return isCanRefresh;
    }

    //准备刷新
    public void onUIReadyToRefresh() {
        if(null != mSunView) {
            mSunView.startAnimotion();
        }
    }

    //放弃刷新
    public void onUICancleRefresh() {
        if (null != mSunView) {
            mSunView.stopAnimotion();
        }
    }

    @Override
    public void onPullProgress(float percent, int status) {
        if (PullRefreshLayout.STATE_RELEASE == status){
            if (percent > 0.9f) {
                isCanRefresh = true;
            }
        }else {
            isCanRefresh = false;
        }
        if (percent >= 0.9f) {
            onUIReadyToRefresh();
        } else {
            onUICancleRefresh();
        }
    }

    @Override
    public void onRefreshComplete() {
        onUICancleRefresh();
    }
}
