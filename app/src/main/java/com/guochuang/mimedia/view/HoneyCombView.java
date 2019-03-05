package com.guochuang.mimedia.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.NestHomeAd;
import com.guochuang.mimedia.tools.AppBarStateChangeListener;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.adapter.HoneyAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HoneyCombView extends LinearLayout {
    @BindView(R.id.barlayout)
    AppBarLayout appbar;
    @BindView(R.id.rv_honey)
    RecyclerView rvHoney;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
     List<NestHomeAd> dataArr=new ArrayList<>();
     List<List<NestHomeAd>> allData=new ArrayList<>();
     HoneyAdapter honeyAdapter;
     int currentRow=0;
    int totalRow=0;
    boolean isExpand=false;
    static final int delayTime=5000;
     Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             if (!isExpand){
                 currentRow++;
                 currentRow=currentRow%totalRow;
                 setCollse();
                 handler.sendEmptyMessageDelayed(0,delayTime);
             }
         }
     };
    public interface OnMenuClickListener{
         void onClick(NestHomeAd data);
         void onSendAd();
     }
    OnMenuClickListener onMenuClickListener;


    public HoneyCombView(Context context) {
        super(context);
        init();
    }

    public HoneyCombView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HoneyCombView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_honeycomb, null);
        ButterKnife.bind(this,contentView);
        addView(contentView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        rvHoney.setLayoutManager(new GridLayoutManager(getContext(),4));
        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                if (state == State.EXPANDED) {
                    showExpand();
                    //展开状态
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    setCollse();
                } else {
                    //中间状态

                }
            }
        });
    }

    public void setData(List<NestHomeAd> data, final OnMenuClickListener onMenuClickListener){
        this.dataArr=data;
        this.onMenuClickListener=onMenuClickListener;
        totalRow=(data.size()/4)+(data.size()%4>0?1:0);
       if (totalRow>0) {
           for (int i = 0; i < totalRow; i++) {
               if (i == (totalRow-1)){
                   allData.add(data.subList(i * 4, data.size()));
               }else {
                   allData.add(data.subList(i * 4, (i + 1) * 4));
               }
           }
       }
        honeyAdapter=new HoneyAdapter(dataArr);
        honeyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onMenuClickListener!=null){
                    onMenuClickListener.onClick(honeyAdapter.getData().get(position));
                }
            }
        });
        rvHoney.setAdapter(honeyAdapter);
        appbar.setExpanded(false,true);
    }
    @OnClick({R.id.iv_arrow,R.id.tv_vote})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_arrow:
                appbar.setExpanded(!isExpand,true);
                break;
            case R.id.tv_vote:
                if (onMenuClickListener!=null)
                onMenuClickListener.onSendAd();
                break;
        }
    }
    public void showExpand(){
        if (allData.size()>1) {
            honeyAdapter.setNewData(dataArr);
        }
        tvVote.setVisibility(VISIBLE);
        ivArrow.setImageResource(R.drawable.ic_double_arrow_up);
        isExpand=true;
    }
    public void setCollse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvVote.setVisibility(GONE);
                ivArrow.setImageResource(R.drawable.ic_double_arrow_down);
                if (allData.size()>1) {
                    honeyAdapter.setNewData(allData.get(currentRow));
                    handler.removeMessages(0);
                    handler.sendEmptyMessageDelayed(0,delayTime);
                }
                isExpand=false;
            }
        },200);

    }
}
