package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.RedbagUser;
import com.guochuang.mimedia.mvp.presenter.RedbagJoinedPresenter;
import com.guochuang.mimedia.mvp.view.RedbagJoinedView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.RedbagJoinedAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedbagJoinedActivity extends MvpActivity<RedbagJoinedPresenter> implements RedbagJoinedView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_has_get)
    TextView tvHasGet;
    @BindView(R.id.rv_joined)
    RecyclerView rvJoined;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<RedbagUser> joinedArr=new ArrayList<>();
    RedbagJoinedAdapter joinedAdapter;
    String defaultIndex="0";
    String startIndex=defaultIndex;
    String redPacketUuid;

    @Override
    protected RedbagJoinedPresenter createPresenter() {
        return new RedbagJoinedPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_redbag_joined;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.redbag_joined_title);
         redPacketUuid=getIntent().getStringExtra(Constant.RED_PACKET_UUID);
         String avatar=getIntent().getStringExtra(Constant.AVATAR);
         String name=getIntent().getStringExtra(Constant.NAME);
         String ksb=getIntent().getStringExtra(Constant.KSB);
         String money=getIntent().getStringExtra(Constant.MONEY);
         String areaType=getIntent().getStringExtra(Constant.AREATYPE);
         String drawNumber=getIntent().getStringExtra(Constant.DRAWNUMBER);
         String total=getIntent().getStringExtra(Constant.TOTAL);
         GlideImgManager.loadCircleImage(this,avatar,ivAvatar);
        tvName.setText(name);
        tvCoin.setText(ksb);
        tvMoney.setText(String.format(getString(R.string.format_add_yuan), money));
        tvArea.setText(areaType);
        tvHasGet.setText(String.format(getString(R.string.redbag_joined_left),drawNumber,total));
         rvJoined.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
         rvJoined.addItemDecoration(new VerticalDecoration(this,R.drawable.bg_divide_yellow));
         joinedAdapter=new RedbagJoinedAdapter(joinedArr);
         joinedAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
         joinedAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
         rvJoined.setAdapter(joinedAdapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRedbagUserDetail(redPacketUuid,startIndex,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex=defaultIndex;
                mvpPresenter.getRedbagUserDetail(redPacketUuid,startIndex,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getRedbagUserDetail(redPacketUuid,startIndex,Constant.PAGE_SIZE);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(List<RedbagUser> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (startIndex.equals(defaultIndex)){
            joinedArr.clear();
        }
        if (data==null||data.size()<Constant.PAGE_SIZE){
            startIndex=defaultIndex;
            joinedArr.addAll(data);
            srlRefresh.setEnableLoadmore(false);
        }else {
            startIndex = data.get(data.size()-1).getStartIndex();
            joinedArr.addAll(data);
            srlRefresh.setEnableLoadmore(true);
        }
        joinedAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
