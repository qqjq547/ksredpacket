package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.mvp.model.LookVideoPBResult;
import com.guochuang.mimedia.mvp.view.LookVideoProblemView;
import com.guochuang.mimedia.tools.RxUtil;

public class LookVideoProblemPresenter extends BasePresenter<LookVideoProblemView> {
    public LookVideoProblemPresenter(LookVideoProblemView view) {
        attachView(view);
    }


    public void getVideoProblemAnswerList(long surveyId, String redpackId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getVideoProblemAnswerList(surveyId, redpackId)), new ApiCallback<LookSurevyResult>() {
            @Override
            public void onSuccess(LookSurevyResult data) {
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
