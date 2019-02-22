package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.view.MyBidView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyBidPresenter extends BasePresenter<MyBidView> {
    public MyBidPresenter(MyBidView view) {
        attachView(view);
    }

    public void getMyAuctionList(int currentPage, int pageSize) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                nestMyAuctionList(currentPage,pageSize)), new ApiCallback<Page<NestAuctionRecord>>() {
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
