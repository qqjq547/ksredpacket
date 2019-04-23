package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.view.AdBidView;
import com.guochuang.mimedia.mvp.view.KsbTranAaaView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class KsbTranAaaPresenter extends BasePresenter<KsbTranAaaView> {

    public KsbTranAaaPresenter(KsbTranAaaView view) {
        attachView(view);
    }

//    public void getNestSpot(String latitude,String longitude){
//        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
//                getNestSpot(latitude,longitude)), new ApiCallback<List<NestLocation>>() {
//            @Override
//            public void onSuccess(List<NestLocation> data) {
//                mvpView.setData(data);
//            }
//
//            @Override
//            public void onFailure(ApiException exception) {
//                mvpView.setError(exception.getMessage());
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }
}
