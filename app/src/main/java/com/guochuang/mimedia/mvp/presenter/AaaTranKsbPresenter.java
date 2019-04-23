package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.mvp.view.AaaTranKsbView;
import com.guochuang.mimedia.mvp.view.KsbTranAaaView;

public class AaaTranKsbPresenter extends BasePresenter<AaaTranKsbView> {

    public AaaTranKsbPresenter(AaaTranKsbView view) {
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
