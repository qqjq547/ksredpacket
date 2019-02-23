package com.guochuang.mimedia.ui.activity.beenest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NestStatistics;
import com.guochuang.mimedia.mvp.presenter.MyAdPresneter;
import com.guochuang.mimedia.mvp.view.MyAdView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.AdListFragment;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAdActivity extends MvpActivity<MyAdPresneter> implements MyAdView {

    MyFragmentPagerAdapter pagerAdapter;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_ad_count)
    TextView tvAdCount;
    @BindView(R.id.tv_ad_showing)
    TextView tvAdShowing;
    @BindView(R.id.tv_ad_money)
    TextView tvAdMoney;
    @BindView(R.id.tv_show_num)
    TextView tvShowNum;
    @BindView(R.id.tv_click_num)
    TextView tvClickNum;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    @BindView(R.id.tb_list)
    TabLayout tbList;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    AdListFragment[] fragments = new AdListFragment[4];

    @Override
    protected MyAdPresneter createPresenter() {
        return new MyAdPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ad;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_ad);
        tvText.setText(R.string.my_bid_buy);
        setFragment();
        mvpPresenter.getMyStatistics();
    }

    private void setFragment() {
        fragments[0] = new AdListFragment();
        fragments[1] = new AdListFragment();
        fragments[1].setStatus(0);
        fragments[2] = new AdListFragment();
        fragments[2].setStatus(1);
        fragments[3] = new AdListFragment();
        fragments[3].setStatus(2);
        String[] titleArr = getResources().getStringArray(R.array.ad_nav);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titleArr);
        vpContent.setAdapter(pagerAdapter);
        tbList.setupWithViewPager(vpContent);
    }

    @OnClick({R.id.iv_back, R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this,MyBidActivity.class));
                break;
        }
    }

    @Override
    public void setStatistics(NestStatistics data) {
        if (data!=null){
            tvAdCount.setText(String.valueOf(data.getAdNumber()));
            tvAdShowing.setText(String.valueOf(data.getPutIn()));
            tvAdMoney.setText(String.valueOf(data.getCountMoney()));
            tvShowNum.setText(String.valueOf(data.getShowQuantity()));
            tvClickNum.setText(String.valueOf(data.getClickQuantity()));
            tvCollectNum.setText(String.valueOf(data.getFavoriteQuantity()));
        }
    }

    @Override
    public void setError(String msg) {
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==Constant.REQUEST_EDIT_NESTAD){
            setFragment();
            mvpPresenter.getMyStatistics();
        }
    }
}
