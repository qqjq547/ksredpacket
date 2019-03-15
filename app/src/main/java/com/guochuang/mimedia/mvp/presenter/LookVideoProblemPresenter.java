package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.view.LookView;
import com.guochuang.mimedia.tools.RxUtil;

public class LookVideoProblemPresenter extends BasePresenter<LookView> {
    public LookVideoProblemPresenter(LookView view) {
        attachView(view);
    }


    public void getProblems(long surveyId,String redpackId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getProblems(surveyId,redpackId)), new ApiCallback<LookVideoResult>() {
            @Override
            public void onSuccess(LookVideoResult data) {
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
