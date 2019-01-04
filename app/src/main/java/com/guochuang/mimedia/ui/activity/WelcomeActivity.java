package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.google.gson.Gson;
import com.guochuang.mimedia.app.AdInfoService;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.mvp.presenter.WelcomePresenter;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.AdCollectionView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.sz.gcyh.KSHongBao.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends MvpActivity<WelcomePresenter> implements WelcomeView {


    @BindView(R.id.ll_ad)
    LinearLayout llAd;

    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
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
