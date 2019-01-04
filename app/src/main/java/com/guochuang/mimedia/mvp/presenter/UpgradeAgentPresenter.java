package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.InviterUser;
import com.guochuang.mimedia.mvp.view.UpgradeAgentView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class UpgradeAgentPresenter extends BasePresenter<UpgradeAgentView> {
    public UpgradeAgentPresenter(UpgradeAgentView view) {
        attachView(view);
    }
    public void getAgentMoney(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getAgentMoney()), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
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
    public void getMyInviter(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyInviter()), new ApiCallback<InviterUser>() {
            @Override
            public void onSuccess(InviterUser data) {
                mvpView.setInviter(data);

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
