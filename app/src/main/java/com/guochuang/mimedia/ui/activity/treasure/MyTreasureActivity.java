package com.guochuang.mimedia.ui.activity.treasure;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.presenter.MyTreasurePresenter;
import com.guochuang.mimedia.mvp.view.MyTreasureView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.UrlConfig;
import com.guochuang.mimedia.tools.pay.AliPay;
import com.guochuang.mimedia.tools.pay.WxPay;
import com.guochuang.mimedia.ui.activity.user.MyAddressActivity;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.adapter.MyTreasureAdapter;
import com.guochuang.mimedia.ui.fragment.TreasureFragment;
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
    @BindView(R.id.tb_list)
    TabLayout tbList;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    MyFragmentPagerAdapter pagerAdapter;
    TreasureFragment[] fragments=new TreasureFragment[2];

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
        String[] titleArr = getResources().getStringArray(R.array.treasure_nav);
        fragments[0]=new TreasureFragment();
        fragments[0].setWin(false);
        fragments[1]=new TreasureFragment();
        fragments[1].setWin(true);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titleArr);
        vpContent.setAdapter(pagerAdapter);
        tbList.setupWithViewPager(vpContent);

    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }


    public void getOrder(long orderId){
        showLoadingDialog(null);
        mvpPresenter.getOrderVendor(orderId);
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
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            fragments[0].autoRefresh();
            fragments[1].autoRefresh();
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
                    showPayResult(success,errMsg);
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
                    showPayResult(success,errMsg);
                }
            });
            break;
    }
}
    public void showPayResult(boolean success,String errmsg){
        if (success){
            new DialogBuilder(this)
                    .setTitle(R.string.tip)
                    .setMessage(R.string.pay_success)
                    .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragments[0].autoRefresh();
                            fragments[1].autoRefresh();
                        }
                    }).create().show();
        }else {
            new DialogBuilder(this)
                    .setTitle(R.string.tip)
                    .setMessage(TextUtils.isEmpty(errmsg)?getString(R.string.pay_fail):errmsg)
                    .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).create().show();
        }
    }
}
