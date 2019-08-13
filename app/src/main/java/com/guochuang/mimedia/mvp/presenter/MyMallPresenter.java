package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MallBidRecord;
import com.guochuang.mimedia.mvp.model.MallNodeResult;
import com.guochuang.mimedia.mvp.model.MallRegionDetail;
import com.guochuang.mimedia.mvp.model.MyMallRecord;
import com.guochuang.mimedia.mvp.model.MyMallStat;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.view.AdBidView;
import com.guochuang.mimedia.mvp.view.MyMallView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class MyMallPresenter extends BasePresenter<MyMallView> {

    public MyMallPresenter(MyMallView view) {
        attachView(view);
    }

    public void getMyMallStat(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                myMallStat()), new ApiCallback<MyMallStat>() {
            @Override
            public void onSuccess(MyMallStat data) {
                mvpView.setMyStat(data);
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

    public void getMyMallList(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                myMallList(currentPage,pageSize)), new ApiCallback<Page<MyMallRecord>>() {
            @Override
            public void onSuccess(Page<MyMallRecord> data) {
                mvpView.setList(data);
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

    public void getMyNodeResult(long mallRegionId,int nodeNumber){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mallNodeResult(mallRegionId,nodeNumber)), new ApiCallback<MallNodeResult>() {
            @Override
            public void onSuccess(MallNodeResult data) {
                mvpView.setMallNodeResult(data);
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

    public void getMallNodeOrderList(long mallRegionId,int nodeNumber){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mallNodeOrderList(mallRegionId,nodeNumber)), new ApiCallback<List<MallBidRecord>>() {
            @Override
            public void onSuccess(List<MallBidRecord> data) {
                mvpView.setMallNodeOrderst(data);
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
    public void mallRegion(long mallRegionId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mallRegionGetRegion(mallRegionId)), new ApiCallback<MallRegionDetail>() {
            @Override
            public void onSuccess(MallRegionDetail data) {
                mvpView.setMallRegionDetail(data);
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
