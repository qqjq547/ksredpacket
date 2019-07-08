package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.view.BidHistoryView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class BidHistoryPresenter extends BasePresenter<BidHistoryView> {

    public BidHistoryPresenter(BidHistoryView view) {
        attachView(view);
    }

    public void getNestAuctionHistory(long nestLocationId,String startDate){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                nestAuctionList(nestLocationId,startDate)), new ApiCallback<List<NestAuctionRecord>>() {
            @Override
            public void onSuccess(List<NestAuctionRecord> data) {
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
