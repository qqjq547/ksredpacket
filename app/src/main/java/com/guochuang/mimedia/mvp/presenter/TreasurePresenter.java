package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.view.MyTreasureView;
import com.guochuang.mimedia.mvp.view.TreasureView;
import com.guochuang.mimedia.tools.RxUtil;

public class TreasurePresenter extends BasePresenter<TreasureView> {
    public TreasurePresenter(TreasureView view) {
        attachView(view);
    }

    public void getRecordList(int currentPage,int pageSize,boolean isWin) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getSnatchRecordlist(currentPage,pageSize,isWin)), new ApiCallback<Page<Snatch>>() {
            @Override
            public void onSuccess(Page<Snatch> data) {
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
