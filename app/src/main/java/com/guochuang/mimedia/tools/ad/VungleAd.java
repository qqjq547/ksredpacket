package com.guochuang.mimedia.tools.ad;

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

    public void init(){
        if (!Vungle.isInitialized()){
            Vungle.init(appId, App.getInstance(), new InitCallback() {
                @Override
                public void onSuccess() {
                   init();
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onAutoCacheAdAvailable(String s) {

                }
            });
        }else {
            if (!Vungle.canPlayAd(adId)) {
                Vungle.loadAd(adId, new LoadAdCallback() {
                    @Override
                    public void onAdLoad(String s) {
                      show();
                    }
                    @Override
                    public void onError(String s, Throwable throwable) {

                    }
                });
            }
        }
    }
    public void show(){
        Vungle.playAd(adId, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String s) {

            }

            @Override
            public void onAdEnd(String s, boolean b, boolean b1) {

            }

            @Override
            public void onError(String s, Throwable throwable) {

            }
        });
    }
}
