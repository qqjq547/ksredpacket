package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LookSurveyResult;
import com.guochuang.mimedia.mvp.model.LuckyResult;
import com.guochuang.mimedia.mvp.view.LookView;
import com.guochuang.mimedia.mvp.view.LuckyView;
import com.guochuang.mimedia.tools.RxUtil;

public class LookSurveyPresenter extends BasePresenter<LookView> {
    public LookSurveyPresenter(LookView view) {
        attachView(view);
    }


    public void getProblems() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getProblems()), new ApiCallback<LookSurveyResult>() {
            @Override
            public void onSuccess(LookSurveyResult data) {
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
