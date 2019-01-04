package com.guochuang.mimedia.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.guochuang.mimedia.mvp.presenter.AdInfoPresneter;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.tools.PrefUtil;

public class AdInfoService extends Service {
    AdInfoPresneter adInfoPresneter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        adInfoPresneter = new AdInfoPresneter(new AdInfoView() {
            @Override
            public void setData(String data) {
                PrefUtil.getInstance().setString(PrefUtil.ADVER_MESSAGE, data);
                stopSelf();
            }

            @Override
            public void setError(String msg) {

            }
        });
        adInfoPresneter.commonAdGetVedorAd();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (adInfoPresneter != null) {
            adInfoPresneter.detachView();
        }
        super.onDestroy();
    }
}
