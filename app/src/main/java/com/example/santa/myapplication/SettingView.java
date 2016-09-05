package com.example.santa.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by santa on 16/7/1.
 */
public class SettingView extends ViewGroup {
    private Context mContext;
    private ListView mListView;
    private LinearLayout mHeaderView;
    private SearchView mSearchView;
    private GridView mGridView;
    //private TextView mTextView2;
    private FloatingActionButton mFab;
    private final int ANIM_TIME = 800;
    private float mDensity = 1;
    //private Indicator mIndicator = new Indicator();

    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mDensity = context.getResources().getDisplayMetrics().density;
        init(context);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childCount = getChildCount();
        for (int i = 0; i<childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof FloatingActionButton) {
                mFab = (FloatingActionButton) v;
                mFab.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ValueAnimator anim = ObjectAnimator.ofFloat(v, "translationY", -mFab.getHeight() - ((MarginLayoutParams)mFab.getLayoutParams()).bottomMargin, 0);
                        anim.setDuration(200);
                        anim.setInterpolator(new DecelerateInterpolator());
                        anim.addListener(new ButtomListener());
                        anim.start();
                    }
                });
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(null != mHeaderView) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
        }
        if (null != mListView) {
            measureChild(mListView, widthMeasureSpec, heightMeasureSpec);
        }
        if (null != mFab) {
            measureChild(mFab, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        if(null != mHeaderView){
            //mIndicator.setOption(0, getMeasuredHeight(), mHeaderView.getMeasuredHeight());

            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int bottom = paddingTop + lp.topMargin;
            final int top = bottom - mHeaderView.getMeasuredHeight();
            final int right = left + mHeaderView.getMeasuredWidth();

            mHeaderView.layout(left, top, right, bottom);
        }

        if(null != mListView){
            MarginLayoutParams lp = (MarginLayoutParams)mListView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = getMeasuredHeight();
            final int right = left + mListView.getMeasuredWidth();
            final int bottom = top + mListView.getMeasuredHeight();
            mListView.layout(left, top, right, bottom);
        }

        if (null != mFab) {
            MarginLayoutParams lp = (MarginLayoutParams)mFab.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = getMeasuredHeight();
            final int right = left + mFab.getMeasuredWidth();
            final int bottom = top + mFab.getMeasuredHeight();
            mFab.layout(left, top, right, bottom);
        }
        startAnimotion();
    }

    public void init(Context context) {
        initHeaderView(context);
        initListView(context);
        initFloatingButton(context);
    }

    public void initFloatingButton(Context context) {
//        mFab = new FloatingActionButton(context);
//        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        mFab.setLayoutParams(l);
//        mFab.setTin
//        mFab.setBackgroundTintList(Color.BLACK);
//        mFab.setRippleColor(Color.BLACK);
//        mFab.setBackgroundColor(Color.BLACK);
//        mFab.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_email));
//        addView(mFab);

    }

    public int calPxFromDp(int px){
        return (int)(px * mDensity);
    }

    public void initHeaderView(Context context) {
        mHeaderView = new LinearLayout(context);
        mHeaderView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(l);
        mHeaderView.setPadding(0, calPxFromDp(10), 0, calPxFromDp(10));
        //mHeaderView.setBackgroundColor(0xff554433);

        mSearchView = new SearchView(context);
        l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSearchView.setLayoutParams(l);
        mSearchView.setBackground(context.getResources().getDrawable(R.drawable.search_edittext_shape));
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchView.setIconifiedByDefault(false); //默认非输入状态
        mSearchView.setInputType(InputType.TYPE_NULL);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("搜索");
//        mSearchView.setBackgroundColor(context.getResources().getColor(R.color.colorSearchView));
        mHeaderView.addView(mSearchView);


        LinearLayout linearLayout = new LinearLayout(context);
        l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(l);
        linearLayout.setPadding(0,calPxFromDp(15), 0, calPxFromDp(15));

        mGridView = new GridView(context);
        l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mGridView.setLayoutParams(l);
        mGridView.setNumColumns(4);
        //mGridView.setColumnWidth(80);
        //mGridView.setBackgroundColor(0xff884433);
        mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        linearLayout.addView(mGridView);

        mHeaderView.addView(linearLayout);


        addView(mHeaderView);


    }

    public void setGridAdapter(BaseAdapter adapter) {
        if (null != mGridView) {
            mGridView.setAdapter(adapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, ((TextView)view.findViewById(R.id.fun_name)).getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void initListView(Context context) {
        mListView = new ListView(context);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mListView.setLayoutParams(l);
        addView(mListView);
    }

    public void setListAdapter(BaseAdapter adapter) {
        if(null != mListView) {
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, ((TextView)view.findViewById(R.id.fun_name)).getText(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void startAnimotion() {
        if(null != mHeaderView) {
            ValueAnimator headerAnim = ObjectAnimator.ofFloat(mHeaderView, "translationY", 0, mHeaderView.getHeight());
            headerAnim.setDuration(ANIM_TIME);
            headerAnim.setInterpolator(new ElasticOutInterpolator());
            headerAnim.start();
        }
        if (null != mListView) {
            ValueAnimator listAnim = ObjectAnimator.ofFloat(mListView, "translationY", 0, mHeaderView.getHeight() - getHeight());
            listAnim.setDuration(ANIM_TIME);
            listAnim.setInterpolator(new ElasticOutInterpolator());
            listAnim.start();
        }

        if (null != mFab) {
            ValueAnimator fabAnim = ObjectAnimator.ofFloat(mFab, "translationY", 0, - mFab.getHeight() - ((MarginLayoutParams)mFab.getLayoutParams()).bottomMargin);
            fabAnim.setDuration(ANIM_TIME);
            fabAnim.setInterpolator(new ElasticOutInterpolator());
            fabAnim.start();
        }

    }

    private class Indicator {
        private int offsetHeader = 0;
        private int offsetList = 0;
        private int offsetButton = 0;

        private int stopScrollY; //header和listview中间的截止线
        private int screenTopY;
        private int screenButtomY;

        public void setOption(int topY, int buttomY, int stopY) {
            screenTopY = topY;
            screenButtomY = buttomY;
            stopScrollY = stopY;

            offsetHeader = topY;
            offsetList = buttomY;

        }

        public int getOffsetHeader() {
            return offsetHeader;
        }

        public int getOffsetList() {
            return offsetList;
        }

        public int getOffsteButton() {
            return offsetButton;
        }

//        public int g


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

    public class ButtomListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ((Activity)mContext).finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
