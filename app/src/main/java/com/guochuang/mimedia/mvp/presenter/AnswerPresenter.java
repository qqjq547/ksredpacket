package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AnswerFrom;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AnswerPresenter extends BasePresenter<AnswerView> {

    public AnswerPresenter(AnswerView view) {
        attachView(view);
    }

    public void getProblems(long surveyId,String redPacketUuid){
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
    public void getRemain(String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getRemain(redPacketUuid)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setRemain(data);
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
    public void videoSubmit(AnswerFrom answerFrom){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, GsonUtil.GsonString(answerFrom));
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                videoSubmit(body)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
                mvpView.setRedbagDetail(data);
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

    public void surveySubmit(AnswerFrom answerFrom){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, GsonUtil.GsonString(answerFrom));
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                surveySubmit(body)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
                mvpView.setRedbagDetail(data);
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
