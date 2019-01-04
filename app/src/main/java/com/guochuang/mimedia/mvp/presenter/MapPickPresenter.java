package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.GeoCode;
import com.guochuang.mimedia.mvp.view.MapPickView;
import com.guochuang.mimedia.tools.RxUtil;

import retrofit2.http.Query;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class MapPickPresenter extends BasePresenter<MapPickView> {
    public MapPickPresenter(MapPickView view) {
        attachView(view);
    }
    public void getGeocode(String latitude,String longitude,String address){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getGeocode(latitude,longitude,address)), new ApiCallback<GeoCode>() {
            @Override
            public void onSuccess(GeoCode data) {
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
