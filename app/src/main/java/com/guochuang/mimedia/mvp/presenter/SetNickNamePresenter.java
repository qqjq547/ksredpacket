package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.SetNickNameView;
import com.guochuang.mimedia.tools.RxUtil;

public class SetNickNamePresenter extends BasePresenter<SetNickNameView> {
    public SetNickNamePresenter(SetNickNameView view) {
        attachView(view);
    }
    public void addUserInfo(String nickName){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().setNickName(
                nickName
        )), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
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
