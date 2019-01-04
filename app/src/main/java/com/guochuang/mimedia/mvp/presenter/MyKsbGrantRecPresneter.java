package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyKsbGrantRec;
import com.guochuang.mimedia.mvp.view.MyKsbGrantRecView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyKsbGrantRecPresneter extends BasePresenter<MyKsbGrantRecView> {
    public MyKsbGrantRecPresneter(MyKsbGrantRecView view) {
        attachView(view);
    }

    public void userGetTransferRec(
            int currentPage,
            int pageSize
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userTransferGetByDonor(currentPage, pageSize)), new ApiCallback<Page<MyKsbGrantRec>>() {
            @Override
            public void onSuccess(Page<MyKsbGrantRec> data) {
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
