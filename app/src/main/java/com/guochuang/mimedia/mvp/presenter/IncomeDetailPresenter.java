package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.IncomeDetail;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.view.IncomeDetailView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class IncomeDetailPresenter extends BasePresenter<IncomeDetailView> {
    public IncomeDetailPresenter(IncomeDetailView view) {
        attachView(view);
    }
    public void getIncreaseUser(int currentPage,int pageSize,long statisticsId,String parentType,String sonType,String beginTime,String endTime){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getIncomeDetail(currentPage,pageSize,statisticsId,parentType,sonType,beginTime,endTime)), new ApiCallback<Page<IncomeDetail>>() {
            @Override
            public void onSuccess(Page<IncomeDetail> data) {
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
    public void getIncomeStatisticsType(String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDictionaryType(type)), new ApiCallback<List<DictionaryType>>() {
            @Override
            public void onSuccess(List<DictionaryType> data) {
                mvpView.setType(data);
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
