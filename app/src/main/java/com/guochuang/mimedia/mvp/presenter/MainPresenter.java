package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NameAuthAndSafety;
import com.guochuang.mimedia.mvp.model.RainMsg;
import com.guochuang.mimedia.mvp.model.Remind;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.model.VersionMsg;
import com.guochuang.mimedia.mvp.view.MainView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void getRainMsg() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketRainGet()), new ApiCallback<RainMsg>() {
            @Override
            public void onSuccess(RainMsg data) {
                mvpView.setRain(data);

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

    public void setRainTip(String redPacketRainUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketRainIsTip(redPacketRainUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setRainTip(data);

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

    public void getUserInfo(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getUserInfo()), new ApiCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                mvpView.setUserInfo(data);

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

    public void isNameAuthAndSafetyCode() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                safeStatus()), new ApiCallback<NameAuthAndSafety>() {
            @Override
            public void onSuccess(NameAuthAndSafety data) {
                mvpView.setNameAuthSafefy(data);

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
    public void messageIsNews() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                messageIsNews()), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setMessageIsNews(data);

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
    public void getRemind() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getRemind()), new ApiCallback<Remind>() {
            @Override
            public void onSuccess(Remind data) {
                mvpView.setRemind(data);

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
    public void marketSwitch(String marketName,String versionCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                marketSwitch(marketName,versionCode)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setMarketSwitch(data);

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
