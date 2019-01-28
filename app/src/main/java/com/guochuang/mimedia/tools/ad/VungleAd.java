package com.guochuang.mimedia.tools.ad;

import android.content.Context;
import android.os.Handler;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.tools.LogUtil;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;

public class VungleAd {
   public static final String appId = "5c2303fdd71d110017b40171";
   public static final String adId="DEFAULT-5224715";

   public VungleAd(Context context){
       Vungle.init(appId,context, new InitCallback() {
           @Override
           public void onSuccess() {
               if (Vungle.isInitialized()) {
                   Vungle.loadAd(adId, new LoadAdCallback() {
                       @Override
                       public void onAdLoad(String s) {

                       }

                       @Override
                       public void onError(String s, Throwable throwable) {

                       }
                   });
               }
           }

           @Override
           public void onError(Throwable throwable) {
           }

           @Override
           public void onAutoCacheAdAvailable(String s) {
               LogUtil.d("onAutoCacheAdAvailable");
           }
       });

   }

    public void showVideo(OnVideoShowResultListener listener) {
        try {
            if (Vungle.canPlayAd(adId)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Vungle.playAd(adId, new AdConfig(), new PlayAdCallback() {
                            @Override
                            public void onAdStart(String s) {

                            }

                            @Override
                            public void onAdEnd(String placementReferenceId, boolean completed, boolean isCTAClicked) {
                                if (completed) {
                                    if (listener != null) {
                                        listener.onVideoFinish();
                                    }
                                }
                            }

                            @Override
                            public void onError(String s, Throwable throwable) {
                                if (listener != null) {
                                    listener.onFailed(s + throwable.getMessage());
                                }
                            }
                        });
                    }
                }, 500);

            } else {
                Vungle.loadAd(adId, new LoadAdCallback() {
                    @Override
                    public void onAdLoad(String s) {
                        showVideo(listener);
                    }

                    @Override
                    public void onError(String s, Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(s + throwable.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onFailed(e.getMessage());
            }
        }
    }
}
