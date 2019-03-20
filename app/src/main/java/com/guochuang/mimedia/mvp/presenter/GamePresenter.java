package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.GameView;
import com.guochuang.mimedia.mvp.model.JxwUserInfoUrl;
import com.guochuang.mimedia.tools.RxUtil;

public class GamePresenter extends BasePresenter<GameView> {

    public GamePresenter(GameView view) {
        attachView(view);
    }

    public void getJxwUserInfoUrl(String deviceCode,String from){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getJxwUserInfoUrl(deviceCode,from)), new ApiCallback<JxwUserInfoUrl>() {
            @Override
            public void onSuccess(JxwUserInfoUrl data) {
                mvpView.setData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
