package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.view.FansView;
import com.guochuang.mimedia.tools.RxUtil;

public class FansPresenter extends BasePresenter<FansView> {
    public FansPresenter(FansView view) {
        attachView(view);
    }
    public void getFans(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDirectFans(currentPage,pageSize)), new ApiCallback<Page<RecommedUser>>() {
            @Override
            public void onSuccess(Page<RecommedUser> data) {
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
