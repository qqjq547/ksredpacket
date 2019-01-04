package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RegionCoreHome;
import com.guochuang.mimedia.mvp.view.OperationCenterView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class OperationCenterPresenter extends BasePresenter<OperationCenterView> {
    public OperationCenterPresenter(OperationCenterView view) {
        attachView(view);
    }
    public void getRegionCoreHome(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRegionCoreHome()), new ApiCallback<RegionCoreHome>() {
            @Override
            public void onSuccess(RegionCoreHome data) {
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
