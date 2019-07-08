package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CurrentRegion;
import com.guochuang.mimedia.mvp.view.CityView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class CityPresenter extends BasePresenter<CityView> {
    public CityPresenter(CityView view) {
        attachView(view);
    }
    public void getCurrentRegion(String latitude,String longitude){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getCurrentRegion(latitude,longitude)), new ApiCallback<CurrentRegion>() {
            @Override
            public void onSuccess(CurrentRegion data) {
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
