package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DrawStatistics;
import com.guochuang.mimedia.mvp.model.RedbagReceived;
import com.guochuang.mimedia.mvp.view.ReceiveRedbagView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class ReceivedRedbagPresenter extends BasePresenter<ReceiveRedbagView> {
    public ReceivedRedbagPresenter(ReceiveRedbagView view) {
        attachView(view);
    }
    public void getKsbDetailsList(String type,String startIndex,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getReceiveRedbag(type,startIndex,pageSize)), new ApiCallback<List<RedbagReceived>>() {
            @Override
            public void onSuccess(List<RedbagReceived> data) {
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
    public void getDrawStatistics(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDrawStatistics()), new ApiCallback<DrawStatistics>() {
            @Override
            public void onSuccess(DrawStatistics data) {
                mvpView.setStatistics(data);
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
