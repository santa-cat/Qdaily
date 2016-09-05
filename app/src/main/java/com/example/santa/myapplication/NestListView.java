package com.example.santa.myapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by santa on 16/6/26.
 */
public class NestListView  extends LinearLayout{
    private RecyclerView mSubListView;
    private TextView mNestViewHeader;
    private String mHeaderDefaultText = "好奇心研究所";


    public NestListView(Context context) {
        super(context);
        init(context);
    }

    public NestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NestListView(Context context, String text){
        this(context);
        setNestViewHeaderText(text);
    }

    public void setNestViewHeaderText(String text){
        mNestViewHeader.setText(text);
    }

    public void init(Context context){

        setOrientation(VERTICAL);
        initNestViewHeader(context);
        initListView(context);

    }

    public void initNestViewHeader(Context context){
        if (!mHeaderDefaultText.isEmpty()) {
            mNestViewHeader = new TextView(context);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mNestViewHeader.setLayoutParams(l);
            mNestViewHeader.setText(mHeaderDefaultText);
            addView(mNestViewHeader);
        }
    }

    public void initListView(Context context) {
        mSubListView = new RecyclerView(context);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSubListView.setLayoutParams(l);
        mSubListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSubListView.setLayoutManager(mLayoutManager);
        addView(mSubListView);
    }

    public void setAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter){
        if(null != mSubListView) {
            mSubListView.setAdapter(adapter);
        }
    }



}
