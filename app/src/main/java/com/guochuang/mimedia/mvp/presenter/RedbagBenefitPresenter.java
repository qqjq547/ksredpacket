package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.guochuang.mimedia.mvp.model.RedbagBenefit;
import com.guochuang.mimedia.mvp.view.RedbagBenefitView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class RedbagBenefitPresenter extends BasePresenter<RedbagBenefitView> {
    public RedbagBenefitPresenter(RedbagBenefitView view) {
        attachView(view);
    }
    public void getBidPrice(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getBenefitType()), new ApiCallback<List<BenefitType>>() {
            @Override
            public void onSuccess(List<BenefitType> data) {
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

    public void getRedbagBenefit(int currentPage,int pageSize,String cityName,String districtName){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRedbagBenefit(currentPage,pageSize,cityName,districtName)), new ApiCallback<Page<RedbagBenefit>>() {
            @Override
            public void onSuccess(Page<RedbagBenefit> data) {
                mvpView.setPageData(data);
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
