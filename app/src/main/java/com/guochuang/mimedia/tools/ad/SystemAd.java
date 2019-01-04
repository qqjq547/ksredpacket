package com.guochuang.mimedia.tools.ad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import org.json.JSONObject;

import java.util.List;

public class SystemAd {
    private ViewGroup.LayoutParams rllp;
    public void init(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width / 3 * 2;
        rllp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

//        AdInfo.VendorAdListBeanX vendorAd = null;
//            for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
//                if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
//                    adDetails = adDetailsBean;
//                }
//            }
//            NativeExpressAD nativeExpressAD = new NativeExpressAD(
//                    context,
//                    new com.qq.e.ads.nativ.ADSize(com.qq.e.ads.nativ.ADSize.FULL_WIDTH, com.qq.e.ads.nativ.ADSize.AUTO_HEIGHT),
//                    adDetails.getAppId(),
//                    adDetails.getLocationId(),
//                    new NativeExpressAD.NativeExpressADListener() {
//                        @Override
//                        public void onADLoaded(List<NativeExpressADView> list) {
//                            // 释放前一个 NativeExpressADView 的资源
//                            if (nativeExpressADView != null) {
//                                nativeExpressADView.destroy();
//                            }
//                            // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
//                            nativeExpressADView = list.get(0);
//                            if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
//                                nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
//                                    @Override
//                                    public void onVideoInit(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoLoading(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoStart(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoPause(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoComplete(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
//
//                                    }
//
//                                    @Override
//                                    public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
//
//                                    }
//                                });
//                            }
//                            nativeExpressADView.render();
//                            if (viewGroup.getChildCount() > 0) {
//                                viewGroup.removeAllViews();
//                            }
//
//                            // 需要保证 View 被绘制的时候是可见的，否则将无法产生曝光和收益。
//                            viewGroup.addView(nativeExpressADView);
//                            nativeExpressADView.render();
//                        }
//
//                        @Override
//                        public void onRenderFail(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onADExposure(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onADClicked(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onADClosed(NativeExpressADView nativeExpressADView) {
//                            if (viewGroup != null && viewGroup.getChildCount() > 0) {
//                                viewGroup.removeAllViews();
//                            }
//                        }
//
//                        @Override
//                        public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
//
//                        }
//
//                        @Override
//                        public void onNoAD(AdError adError) {
//                            systemAd(sysImaUrl, sysGoUrl);
//                        }
//                    });
//            nativeExpressAD.setVideoOption(new VideoOption.Builder()
//                    .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
//                    .setAutoPlayMuted(true) // 自动播放时为静音
//                    .build());
//            nativeExpressAD.loadAD(1);
//
//        for (AdInfo.VendorAdListBeanX.VendorAdListBean adDetailsBean : vendorAd.getVendorAdList()) {
//            if (Double.parseDouble(adDetailsBean.getType()) == 1002) {
//                adDetails = adDetailsBean;
//            }
//        }
//        AdView adView = new AdView(context, adDetails.getLocationId());
//        adView.setListener(new AdViewListener() {
//            @Override
//            public void onAdReady(AdView adView) {
//
//            }
//
//            @Override
//            public void onAdShow(JSONObject jsonObject) {
//
//            }
//
//            @Override
//            public void onAdClick(JSONObject jsonObject) {
//
//            }
//
//            @Override
//            public void onAdFailed(String s) {
//                systemAd(sysImaUrl, sysGoUrl);
//            }
//
//            @Override
//            public void onAdSwitch() {
//
//            }
//
//            @Override
//            public void onAdClose(JSONObject jsonObject) {
//
//            }
//        });
//        viewGroup.addView(adView, rllp);
//        }
    }

    private void show(final Context context, final ViewGroup viewGroup, String sysImaUrl, final String sysGoUrl) {
        if (TextUtils.isEmpty(sysImaUrl)) {
            return;
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
}
