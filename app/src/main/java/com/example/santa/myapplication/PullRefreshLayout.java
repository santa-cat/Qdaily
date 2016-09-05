package com.example.santa.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by santa on 16/6/21.
 */
public class PullRefreshLayout extends ViewGroup implements NestedScrollingParent {
    private final String DEBUG_FLAG = "PullRefreshLayout";
    private View mHeaderView;
    private View mContentView;
    private PullHeader mPullHeaderView;
    private PullHandler mPullHandler;
    private float mDensity;

    //滑动相关
    private int mActivePointerId;
    private PullIndicator mIndicator;
    private NestedScrollingParentHelper mParentHelper;
    private Scroller mScroller;
    public static final int STATE_IDLE = 0;
    public static final int STATE_DRAGE = 1;
    public static final int STATE_FLING = 2;
    public static final int STATE_RELEASE = 3;
    private int mStatus;
    private boolean isFlingConsumed = false;

    private final int RELEASE_TIME = 600;

    private int mDefaultHeight = 220;
    private int mMaxHeight = 280;
    private int mMinHeight = 48;
    public PullRefreshLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        mDensity = context.getResources().getDisplayMetrics().density;

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PullRefreshLayout, defStyleAttr, defStyleRes);
        mDefaultHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_default_height, calPxFromDp(mDefaultHeight));
        mMaxHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_max_height, calPxFromDp(mMaxHeight));
        mMinHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_min_height, calPxFromDp(mMinHeight));
        //TODO:增加增值器
        mScroller = new Scroller(context, new ElasticOutInterpolator());

        mIndicator = new PullIndicator(mDefaultHeight, mMaxHeight, mMinHeight);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        //应该增加一个上拉加载
        if(count > 2) {
            Log.e("refresh", "PullRefreshLayout only can host 2 elements");
            throw new IllegalStateException("PullRefreshLayout only can host 2 elements");
        } else if (count == 2){
            View child;

            for(int i = 0; i<count; i++) {
                child = getChildAt(i);
                if (child instanceof PullHeader) {
                    mHeaderView = child;
                    mPullHeaderView = (PullHeader) mHeaderView;
                } else {
                    mContentView = child;
                }
            }
        }

        if(mPullHeaderView.isMoveWithContent()){
            mIndicator.setHeightFixd();
            assert mHeaderView != null;
            mHeaderView.bringToFront();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(null != mHeaderView){
            measureChildWithMargins(mHeaderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        //TODO 优化 heightused
        if (null != mContentView){
            measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    public void setPullHandler(PullHandler handler) {
        mPullHandler = handler;
    }


//    public void setHeight()

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offsetContent = mIndicator.getHeaderOffcet();
        int offsetHeader = mPullHeaderView.isMoveWithContent() ? (mHeaderView.getMeasuredHeight() - mIndicator.getHeaderOffcet()): 0;
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        if(null != mHeaderView){
            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin - offsetHeader;
            final int right = left + mHeaderView.getMeasuredWidth();
            final int bottom = top + mHeaderView.getMeasuredHeight();
            mHeaderView.layout(left, top, right, bottom);
        }

        if(null != mContentView){
            MarginLayoutParams lp = (MarginLayoutParams)mContentView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin + offsetContent;
            final int right = left + mContentView.getMeasuredWidth();
            final int bottom = top + mContentView.getMeasuredHeight();
            mContentView.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept = !mScroller.isFinished();
                break;
            case MotionEvent.ACTION_MOVE:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isIntercept = false;
                break;

        }
        return isIntercept;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int actionMasked = MotionEventCompat.getActionMasked(ev);
        if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            release();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                release();
                break;
            case MotionEvent.ACTION_DOWN:
                //mScroller.forceFinished(true);
                mScroller.abortAnimation();

                mIndicator.setHeaderLastPosition( (int) event.getY() );
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e("refresh", "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                int curPosition = (int)(event.getY());
                moveBy(mIndicator.getHeaderLastPosition() - curPosition);
                mIndicator.setHeaderLastPosition(curPosition);
                break;
            default:
                Log.e("refresh", "touch event id is "+event.getAction());
                break;
        }

        return true;
    }

    public int calPxFromDp(int px){
        return (int)(px * mDensity);
    }


    public void refreshComplete() {
        if (null != mPullHeaderView) {
            mPullHeaderView.onRefreshComplete();
        }
        resetBackAfterTouch();
    }

    public void release(){
        setStatus(STATE_RELEASE);
        //如果是header和content一起的情况,需要先设置一下是否能刷新的标志
        if(mPullHeaderView.isMoveWithContent()) {
            mPullHeaderView.onPullProgress(mIndicator.getPercent(), mStatus);

        }
        if(mPullHeaderView.isCanRefresh() && null != mPullHandler) {
            mPullHandler.onRefreshBegin(this);
        }
        if(!mPullHeaderView.isMoveWithContent() || !mPullHeaderView.isCanRefresh()) {
            resetBackAfterTouch();
        }
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 必须调用该方法，否则不一定能看到滚动效果

            int delteY = -mIndicator.getCurScrollY() + mScroller.getCurrY();
            //Log.d(DEBUG_FLAG, "status" + mStatus + "last"+mIndicator.getCurScrollY() +"cur"+ mScroller.getCurrY());
            moveBy(delteY);
            postInvalidate();
            resetBackAfterFling();
        }else {
            setStatus(STATE_IDLE);

        }

    }

    private void resetBackAfterFling() {
        if(mIndicator.isOverDefaultHeight() && mStatus == STATE_FLING){
            mScroller.abortAnimation();
            resetBackAfterTouch();
        }
    }

    private void resetBackAfterTouch(){
        if (mIndicator.isOverDefaultHeight()){
            int curScrollY = mIndicator.getCurScrollY();
            //start 入参意思是从哪个位置开始,滚动多少偏移,然后computeScroll中每次得到getCurrY
            mScroller.startScroll(0, curScrollY, 0, -curScrollY, RELEASE_TIME);
            invalidate();
        } else {
            setStatus(STATE_IDLE);
        }
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public void setStatus(int status) {
        mStatus = status;
    }


    public int moveBy(float deltaY){
//        assert mStatus != STATE_RELEASE;
        int consumed = mIndicator.moveBy(deltaY);
//        Log.d(DEBUG_FLAG, "scroll consumed is "+ consumed);

        if(null != mContentView) {
            mContentView.offsetTopAndBottom(-consumed);
        }

        if (null != mHeaderView) {
            mPullHeaderView.onPullProgress(mIndicator.getPercent(), mStatus);
            if (mPullHeaderView.isMoveWithContent()){
                mHeaderView.offsetTopAndBottom(-consumed);
                invalidate();
            }
//            Log.d(DEBUG_FLAG, "percent is "+ mIndicator.getPercent());
//            Log.d(DEBUG_FLAG, "mStatus is "+ mStatus);

        }


        return consumed;
    }


    //NestedScrollingParent
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
    }
    //先滑动子view,剩余的后穿给该view
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        moveBy(dyUnconsumed);
    }

    //先滑动当前view,后返回给子view
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0 && !mIndicator.isReachMinHeight()) {
            final int delta = moveBy(dy);
            consumed[0] = 0;
            consumed[1] = delta;
        }
    }

    //判断是否存在子view要和该view配合滑动的
    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }


    //如何判断fling应该不应该给parent用,暂时父子都用一个fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        if(!mIndicator.isReachMinHeight() || !isFlingConsumed) {
//            return nestedFling((int) velocityY);
//        }
//        return false;
        //向上滑可以完美实现
        boolean consumed = nestedFling((int) velocityY);
        //return velocityY>0 ? consumed : false;
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        isFlingConsumed = consumed;
//        Log.d(DEBUG_FLAG, "isFlingConsumed" + isFlingConsumed);
        return false;
    }



    private boolean nestedFling(int velocityY) {
//        final boolean canFling = (!mIndicator.isReachMinHeight() && velocityY > 0) ||
//                (!mIndicator.isReachMaxHeight() && velocityY < 0);
//        //Log.d(DEBUG_FLAG, "canFling" + canFling);
//        if(canFling) {
//            fling(velocityY);
//        }
//        return canFling;
        return false;
    }


    public void fling(int velocityY) {
        //mPullState = STATE_FLING;
        setStatus(STATE_FLING);
        mScroller.abortAnimation();
        int curScrollY = mIndicator.getCurScrollY();
        int maxScrollY = mIndicator.getHeaderMaxScroll();
        int minScrollY = mIndicator.getHeaderMinScroll();
        mScroller.fling(0, curScrollY, 0, velocityY, 0,  0, minScrollY, maxScrollY);
        postInvalidate();
    }
}
