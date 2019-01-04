package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Area;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.mvp.view.CityBidHallView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class CityBidHallPresenter extends BasePresenter<CityBidHallView> {
    public CityBidHallPresenter(CityBidHallView view) {
        attachView(view);
    }

    public void getRegion() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRegion()), new ApiCallback<List<Area>>() {
            @Override
            public void onSuccess(List<Area> data) {
                mvpView.setArea(data);

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

    public void userBidCityHall(
            int currentPage,
            int pageSize,
            String latitude,
            String longitude,
            String provinceName,
            String cityName,
            String districtName,
            Integer hottest,
            Integer dereliction,
            Integer price
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBidCityHall(currentPage, pageSize, latitude, longitude, provinceName, cityName, districtName, hottest, dereliction, price)), new ApiCallback<Page<CityBidHall>>() {
            @Override
            public void onSuccess(Page<CityBidHall> data) {
                mvpView.setCityData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setCityError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
