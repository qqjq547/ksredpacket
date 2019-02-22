package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.model.NestHistory;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.mvp.view.BidHistoryView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

public class BidHistoryPresneter extends BasePresenter<BidHistoryView> {

    public BidHistoryPresneter(BidHistoryView view) {
        attachView(view);
    }

    public void getNestAuctionHistory(long nestTimeId,int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                nestAuctionList(nestTimeId,currentPage,pageSize)), new ApiCallback<Page<NestAuctionRecord>>() {
            @Override
            public void onSuccess(Page<NestAuctionRecord> data) {
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
