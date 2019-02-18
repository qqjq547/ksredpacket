package com.guochuang.mimedia.tools.ad;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class TencentAd {
    private NativeExpressADView nativeExpressADView;
    private String appId;

    public TencentAd(String appId) {
        this.appId = appId;
    }

    public void showSplash(Context context, ViewGroup viewGroup, String placeId, final OnShowResultListener listener){
        SplashAD splashAD = new SplashAD((Activity) context, viewGroup, appId, placeId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                if (listener != null) {
                    listener.onDismiss();
                }
            }

            @Override
            public void onNoAD(AdError adError) {
                if (listener != null) {
                    listener.onFailed(adError.getErrorMsg());
                }
            }

            @Override
            public void onADPresent() {
                if (listener != null) {
                    listener.onShowSuccess();
                }
            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onADExposure() {

            }
        });
    }
    public void showInterstitial(Context context, final ViewGroup viewGroup, String placeId, final OnShowResultListener listener){
        NativeExpressAD nativeExpressAD = new NativeExpressAD(
                context,
                new com.qq.e.ads.nativ.ADSize(com.qq.e.ads.nativ.ADSize.FULL_WIDTH, com.qq.e.ads.nativ.ADSize.AUTO_HEIGHT),
                appId,
                placeId,
                new NativeExpressAD.NativeExpressADListener() {
                    @Override
                    public void onADLoaded(List<NativeExpressADView> list) {
                        // 释放前一个 NativeExpressADView 的资源
                        if (listener!=null) {
                            listener.onShowSuccess();
                        }
                        if (nativeExpressADView != null) {
                            nativeExpressADView.destroy();
                        }
                        // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                        nativeExpressADView = list.get(0);
                        nativeExpressADView.render();
                        if (viewGroup.getChildCount() > 0) {
                            viewGroup.removeAllViews();
                        }

                        // 需要保证 View 被绘制的时候是可见的，否则将无法产生曝光和收益。
                        viewGroup.addView(nativeExpressADView);
                        nativeExpressADView.render();
                    }

                    @Override
                    public void onRenderFail(NativeExpressADView nativeExpressADView) {
                        if (listener!=null) {
                            listener.onFailed("");
                        }
                    }

                    @Override
                    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADExposure(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADClicked(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADClosed(NativeExpressADView nativeExpressADView) {
                        if (viewGroup != null && viewGroup.getChildCount() > 0) {
                            viewGroup.removeAllViews();
                        }
                        if (listener!=null) {
                            listener.onDismiss();
                        }
                    }

                    @Override
                    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onNoAD(AdError adError) {
                       listener.onFailed(adError.getErrorMsg());
                    }
                });
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
                .setAutoPlayMuted(true) // 自动播放时为静音
                .build());
        nativeExpressAD.loadAD(1);
    }
    public void showBanner(Context context,ViewGroup viewGroup,ViewGroup.LayoutParams lp,String placeId,final OnShowResultListener listener){
        BannerView bannerView = new BannerView((Activity) context, ADSize.BANNER,appId, placeId);
        bannerView.setRefresh(30);
        bannerView.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                if (listener!=null)
                listener.onFailed(adError.getErrorMsg());
            }

            @Override
            public void onADReceiv() {
                if (listener!=null)
                listener.onShowSuccess();
            }
        });
        viewGroup.addView(bannerView, lp);
        bannerView.loadAD();
    }
    public void showVideo(Context context, final ViewGroup viewGroup, String appId, String locationId,final OnShowResultListener listener){
        NativeExpressAD nativeExpressAD = new NativeExpressAD(
                context,
                new com.qq.e.ads.nativ.ADSize(com.qq.e.ads.nativ.ADSize.FULL_WIDTH, com.qq.e.ads.nativ.ADSize.AUTO_HEIGHT),
                appId,
                locationId,
                new NativeExpressAD.NativeExpressADListener() {
                    @Override
                    public void onADLoaded(List<NativeExpressADView> list) {
                        if (listener!=null){
                            listener.onShowSuccess();
                        }
                        // 释放前一个 NativeExpressADView 的资源
                        if (nativeExpressADView != null) {
                            nativeExpressADView.destroy();
                        }
                        // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                        nativeExpressADView = list.get(0);
                        nativeExpressADView.render();
                        if (viewGroup.getChildCount() > 0) {
                            viewGroup.removeAllViews();
                        }

                        // 需要保证 View 被绘制的时候是可见的，否则将无法产生曝光和收益。
                        viewGroup.addView(nativeExpressADView);
                        nativeExpressADView.render();
                    }

                    @Override
                    public void onRenderFail(NativeExpressADView nativeExpressADView) {
                        if (listener!=null)
                            listener.onFailed("");
                    }

                    @Override
                    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADExposure(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADClicked(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADClosed(NativeExpressADView nativeExpressADView) {
                        if (viewGroup != null && viewGroup.getChildCount() > 0) {
                            viewGroup.removeAllViews();
                        }
                        if (listener!=null){
                            listener.onDismiss();
                        }
                    }

                    @Override
                    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

                    }

                    @Override
                    public void onNoAD(AdError adError) {
                        if (listener!=null)
                            listener.onFailed(adError.getErrorMsg());
                    }
                });
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
                .setAutoPlayMuted(true) // 自动播放时为静音
                .build());
        nativeExpressAD.loadAD(1);
    }
}
