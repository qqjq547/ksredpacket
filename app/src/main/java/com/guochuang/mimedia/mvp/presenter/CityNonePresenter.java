package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.mvp.view.CityNoneView;

public class CityNonePresenter extends BasePresenter<CityNoneView> {
    public CityNonePresenter(CityNoneView view) {
        attachView(view);
    }
//    public void getRecommendList(int currentPage,int pageSize,int categoryId,String title){
//        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRecommendList(currentPage,pageSize,categoryId,title)), new ApiCallback<Page<InfoItem>>() {
//            @Override
//            public void onSuccess(Page<InfoItem> data) {
//                mvpView.setRedbagDetail(data);
//
//            }
//
//            @Override
//            public void onFailure(ApiException exception) {
//                mvpView.setError(exception.getMessage());
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }
}
