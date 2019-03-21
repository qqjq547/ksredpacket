package com.guochuang.mimedia.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.guochuang.mimedia.app.AdInfoService;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BaseActivity;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.WelcomePresenter;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.AdCollectionView;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.activity.user.BindingPhoneAcitivity;
import com.guochuang.mimedia.ui.activity.user.LoginActivity;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity implements WelcomeView {


    @BindView(R.id.ll_ad)
    LinearLayout llAd;

    WelcomePresenter mvpPresenter;

    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(getLayout());
        initViewAndData();
        ButterKnife.bind(this);
        App.getInstance().addActivity(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViewAndData() {
        startService(new Intent(this, AdInfoService.class));
        // gdt "1108034968", "5030540729471947"
        AdCollectionView adCollectionView = new AdCollectionView(this, llAd, new AdCollectionView.AdCollectionListener() {
            @Override
            public void onAdDismissed() {
                next();
            }

            @Override
            public void onAdFailed(String s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                }, 2000);
            }
        });
        adCollectionView.init(AdCollectionView.TYPE_BD, AdCollectionView.LOCATION_TAIL, null, null);
    }

    private void next() {
        if (getPref().getBoolean(PrefUtil.FIRSTOPEN, true)) {
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
            getPref().setBoolean(PrefUtil.FIRSTOPEN, false);
        } else {
            if (TextUtils.isEmpty(getPref().getUserToken())) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            } else {
                if (TextUtils.isEmpty(getPref().getString(PrefUtil.MOBILE, null))) {
                    startActivity(new Intent(WelcomeActivity.this, BindingPhoneAcitivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }
        }
        finish();
    }

    @Override
    public void setData(String data) {

    }

    @Override
    public void setError(String msg) {
        showShortToast(msg);
    }
}
