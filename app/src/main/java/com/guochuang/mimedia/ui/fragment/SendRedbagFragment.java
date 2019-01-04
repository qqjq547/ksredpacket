package com.guochuang.mimedia.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.mvp.presenter.SendRedbagPresenter;
import com.guochuang.mimedia.mvp.view.SendRedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.activity.MessageActivity;
import com.guochuang.mimedia.ui.adapter.SendRedbagAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SendRedbagFragment extends MvpFragment<SendRedbagPresenter> implements SendRedbagView {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_has_send)
    TextView tvHasSend;
    @BindView(R.id.tv_grab_num)
    TextView tvGrabNum;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    List<RedbagRecord> itemArr = new ArrayList<>();
    SendRedbagAdapter adapter;
    int curPage = 1;

    @Override
    protected SendRedbagPresenter createPresenter() {
        return new SendRedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_send_redbag;
    }

    @Override
    public void initViewAndData() {
        rvRecord.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        rvRecord.addItemDecoration(new VerticalDecoration(getContext(), R.drawable.bg_decoration));
        adapter = new SendRedbagAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startRedbagDetailActivity(getActivity(),itemArr.get(position).getRedPacketUuid(),Constant.ROLETYPE_PERSON,null);
            }
        });
        rvRecord.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getSendRedbagList(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getSendRedbagList(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getSendRedbagList(curPage, Constant.PAGE_SIZE);
    }

    @OnClick({R.id.tv_number, R.id.tv_has_send, R.id.tv_grab_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_number:
                break;
            case R.id.tv_has_send:
                break;
            case R.id.tv_grab_num:
                break;
        }
    }

    @Override
    public void setData(Page<RedbagRecord> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            itemArr.clear();
        }
        if (data.getDataList() != null) {
            itemArr.addAll(data.getDataList());
        }
        if (itemArr.size() > 0) {
            tvNumber.setText(String.valueOf(data.getDataList().get(0).getTotalCount()));
            tvHasSend.setText(String.valueOf(data.getDataList().get(0).getTotalMoney()));
            tvGrabNum.setText(String.valueOf(data.getDataList().get(0).getDrawPerson()));
        }
        adapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
