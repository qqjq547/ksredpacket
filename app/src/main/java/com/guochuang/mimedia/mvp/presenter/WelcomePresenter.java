package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.mvp.view.WelcomeView;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class WelcomePresenter extends BasePresenter<WelcomeView> {
    public WelcomePresenter(WelcomeView view) {
        attachView(view);
    }
//    public void queryMap(){
//        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().queryMap()), new ApiCallback<String>() {
//            @Override
//            public void onSuccess(String data) {
//                mvpView.setRedbagDetail(data);
//
//            }
//
//            @Override
//            public void onFailure(ApiException exception) {
//                mvpView.setError(exception.getMessage());
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }

}
