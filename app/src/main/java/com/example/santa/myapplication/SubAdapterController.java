package com.example.santa.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by santa on 16/6/27.
 */
public class SubAdapterController {
    public final static String TITLE = "title";
    public final static String SUB_TITLE = "subTitle";
    private List<HashMap<String, Object>> mData;
    private Context mContext;
    private SubAdapter mAdapter;

    public SubAdapterController(List<HashMap<String, Object>> data, Context context){
        mData = data;
        if(mData.isEmpty()) {
            initData();
        }
        mContext = context;
    }


    public void initData() {
        mData = new ArrayList<HashMap<String, Object>>();
        /*为动态数组添加数据*/
        for (int i = 0; i < 20; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(TITLE, i + "此次发布的主题曲MV中，还穿插了不少影片中的精彩画面，不但有周杰伦在杰西·艾森伯格扮演的快手侠盗丹尼面前变魔术的戏份，还包括丹尼“控制下雨”魔术、四骑士合力偷芯片、澳门街头追逐激战等刺激戏码。据悉，此次“四骑士”被神秘力量骗到澳门");
            map.put(SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
            //根据数据库图片数目插入相应个数的image,image又是一个数组
//            ArrayList<String> imageList = new ArrayList<String>();
//            for(int j=0; j<3; j++) {
//                imageList.add(j,IMAGE_URLS[i+j]);
//            }
            ///map.put("image",imageList);
            //like和comment  插入图标及个数
            mData.add(map);
        }
    }

    public SubAdapter getAdapter(){
        if (!mData.isEmpty()) {
            if(null == mAdapter){
                mAdapter = new SubAdapter(mContext);
            }
            return mAdapter;
        } else {
            return null;
        }

    }

    class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHolder> {
        private LayoutInflater mInflater;
        public SubAdapter(Context context) {
            mInflater = LayoutInflater.from(context);

        }
        @Override
        public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.subitem, parent, false);
            return new SubViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SubViewHolder holder, int position) {
            holder.title.setText(mData.get(position).get(TITLE).toString());
            holder.subTitle.setText(mData.get(position).get(SUB_TITLE).toString());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class SubViewHolder extends RecyclerView.ViewHolder {
            public TextView subTitle;

            public TextView title;
//            public ImageView image;

            public SubViewHolder(View view) {
                super(view);
                subTitle= (TextView) view.findViewById(R.id.sub_title);
                title = (TextView) view.findViewById(R.id.main_title);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //启动一个ACTIVITY
                        Toast.makeText(mContext, ((TextView)v.findViewById(R.id.main_title)).getText(), Toast.LENGTH_SHORT).show();
                    }
                });
//                image = (ImageView) findViewById(R.id.image);
            }
        }
    }
}
