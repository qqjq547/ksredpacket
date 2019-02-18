package com.guochuang.mimedia.ui.activity.treasure;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.presenter.MyTreasurePresenter;
import com.guochuang.mimedia.mvp.view.MyTreasureView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.pay.AliPay;
import com.guochuang.mimedia.tools.pay.WxPay;
import com.guochuang.mimedia.ui.activity.MyAddressActivity;
import com.guochuang.mimedia.ui.adapter.AddressAdapter;
import com.guochuang.mimedia.ui.adapter.MyTreasureAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTreasureActivity extends MvpActivity<MyTreasurePresenter> implements MyTreasureView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_treasure)
    RecyclerView rvTreasure;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    MyTreasureAdapter adapter;
    List<Snatch> dataArr=new ArrayList<>();
    int curPage=1;
    @Override
    protected MyTreasurePresenter createPresenter() {
        return new MyTreasurePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_treasure;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_treasure);
        rvTreasure.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new MyTreasureAdapter(dataArr);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Snatch snatch=dataArr.get(position);
                switch (view.getId()){
                    case R.id.tv_address:
                        if (snatch.getShowAddress()==1) {
                            startActivityForResult(new Intent(MyTreasureActivity.this, MyAddressActivity.class).putExtra(Constant.SNATCHID,snatch.getSnatchId()), Constant.REQUEST_PICK_ADDRESS);
                        }
                        break;
                    case R.id.tv_comment:
                        startActivityForResult(new Intent(MyTreasureActivity.this, ShowListActivity.class).putExtra(Constant.SNATCH,snatch),Constant.REQUEST_SET_SHOWLIST);
                        break;
                    case R.id.tv_express:
                        startActivity(new Intent(MyTreasureActivity.this, ExpressInfoActivity.class).putExtra(Constant.SNATCHID, snatch.getSnatchId()));
                        break;
                    case R.id.lin_join_people_time:
                        IntentUtils.startWebActivity(MyTreasureActivity.this,"",Constant.URL_DUOBAO_TREASURE_NUMBER+snatch.getSnatchRecordId());
                        break;
                    case R.id.tv_pay:
                        showLoadingDialog(null);
                        mvpPresenter.getOrderVendor(snatch.getOrderId());
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startWebActivity(MyTreasureActivity.this,"",Constant.URL_DUOBAO_DETAIL+dataArr.get(position).getSnatchId());
            }
        });
        rvTreasure.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
               mvpPresenter.getRecordList(curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRecordList(1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getRecordList(1,Constant.PAGE_SIZE);
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<Snatch> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            dataArr.clear();
        }
        if (data.getDataList() != null) {
            dataArr.addAll(data.getDataList());
        }
        adapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setVendor(Order data) {
        closeLoadingDialog();
        if (data!=null){
            payResult(data,data.getPayType());
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            srlRefresh.autoRefresh();
        }
    }
    public void payResult(Order order,int payType){
        switch (payType){
        case  Constant.PAY_TYPE_WXPAY:
            if (TextUtils.isEmpty(order.getVendorResponse())){
                showShortToast(R.string.can_get_order);
                return;
            }
            WxPay.getInstance().pay(order.getVendorResponse(), new WxPay.OnResultListener() {
                @Override
                public void onResult(boolean success, String errMsg) {
                    srlRefresh.autoRefresh();
                }
            });
            break;
        case  Constant.PAY_TYPE_ALIPAY:
            if (TextUtils.isEmpty(order.getVendorResponse())){
                showShortToast(R.string.can_get_order);
                return;
            }
            AliPay.getInstance().pay(this, order.getVendorResponse(), new AliPay.OnResultListener() {
                @Override
                public void onResult(boolean success, String errMsg) {
                    srlRefresh.autoRefresh();
                }
            });
            break;
    }
}
}
