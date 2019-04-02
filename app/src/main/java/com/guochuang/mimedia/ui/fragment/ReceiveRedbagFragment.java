package com.guochuang.mimedia.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.DrawStatistics;
import com.guochuang.mimedia.mvp.model.RedbagReceived;
import com.guochuang.mimedia.mvp.presenter.ReceivedRedbagPresenter;
import com.guochuang.mimedia.mvp.view.ReceiveRedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.ReceiveRedbagAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiveRedbagFragment extends MvpFragment<ReceivedRedbagPresenter> implements ReceiveRedbagView {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_ksb)
    TextView tvKsb;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<RedbagReceived> itemArr=new ArrayList<>();
    ReceiveRedbagAdapter adapter;
    String defaultIndex="0";
    String startIndex=defaultIndex;
    String type="0";
    @Override
    protected ReceivedRedbagPresenter createPresenter() {
        return new ReceivedRedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_receive_redbag;
    }

    @Override
    public void initViewAndData() {
        rvRecord.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.VERTICAL,false));
        rvRecord.addItemDecoration(new VerticalDecoration(getContext(),R.drawable.bg_decoration));
        adapter=new ReceiveRedbagAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startRedbagDetailActivity(getActivity(),itemArr.get(position).getRedPacketUuid(),itemArr.get(position).getRedPacketType(),null,itemArr.get(position).getStartIndex());
            }
        });
        rvRecord.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getKsbDetailsList(type,startIndex,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex=defaultIndex;
                mvpPresenter.getKsbDetailsList(type,startIndex,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getDrawStatistics();
        mvpPresenter.getKsbDetailsList(type,startIndex,Constant.PAGE_SIZE);

    }

    @OnClick({R.id.tv_number, R.id.tv_ksb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_number:
                break;
            case R.id.tv_ksb:
                break;
        }
    }

    @Override
    public void setData(List<RedbagReceived> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (startIndex.equals(defaultIndex)){
            itemArr.clear();
        }
        if (data==null||data.size()<Constant.PAGE_SIZE){
            startIndex=defaultIndex;
            srlRefresh.setEnableLoadmore(false);
            itemArr.addAll(data);
        }else {
            startIndex = data.get(data.size()-1).getStartIndex();
            itemArr.addAll(data);
            srlRefresh.setEnableLoadmore(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setStatistics(DrawStatistics data) {
        if (data!=null){
            tvNumber.setText(String.valueOf(data.getTotalCount()));
            tvKsb.setText(String.valueOf(data.getTotalCoin()));
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
