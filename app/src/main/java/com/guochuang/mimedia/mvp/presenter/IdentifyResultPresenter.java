package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NameAuthGet;
import com.guochuang.mimedia.mvp.view.IdentifyResultView;
import com.guochuang.mimedia.tools.RxUtil;

public class IdentifyResultPresenter extends BasePresenter<IdentifyResultView> {
    public IdentifyResultPresenter(IdentifyResultView view) {
        attachView(view);
    }
    public void userNameAuthGet() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userNameAuthGet()), new ApiCallback<NameAuthGet>() {
            @Override
            public void onSuccess(NameAuthGet data) {
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
