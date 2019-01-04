package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.SetInviterView;
import com.guochuang.mimedia.tools.RxUtil;

public class SetInviterPresenter extends BasePresenter<SetInviterView> {
    public SetInviterPresenter(SetInviterView view) {
        attachView(view);
    }
    public void addUserInfo(String inviteCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addInviter(inviteCode)), new ApiCallback<Boolean>() {
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
