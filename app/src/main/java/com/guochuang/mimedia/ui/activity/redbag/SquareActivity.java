package com.guochuang.mimedia.ui.activity.redbag;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Square;
import com.guochuang.mimedia.mvp.presenter.SquarePresenter;
import com.guochuang.mimedia.mvp.view.SquareView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.SquareAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SquareActivity extends MvpActivity<SquarePresenter> implements SquareView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_square)
    RecyclerView rvSquare;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    SquareAdapter squareAdapter;
    List<Square> squareArr=new ArrayList<>();
    int curPage=1;
    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_square;
    }

    @Override
    public void initViewAndData() {
      tvTitle.setText(R.string.square);
      rvSquare.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
      rvSquare.addItemDecoration(new VerticalDecoration(this));
      rvSquare.setItemAnimator(new DefaultItemAnimator());
        squareAdapter=new SquareAdapter(squareArr);
        squareAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        squareAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        squareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startSquareDetailActivity(SquareActivity.this,squareArr.get(position).getRedPacketUuid());
            }
        });
        rvSquare.setAdapter(squareAdapter);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getSquareList(curPage+1,Constant.PAGE_SIZE,getPref().getLatitude(),getPref().getLongitude());
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getSquareList(1,Constant.PAGE_SIZE,getPref().getLatitude(),getPref().getLongitude());
            }
        });
       mvpPresenter.getSquareList(curPage,Constant.PAGE_SIZE,getPref().getLatitude(),getPref().getLongitude());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<Square> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (data!=null) {
            curPage = data.getCurrentPage();
            if (curPage == 1) {
                squareArr.clear();
            }
            squareArr.addAll(data.getDataList());
            squareAdapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
