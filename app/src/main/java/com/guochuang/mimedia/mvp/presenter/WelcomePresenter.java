package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.VersionMsg;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

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
public void getVersion(String systemCode,String sequence){
    addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().versionGet(systemCode,sequence)), new ApiCallback<VersionMsg>() {
        @Override
        public void onSuccess(VersionMsg data) {
            mvpView.setVersion(data);

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
