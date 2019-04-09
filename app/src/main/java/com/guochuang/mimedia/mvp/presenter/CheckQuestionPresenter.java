package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AnswerFrom;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.mvp.view.CheckQuestionView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CheckQuestionPresenter extends BasePresenter<CheckQuestionView> {

    public CheckQuestionPresenter(CheckQuestionView view) {
        attachView(view);
    }

    public void getQuestion(long surveyId,String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getProblems(surveyId,redPacketUuid)), new ApiCallback<LookVideoResult>() {
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
