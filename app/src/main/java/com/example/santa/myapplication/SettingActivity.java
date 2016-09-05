package com.example.santa.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by santa on 16/7/1.
 */
public class SettingActivity extends AppCompatActivity {
    private SettingView mSettingView;
    private List<HashMap<String, Object>> mListData;
    private List<HashMap<String, Object>> mGridData;
    private String FUN_NAME = "fun_name";
    private String FUN_ICON = "fun_icon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        initData();
        mSettingView = (SettingView) findViewById(R.id.setting_view);
        mSettingView.setListAdapter(new SettingListAdapter());
        mSettingView.setGridAdapter(new SettingGridAdapter());


    }

    public void initData() {
        initListData();
        initGridData();
    }
    public void initListData() {
        mListData = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put(FUN_NAME, "关于我们");
        map1.put(FUN_ICON, new LetterDrawable("A", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(FUN_NAME, "新闻分类");
        map2.put(FUN_ICON, new LetterDrawable("B", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put(FUN_NAME, "栏目中心");
        map3.put(FUN_ICON, new LetterDrawable("C", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put(FUN_NAME, "好奇心研究所");
        map4.put(FUN_ICON, new LetterDrawable("D", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put(FUN_NAME, "我的消息");
        map5.put(FUN_ICON, new LetterDrawable("E", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map5);

        HashMap<String, Object> map6 = new HashMap<>();
        map6.put(FUN_NAME, "个人中心");
        map6.put(FUN_ICON, new LetterDrawable("F", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map6);

        HashMap<String, Object> map7 = new HashMap<>();
        map7.put(FUN_NAME, "首页");
        map7.put(FUN_ICON, new LetterDrawable("G", getResources().getColor(R.color.colorCircleText), getResources().getColor(R.color.colorAccent)));
        mListData.add(map7);
    }

    public void initGridData() {
        mGridData = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put(FUN_NAME, "设置");
        map1.put(FUN_ICON, new LetterDrawable("S", getResources().getColor(R.color.colorAccent), Color.WHITE));
        mGridData.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(FUN_NAME, "夜间");
        map2.put(FUN_ICON, new LetterDrawable("T", getResources().getColor(R.color.colorAccent), Color.WHITE));
        mGridData.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put(FUN_NAME, "离线");
        map3.put(FUN_ICON, new LetterDrawable("A", getResources().getColor(R.color.colorAccent), Color.WHITE));
        mGridData.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put(FUN_NAME, "推荐");
        map4.put(FUN_ICON, new LetterDrawable("R", getResources().getColor(R.color.colorAccent), Color.WHITE));
        mGridData.add(map4);
    }

    public class SettingListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.setting_listitem, null);
                holder = new ListViewHolder();
                holder.funName = (TextView) convertView.findViewById(R.id.fun_name);
                holder.funIcon = (ImageView) convertView.findViewById(R.id.fun_ic);

                convertView.setTag(holder);
            } else {
                holder = (ListViewHolder) (convertView.getTag());
            }

            holder.funName.setText(mListData.get(position).get(FUN_NAME).toString());
            holder.funIcon.setImageDrawable((Drawable) mListData.get(position).get(FUN_ICON));

            return convertView;
        }
    }

    public class SettingGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mGridData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.setting_griditem, null);
                holder = new ListViewHolder();
                holder.funName = (TextView) convertView.findViewById(R.id.fun_name);
                holder.funIcon = (ImageView) convertView.findViewById(R.id.fun_ic);

                convertView.setTag(holder);
            } else {
                holder = (ListViewHolder) (convertView.getTag());
            }

            holder.funName.setText(mGridData.get(position).get(FUN_NAME).toString());
            holder.funIcon.setImageDrawable((Drawable) mGridData.get(position).get(FUN_ICON));

            return convertView;
        }
    }

    public final class ListViewHolder{
        private TextView funName;
        private ImageView funIcon;

    }



}
