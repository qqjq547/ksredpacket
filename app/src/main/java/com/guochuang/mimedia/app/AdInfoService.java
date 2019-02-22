package com.guochuang.mimedia.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.guochuang.mimedia.mvp.presenter.AdInfoPresenter;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.tools.PrefUtil;

public class AdInfoService extends Service {
    AdInfoPresenter adInfoPresenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        adInfoPresenter = new AdInfoPresenter(new AdInfoView() {
            @Override
            public void setData(String data) {
                PrefUtil.getInstance().setString(PrefUtil.ADVER_MESSAGE, data);
                stopSelf();
            }

            @Override
            public void setError(String msg) {

            }
        });
        adInfoPresenter.commonAdGetVedorAd();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (adInfoPresenter != null) {
            adInfoPresenter.detachView();
        }
        super.onDestroy();
    }
}
