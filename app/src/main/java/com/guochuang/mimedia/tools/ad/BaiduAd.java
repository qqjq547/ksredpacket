package com.guochuang.mimedia.tools.ad;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

import org.json.JSONObject;

public class BaiduAd {
    public Context context;
    public BaiduAd(Context context,String appId){
        this.context=context;
        AdView.setAppSid(context, appId);
    }
    public void showSplash(ViewGroup viewGroup,String placeId,final OnShowResultListener listener){
        SplashAd splashAd = new SplashAd(context, viewGroup, new SplashAdListener() {
            @Override
            public void onAdPresent() {
                if (listener != null) {
                    listener.onShowSuccess();
                }
            }

            @Override
            public void onAdDismissed() {
                if (listener != null) {
                    listener.onDismiss();
                }
            }

            @Override
            public void onAdFailed(String s) {
                if (listener != null) {
                    listener.onFailed(s);
                }
            }

            @Override
            public void onAdClick() {

            }
        }, placeId, true);
    }
    public void showBanner(ViewGroup viewGroup,ViewGroup.LayoutParams lp,String placeId,final OnShowResultListener listener){

        AdView adView = new AdView(context, placeId);
        adView.setListener(new AdViewListener() {
            @Override
            public void onAdReady(AdView adView) {

            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                if (listener != null) {
                    listener.onShowSuccess();
                }
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {

            }

            @Override
            public void onAdFailed(String s) {
                if (listener != null) {
                    listener.onFailed(s);
                }
            }

            @Override
            public void onAdSwitch() {

            }

            @Override
            public void onAdClose(JSONObject jsonObject) {
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        viewGroup.addView(adView, lp);
    }
}
