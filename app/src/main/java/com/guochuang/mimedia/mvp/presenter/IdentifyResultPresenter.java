package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NameAuthGet;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.IdentifyResultView;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
