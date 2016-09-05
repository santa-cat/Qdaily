package com.example.santa.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final int RV_ITEM_NEST = 0;
    private final int RV_ITEM_BIG  = 1;
    private final int RV_ITEM_SMALL = 2;
    private final int RV_ITEM_CYCLE = 3;

    private final String RV_ITEM_STATUS= "itemStatus";
    RecyclerView mMainListView;
    //NestListView mNestListView;
    List<ArrayList<HashMap<String, Object>>> mDataAll;
    List<HashMap<String, Object>> mDataTag;

    SubAdapterController mSubAdapterCrl;
    FloatingActionButton mFab;

    List<View> mViewList = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView imageView = (ImageView) findViewById(R.id.image);
//        assert imageView != null;
//        imageView.setBackground(new LetterDrawable("H"));

        init();
        initCycleViewPager();
        mMainListView = (RecyclerView) findViewById(R.id.mainlistview);
        mMainListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mMainListView.setLayoutManager(mLayoutManager);
        mMainListView.setAdapter(new MainAdapter(this));
        mMainListView.addItemDecoration(new SpaceItemDecoration(10));


        mFab = (FloatingActionButton) findViewById(R.id.fab);
        assert mFab != null;
        //mFab.setBackgroundDrawable(new LetterDrawable("Q"));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValueAnimator anim = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight()*2);
                anim.setDuration(200);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.addListener(new ButtomListener());
                anim.start();

            }
        });


        PullRefreshLayout prl = (PullRefreshLayout) findViewById(R.id.pullRefreshLayout);
        assert prl!=null;
        prl.setPullHandler(new PullHandler(){
            @Override
            public void onRefreshBegin(final PullRefreshLayout layout) {


                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从客户端获取数据

                        layout.refreshComplete();
                    }
                }, 2000);

            }

            @Override
            public void onRefreshFinshed() {
                //更新数据
                //addData();
            }
        });
    }

    public void initCycleViewPager() {
        LayoutInflater inflater=getLayoutInflater();
        View view1 = inflater.inflate(R.layout.pager1, null);
        View view2 = inflater.inflate(R.layout.pager2, null);
        View view3 = inflater.inflate(R.layout.pager3, null);


        View view1_copy = inflater.inflate(R.layout.pager1, null);
        View view3_copy = inflater.inflate(R.layout.pager3, null);
        mViewList = new ArrayList<View>();
        mViewList.add(view3_copy);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view1_copy);
    }

    public void init() {
        mDataAll = new ArrayList<ArrayList<HashMap<String, Object>>>();
        mDataTag = new ArrayList<HashMap<String, Object>>();

        //添加头部
        for (int i = 0; i < 1; i++) {
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            //cyleview有几个viewpager
            for (int j = 0 ; j<3; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"《极限挑战》是上海东方卫视推出的大型励志体验真人秀节目，第一、二季节目六位固定成员是黄渤、孙红雷、黄磊、罗志祥、王迅、张艺兴。");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_CYCLE);
            mDataTag.add(mapStatus);
        }

        for (int i = 0; i < 2; i++){
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<20; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"此次发布的主题曲MV中，还穿插了不少影片中的精彩画面，不但有周杰伦在杰西·艾森伯格扮演的快手侠盗丹尼面前变魔术的戏份，还包括丹尼“控制下雨”魔术、四骑士合力偷芯片、澳门街头追逐激战等刺激戏码。据悉，此次“四骑士”被神秘力量骗到澳门");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_NEST);
            mDataTag.add(mapStatus);
        }

        for (int i = 0; i < 2; i++) {
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<1; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"《极限挑战》是上海东方卫视推出的大型励志体验真人秀节目，第一、二季节目六位固定成员是黄渤、孙红雷、黄磊、罗志祥、王迅、张艺兴。");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_BIG);
            mDataTag.add(mapStatus);
        }

        for (int i = 0; i < 3; i++) {
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<1; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"邓丽君，1953年1月29日出生于中国台湾省云林县，祖籍中国河北省邯郸市大名县，中国台湾歌唱家、日本昭和时代代表性日语女歌手之一。");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_SMALL);
            mDataTag.add(mapStatus);
        }

        //重复
        for (int i = 0; i < 2; i++){
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<20; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"此次发布的主题曲MV中，还穿插了不少影片中的精彩画面，不但有周杰伦在杰西·艾森伯格扮演的快手侠盗丹尼面前变魔术的戏份，还包括丹尼“控制下雨”魔术、四骑士合力偷芯片、澳门街头追逐激战等刺激戏码。据悉，此次“四骑士”被神秘力量骗到澳门");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_NEST);
            mDataTag.add(mapStatus);
        }

        for (int i = 0; i < 2; i++) {
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<1; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"《极限挑战》是上海东方卫视推出的大型励志体验真人秀节目，第一、二季节目六位固定成员是黄渤、孙红雷、黄磊、罗志祥、王迅、张艺兴。");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_BIG);
            mDataTag.add(mapStatus);
        }

        for (int i = 0; i < 3; i++) {
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            for (int j = 0 ; j<1; j++) {
                HashMap<String ,Object> map = new HashMap<String, Object>();
                map.put(SubAdapterController.TITLE, i+ j+"邓丽君，1953年1月29日出生于中国台湾省云林县，祖籍中国河北省邯郸市大名县，中国台湾歌唱家、日本昭和时代代表性日语女歌手之一。");
                map.put(SubAdapterController.SUB_TITLE, "As with the specification, this implementation relies on code laid out in Henry S. Warren, Jr.'s Hacker's Delight");
                list.add(map);
            }
            mDataAll.add(list);
            HashMap<String ,Object> mapStatus = new HashMap<String, Object>();
            mapStatus.put(RV_ITEM_STATUS, RV_ITEM_SMALL);
            mDataTag.add(mapStatus);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mFab && mFab.getVisibility() == View.GONE) {
            mFab.setVisibility(View.VISIBLE);
            ValueAnimator anim = ObjectAnimator.ofFloat(mFab, "translationY", mFab.getHeight()*2, 0);
            anim.setDuration(500);
            anim.setInterpolator(new ElasticOutInterpolator());
            anim.start();
        }

    }

    class ButtomListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mFab.setVisibility(View.GONE);
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }


    class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private LayoutInflater mInflater;
        private Context mContext;
        public MainAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public int getItemViewType(int position) {
            return (int)(mDataTag.get(position).get(RV_ITEM_STATUS));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == RV_ITEM_NEST) {
                View view = mInflater.inflate(R.layout.mainitem, parent, false);
                return new MainViewHolder(view);
            }else if (viewType == RV_ITEM_BIG){
                View view = mInflater.inflate(R.layout.mainitem_big, parent, false);
                return new MainViewBigHolder(view);
            }else if(viewType == RV_ITEM_SMALL){
                View view = mInflater.inflate(R.layout.mainitem_small, parent, false);
                return new MainViewBigHolder(view);
            }else if(viewType == RV_ITEM_CYCLE) {
                View view = mInflater.inflate(R.layout.mainitem_cycleview, parent, false);
                return new MainCycleViewHolder(view);
            }
            else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(getItemViewType(position) == RV_ITEM_NEST) {
                mSubAdapterCrl = new SubAdapterController(mDataAll.get(position), mContext);
                //holder = (MainViewHolder)holder;

                if(position == 1) {
                    ((MainViewHolder)holder).nestListView.setNestViewHeaderText("好奇心");
                }
                ((MainViewHolder)holder).nestListView.setAdapter(mSubAdapterCrl.getAdapter());
            } else if(getItemViewType(position) == RV_ITEM_BIG || getItemViewType(position) == RV_ITEM_SMALL) {
                ((MainViewBigHolder)holder).title.setText(mDataAll.get(position).get(0).get(SubAdapterController.TITLE).toString());
                ((MainViewBigHolder)holder).subTitle.setText(mDataAll.get(position).get(0).get(SubAdapterController.SUB_TITLE).toString());
            } else if(getItemViewType(position) == RV_ITEM_CYCLE) {
                ((MainCycleViewHolder)holder).cycleView.setViewList(mViewList);
                ((MainCycleViewHolder)holder).cycleView.startCycle();
            }
        }

        @Override
        public int getItemCount() {
            return mDataAll.size();
        }


        class MainCycleViewHolder extends RecyclerView.ViewHolder{
            public AutoCycleView cycleView;
            public MainCycleViewHolder(View itemView) {
                super(itemView);
                cycleView = (AutoCycleView) itemView.findViewById(R.id.cycle_view);
            }
        }


        class MainViewHolder extends RecyclerView.ViewHolder{
            public NestListView nestListView;
            public MainViewHolder(View itemView) {
                super(itemView);
                nestListView = (NestListView) itemView.findViewById(R.id.nestlistview);
            }
        }


        class MainViewBigHolder extends RecyclerView.ViewHolder{
            public ImageView image;
            public TextView title;
            public TextView subTitle;
            public MainViewBigHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                title = (TextView) itemView.findViewById(R.id.main_title);
                subTitle = (TextView) itemView.findViewById(R.id.sub_title);
            }
        }
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //设置左右的间隔如果想设置的话自行设置，我这用不到就注释掉了
/*          outRect.left = space;
            outRect.right = space;*/

            //       System.out.println("position"+parent.getChildPosition(view));
            //       System.out.println("count"+parent.getChildCount());

            //         if(parent.getChildPosition(view) != parent.getChildCount() - 1)
            outRect.bottom = space;

            //改成使用上面的间隔来设置
            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }



}
