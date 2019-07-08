package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.UserStatistics;
import com.guochuang.mimedia.mvp.view.UserStatisticsView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class UserStatisticsPresenter extends BasePresenter<UserStatisticsView> {
    public UserStatisticsPresenter(UserStatisticsView view) {
        attachView(view);
    }
    public void getUserStatistics(String beginRegisterDate,String endRegisterDate){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getUserStatistics(beginRegisterDate,endRegisterDate)), new ApiCallback<UserStatistics>() {
            @Override
            public void onSuccess(UserStatistics data) {
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
