package com.guochuang.mimedia.ui.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.ForgetEmailFragment;
import com.guochuang.mimedia.ui.fragment.ForgetMobileFragment;
import com.guochuang.mimedia.ui.fragment.TradePwdEmailFragment;
import com.guochuang.mimedia.ui.fragment.TradePwdMobileFragment;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.TradePwdPresenter;
import com.guochuang.mimedia.mvp.view.TradePawView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import rx.functions.Action0;

public class TradePwdActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_list)
    TabLayout tbList;
    @BindView(R.id.vp_content)
    ViewPager vpContent;


    Fragment[] fragments = new Fragment[2];

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_trade_pwd;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.trade_pwd);
        UserInfo userInfo=App.getInstance().getUserInfo();
        List<Fragment> list=new ArrayList<>();
        List<String> titleArr=new ArrayList<>();
        if (!TextUtils.isEmpty(userInfo.getMobile())){
            list.add(new TradePwdMobileFragment());
            titleArr.add(getString(R.string.mobile));
        }
        if (!TextUtils.isEmpty(userInfo.getEmailAddress())){
            list.add(new TradePwdEmailFragment());
            titleArr.add(getString(R.string.email));
        }
        fragments = list.toArray(new Fragment[0]);
        String[] titles=titleArr.toArray(new String[0]);
        if (titles.length==1){
            tbList.setVisibility(View.GONE);
        }else {
            tbList.setVisibility(View.VISIBLE);
        }
        MyFragmentPagerAdapter redbagDynamicAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpContent.setAdapter(redbagDynamicAdapter);
        tbList.setupWithViewPager(vpContent);
        tbList.setTabIndicatorFullWidth(false);
    }


    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}
