package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.Square;
import com.guochuang.mimedia.mvp.view.BidPriceView;
import com.guochuang.mimedia.mvp.view.SquareView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class SquarePresenter extends BasePresenter<SquareView> {
    public SquarePresenter(SquareView view) {
        attachView(view);
    }
    public void getSquareList(int currentPage,int pageSize,String latitude,String longitude){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSquareList(currentPage,pageSize,latitude,longitude)), new ApiCallback<Page<Square>>() {
            @Override
            public void onSuccess(Page<Square> data) {
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
