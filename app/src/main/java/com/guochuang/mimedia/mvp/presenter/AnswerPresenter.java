package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.view.AdBidView;
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

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
    public void videoSubmit(String channelCode,String clientIp,long userAccountId,String redPacketUuid,String latitude,String longitude,String submitJson){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                videoSubmit(channelCode,clientIp,userAccountId,redPacketUuid,latitude,longitude,submitJson)), new ApiCallback<RedbagDetail>() {
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
    public void surveySubmit(String channelCode,String clientIp,long userAccountId,String redPacketUuid,String latitude,String longitude,String submitJson){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                surveySubmit(channelCode,clientIp,userAccountId,redPacketUuid,latitude,longitude,submitJson)), new ApiCallback<RedbagDetail>() {
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
