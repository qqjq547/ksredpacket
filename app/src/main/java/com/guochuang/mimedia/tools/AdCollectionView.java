package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.video.XAdManager;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.BaiduManager;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.baidu.mobads.interfaces.IXAdConstants4PDK;
import com.baidu.mobads.interfaces.IXAdContext;
import com.baidu.mobads.interfaces.IXAdProd;
import com.baidu.mobads.openad.interfaces.event.IOAdEvent;
import com.baidu.mobads.openad.interfaces.event.IOAdEventListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.sz.gcyh.KSHongBao.R;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;

import org.json.JSONObject;

import java.util.List;

import javax.annotation.Nullable;

public class AdCollectionView {

    // 广告样式
    public static final String TYPE_GDT = "tencent";
    public static final String TYPE_BD = "baidu";
    public static final String TYPE_VUNGLE = "vungle";
    public static final String TYPE_SYSTEM = "system";
    //    // 广告位置
    public static final String LOCATION_TAIL = "tail";
    public static final String LOCATION_INFO = "info";
    public static final String LOCATION_REDBAG = "redbag";
    public static final String LOCATION_CELLULAR = "cellular";

    private String currentType = "";

    private Context context;
    private ViewGroup viewGroup;
    private ViewGroup.LayoutParams rllp;
    private AdCollectionListener adCollectionListener;
    private AdVideoListener adVideoListener;
    private AdVideoInitListener adVideoInitListener;
    private NativeExpressADView nativeExpressADView;
    private RewardVideoAD rewardVideoAD;

    public AdCollectionView(Context context, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.context = context;
    }

    public AdCollectionView(Context context, ViewGroup viewGroup, AdCollectionListener adCollectionListener) {
        this.viewGroup = viewGroup;
        this.context = context;
        this.adCollectionListener = adCollectionListener;
    }

    public NativeExpressADView getNativeExpressADView() {
        return nativeExpressADView;
    }

    // String appId, String adPlaceId,

    /**
     * @param type      广告类型
     * @param place     广告位置
     * @param sysImaUrl
     * @param sysGoUrl
     */
    public void init(String type, String place, final @Nullable String sysImaUrl, @Nullable final String sysGoUrl) {
        String adInfoStr = PrefUtil.getInstance().getString(PrefUtil.ADVER_MESSAGE, "");
        AdInfo adInfo = null;
        if (adInfoStr.equals("")) {
            if (place.equals(LOCATION_TAIL)) {
                if (adCollectionListener != null) {
                    adCollectionListener.onAdFailed("");
                }
                return;
            } else {
                type = TYPE_SYSTEM;
            }
        } else {
            try {
                adInfo = GsonUtil.GsonToBean(adInfoStr, AdInfo.class);
                if (!place.equals(LOCATION_CELLULAR)) {
                    type = adInfo.getDefaultVendorType();
                } else {
                    type = TYPE_VUNGLE;
                }
                if (TextUtils.isEmpty(type)) {
                    if (place.equals(LOCATION_TAIL)) {
                        if (adCollectionListener != null) {
                            adCollectionListener.onAdFailed("");
                        }
                        return;
                    } else {
                        type = TYPE_SYSTEM;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (adCollectionListener != null) {
                    adCollectionListener.onAdFailed(e.toString());
                }
            }
        }
        if (place.equals(LOCATION_INFO)) {
            // 横幅广告宽高设置
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            int winW = dm.widthPixels;
            int winH = dm.heightPixels;
            int width = Math.min(winW, winH);
            int height = width / 20 * 3;
            rllp = new ViewGroup.LayoutParams(width, height);
        } else if (place.equals(LOCATION_REDBAG)) {
            // 横幅广告宽高设置
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            int winW = dm.widthPixels;
            int winH = dm.heightPixels;
            int width = Math.min(winW, winH);
            int height = width / 3 * 2;
            rllp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
        AdInfo.VendorAdListBeanX vendorAd = null;
        if (type.equals(TYPE_GDT)) {
            for (AdInfo.VendorAdListBeanX vendorAdBean : adInfo.getVendorAdList()) {
                if (vendorAdBean.getVendorType().equals(TYPE_GDT)) {
                    vendorAd = vendorAdBean;
                }
            }
            AdInfo.VendorAdListBeanX.VendorAdListBean adDetails = null;
            if (place.equals(LOCATION_TAIL)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1000) {
                        adDetails = adDetailsBean;
                    }
                }
                if(adDetails==null){
                    if (adCollectionListener != null) {
                        adCollectionListener.onAdFailed("");
                    }
                    return;
                }
                SplashAD splashAD = new SplashAD((Activity) context, viewGroup, adDetails.getAppId(), adDetails.getLocationId(), new SplashADListener() {
                    @Override
                    public void onADDismissed() {
                        if (adCollectionListener != null) {
                            adCollectionListener.onAdDismissed();
                        }
                    }

                    @Override
                    public void onNoAD(AdError adError) {
                        if (adCollectionListener != null) {
                            adCollectionListener.onAdFailed(adError.getErrorMsg());
                        }
                    }

                    @Override
                    public void onADPresent() {

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
            } else if (place.equals(LOCATION_INFO)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1001) {
                        adDetails = adDetailsBean;
                    }
                }
                if(adDetails==null){
                    if (adCollectionListener != null) {
                        adCollectionListener.onAdFailed("");
                    }
                    return;
                }
                BannerView bannerView = new BannerView((Activity) context, ADSize.BANNER, adDetails.getAppId(), adDetails.getLocationId());
                bannerView.setRefresh(30);
                bannerView.setADListener(new AbstractBannerADListener() {
                    @Override
                    public void onNoAD(AdError adError) {
                        systemAd(sysImaUrl, sysGoUrl);
                    }

                    @Override
                    public void onADReceiv() {

                    }
                });
                viewGroup.addView(bannerView, rllp);
                bannerView.loadAD();
            } else if (place.equals(LOCATION_REDBAG)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
                        adDetails = adDetailsBean;
                    }
                }
                if(adDetails==null){
                    if (adCollectionListener != null) {
                        adCollectionListener.onAdFailed("");
                    }
                    return;
                }
                NativeExpressAD nativeExpressAD = new NativeExpressAD(
                        context,
                        new com.qq.e.ads.nativ.ADSize(com.qq.e.ads.nativ.ADSize.FULL_WIDTH, com.qq.e.ads.nativ.ADSize.AUTO_HEIGHT),
                        adDetails.getAppId(),
                        adDetails.getLocationId(),
                        new NativeExpressAD.NativeExpressADListener() {
                            @Override
                            public void onADLoaded(List<NativeExpressADView> list) {
                                // 释放前一个 NativeExpressADView 的资源
                                if (nativeExpressADView != null) {
                                    nativeExpressADView.destroy();
                                }
                                // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                                nativeExpressADView = list.get(0);
                                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                                    nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
                                        @Override
                                        public void onVideoInit(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoLoading(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {

                                        }

                                        @Override
                                        public void onVideoStart(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoPause(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoComplete(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {

                                        }

                                        @Override
                                        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {

                                        }

                                        @Override
                                        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

                                        }
                                    });
                                }
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
                                systemAd(sysImaUrl, sysGoUrl);
                            }
                        });
                nativeExpressAD.setVideoOption(new VideoOption.Builder()
                        .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
                        .setAutoPlayMuted(true) // 自动播放时为静音
                        .build());
                nativeExpressAD.loadAD(1);
            } else if (place.equals(LOCATION_CELLULAR)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
                        adDetails = adDetailsBean;
                    }
                }
                rewardVideoAD = new RewardVideoAD(context, adDetails.getAppId(), adDetails.getLocationId(), new RewardVideoADListener() {
                    @Override
                    public void onADLoad() {

                    }

                    @Override
                    public void onVideoCached() {
                    }

                    @Override
                    public void onADShow() {
                    }

                    @Override
                    public void onADExpose() {
                    }

                    /**
                     * 激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
                     */
                    @Override
                    public void onReward() {
                        if (adVideoListener != null) {
                            adVideoListener.onVideoFinish();
                        }
                    }

                    @Override
                    public void onADClick() {
                    }

                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    public void onADClose() {

                    }

                    @Override
                    public void onError(AdError adError) {
                        if (adVideoListener != null) {
                            adVideoListener.onVideoError(adError.getErrorMsg());
                        }
                    }
                });
                rewardVideoAD.loadAD();
            }
        } else if (type.equals(TYPE_BD)) {
            for (AdInfo.VendorAdListBeanX vendorAdBean : adInfo.getVendorAdList()) {
                if (vendorAdBean.getVendorType().equals(TYPE_BD)) {
                    vendorAd = vendorAdBean;
                    AdView.setAppSid(context, vendorAd.getAppId());
                }
            }
            AdInfo.VendorAdListBeanX.VendorAdListBean adDetails = null;
            if (place.equals(LOCATION_TAIL)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1000) {
                        adDetails = adDetailsBean;
                    }
                }
                SplashAd splashAd = new SplashAd(context, viewGroup, new SplashAdListener() {
                    @Override
                    public void onAdPresent() {

                    }

                    @Override
                    public void onAdDismissed() {
                        if (adCollectionListener != null) {
                            adCollectionListener.onAdDismissed();
                        }
                    }

                    @Override
                    public void onAdFailed(String s) {
                        if (adCollectionListener != null) {
                            adCollectionListener.onAdFailed(s);
                        }
                    }

                    @Override
                    public void onAdClick() {

                    }
                }, adDetails.getLocationId(), true);
            } else if (place.equals(LOCATION_INFO)) {
                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1001) {
                        adDetails = adDetailsBean;
                    }
                }
                AdView adView = new AdView(context, adDetails.getLocationId());
                adView.setListener(new AdViewListener() {
                    @Override
                    public void onAdReady(AdView adView) {

                    }

                    @Override
                    public void onAdShow(JSONObject jsonObject) {

                    }

                    @Override
                    public void onAdClick(JSONObject jsonObject) {

                    }

                    @Override
                    public void onAdFailed(String s) {
                        systemAd(sysImaUrl, sysGoUrl);
                    }

                    @Override
                    public void onAdSwitch() {

                    }

                    @Override
                    public void onAdClose(JSONObject jsonObject) {

                    }
                });
                viewGroup.addView(adView, rllp);
            } else if (place.equals(LOCATION_REDBAG)) {
//                // 信息流广告（暂不用）
//                final ImageView ivAd = new ImageView(context);
//                ivAd.setScaleType(ImageView.ScaleType.FIT_XY);
//                ivAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                BaiduNative baiduNative = new BaiduNative(context, "", new BaiduNative.BaiduNativeNetworkListener() {
//                    @Override
//                    public void onNativeLoad(List<NativeResponse> list) {
//                        if (list.size() == 0) {
//                            GlideImgManager.loadImage(context, sysImaUrl, ivAd);
//                            return;
//                        }
//                        View view = LayoutInflater.from(context).inflate(R.layout.layout_redbag_bd_adver, viewGroup);
//                        RelativeLayout rlTemplate1 = view.findViewById(R.id.rlTemplate1);
//                        for (final NativeResponse nativeResponse : list) {
//                            if (nativeResponse.isAdAvailable(context)) {
//                                AQuery aq = new AQuery(view);
//                                aq.id(R.id.iv_title).text(nativeResponse.getTitle());
//                                aq.id(R.id.iv_icon).image(nativeResponse.getIconUrl());
//                                aq.id(R.id.iv_main).image(nativeResponse.getImageUrl());
//                                //添加广告标示和百度 logo
//                                aq.id(R.id.iv_adlogo).image(nativeResponse.getAdLogoUrl(), false, true);
//                                aq.id(R.id.iv_baidulogo).image(nativeResponse.getBaiduLogoUrl(), false, true);
//                                nativeResponse.recordImpression(rlTemplate1);//  警告：调用该函数来发送展现，勿漏！
//                                rlTemplate1.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        nativeResponse.handleClick(view);//  点击响应
//                                    }
//                                });
//                                return;
//                            } else {
//                                if (list.get(list.size() - 1) == nativeResponse) {
//                                    AQuery aq = new AQuery(view);
//                                    aq.id(R.id.iv_title).text(nativeResponse.getTitle());
//                                    aq.id(R.id.iv_icon).image(nativeResponse.getIconUrl());
//                                    aq.id(R.id.iv_main).image(nativeResponse.getImageUrl());
//                                    //添加广告标示和百度 logo
//                                    aq.id(R.id.iv_adlogo).image(nativeResponse.getAdLogoUrl(), false, true);
//                                    aq.id(R.id.iv_baidulogo).image(nativeResponse.getBaiduLogoUrl(), false, true);
//                                    nativeResponse.recordImpression(rlTemplate1);//  警告：调用该函数来发送展现，勿漏！
//                                    rlTemplate1.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            nativeResponse.handleClick(view);//  点击响应
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                        viewGroup.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    }
//
//                    @Override
//                    public void onNativeFail(NativeErrorCode nativeErrorCode) {
//                        GlideImgManager.loadImage(context, sysImaUrl, ivAd);
//                        viewGroup.addView(ivAd, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    }
//                });
//                RequestParameters requestParameters = new RequestParameters.Builder()
//                        .downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ALWAYS)
//                        .build();
//                baiduNative.makeRequest(requestParameters);

                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
                    if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
                        adDetails = adDetailsBean;
                    }
                }
                AdView adView = new AdView(context, adDetails.getLocationId());
                adView.setListener(new AdViewListener() {
                    @Override
                    public void onAdReady(AdView adView) {

                    }

                    @Override
                    public void onAdShow(JSONObject jsonObject) {

                    }

                    @Override
                    public void onAdClick(JSONObject jsonObject) {

                    }

                    @Override
                    public void onAdFailed(String s) {
                        systemAd(sysImaUrl, sysGoUrl);
                    }

                    @Override
                    public void onAdSwitch() {

                    }

                    @Override
                    public void onAdClose(JSONObject jsonObject) {

                    }
                });
                viewGroup.addView(adView, rllp);
            } else if (place.equals(LOCATION_CELLULAR)) {
//                for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
//                    if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
//                        adDetails = adDetailsBean;
//                    }
//                }
//                final String placeId = adDetails.getLocationId();
//                AdView.setAppSid(context, "c8a9f32f");
//                interstitialAd = new InterstitialAd(context, "6012509");
//                interstitialAd.setListener(new InterstitialAdListener() {
//                    @Override
//                    public void onAdReady() {
//
//                    }
//
//                    @Override
//                    public void onAdPresent() {
//
//                    }
//
//                    @Override
//                    public void onAdClick(InterstitialAd interstitialAd) {
//
//                    }
//
//                    @Override
//                    public void onAdDismissed() {
//
//                    }
//
//                    @Override
//                    public void onAdFailed(String s) {
//
//                    }
//                });
//                interstitialAd.loadAd();
            }
        } else if (type.equals(TYPE_VUNGLE)) {
            currentType = type;
            if (place.equals(LOCATION_CELLULAR)) {
                String appId = "5c2303fdd71d110017b40171";
                Vungle.init(appId, App.getInstance(), new InitCallback() {
                    @Override
                    public void onSuccess() {
                        if (Vungle.isInitialized()) {
                            Vungle.loadAd("DEFAULT-5224715", new LoadAdCallback() {
                                @Override
                                public void onAdLoad(String s) {
                                    if (adVideoInitListener != null) {
                                        adVideoInitListener.onInit();
                                    }
                                }

                                @Override
                                public void onError(String s, Throwable throwable) {
                                    if (adVideoInitListener != null) {
                                        adVideoInitListener.onError(throwable.getMessage());
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (adVideoInitListener != null) {
                            adVideoInitListener.onError(throwable.getMessage());
                        }
                    }

                    @Override
                    public void onAutoCacheAdAvailable(String s) {
                        LogUtil.d("onAutoCacheAdAvailable");
                    }
                });

            }

        } else {
            if (place.equals(LOCATION_TAIL)) {
                if (adCollectionListener != null) {
                    adCollectionListener.onAdFailed("");
                }
                return;
            }
            if (place.equals(LOCATION_CELLULAR)) {
                if (adCollectionListener != null) {
                    adCollectionListener.onAdFailed(context.getResources().getString(R.string.adver_error_call_service));
                }
                return;
            }
            if (TextUtils.isEmpty(sysGoUrl) || TextUtils.isEmpty(sysImaUrl)) {
                return;
            }
            systemAd(sysImaUrl, sysGoUrl);
        }
    }

    private void systemAd(String sysImaUrl, final String sysGoUrl) {
        if (TextUtils.isEmpty(sysImaUrl)) {
            return;
        }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if(((Activity)context).isDestroyed()){
                    return;
                }
            }
        GlideApp.with(context).asBitmap().load(sysImaUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @android.support.annotation.Nullable Transition<? super Bitmap> transition) {
                ImageView iv = new ImageView(context);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageBitmap(resource);
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.height = (int) ((resource.getHeight() / (float) resource.getWidth()) * dm.widthPixels);
                iv.setLayoutParams(lp);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(sysGoUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                });
                viewGroup.addView(iv);
            }
        });
    }

    public void setAdCollectionListener(AdCollectionListener adCollectionListener) {
        this.adCollectionListener = adCollectionListener;
    }

    public void setAdVideoListener(AdVideoListener adVideoListener) {
        this.adVideoListener = adVideoListener;
    }

    public void setAdVideoInitListener(AdVideoInitListener adVideoInitListener) {
        this.adVideoInitListener = adVideoInitListener;
    }

    /**
     * 播放激励视屏
     */
    public void showRewardVideoAD() {
        if (rewardVideoAD != null) {
            rewardVideoAD.showAD();
        }
        if (currentType.equals(TYPE_VUNGLE)) {
            try {
                if (Vungle.canPlayAd("DEFAULT-5224715")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Vungle.playAd("DEFAULT-5224715", new AdConfig(), new PlayAdCallback() {
                                @Override
                                public void onAdStart(String s) {

                                }

                                @Override
                                public void onAdEnd(String placementReferenceId, boolean completed, boolean isCTAClicked) {
                                    if (completed) {
                                        if (adVideoListener != null) {
                                            adVideoListener.onVideoFinish();
                                        }
                                    }
                                }

                                @Override
                                public void onError(String s, Throwable throwable) {
                                    if (adVideoListener != null) {
                                        adVideoListener.onVideoError(s + throwable.getMessage());
                                    }
                                }
                            });
                        }
                    }, 500);

                } else {
                    Vungle.loadAd("DEFAULT-5224715", new LoadAdCallback() {
                        @Override
                        public void onAdLoad(String s) {
                            showRewardVideoAD();
                        }

                        @Override
                        public void onError(String s, Throwable throwable) {
                            if (adVideoListener != null) {
                                adVideoListener.onVideoError(s + throwable.getMessage());
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (adVideoListener != null) {
                    adVideoListener.onVideoError(e.getMessage());
                }
            }
        }

//        if (interstitialAd != null) {
//            if (interstitialAd.isAdReady()) {
//                interstitialAd.showAd((Activity) context);
//            } else {
//                interstitialAd.loadAd();
//            }
//        }
    }

    public interface AdCollectionListener {
        void onAdDismissed();

        void onAdFailed(String s);
    }

    public interface AdVideoInitListener {
        void onInit();

        void onError(String msg);
    }

    public interface AdVideoListener {
        void onVideoFinish();

        void onVideoError(String msg);
    }
}
