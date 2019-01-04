package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyKsbTransRec;
import com.guochuang.mimedia.mvp.view.MyKsbTransRecView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyKsbTransRecPresenter extends BasePresenter<MyKsbTransRecView> {
    public MyKsbTransRecPresenter(MyKsbTransRecView view) {
        attachView(view);
    }

    public void userWithdrawalsGet(
            int currentPage,
            int pageSize
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userWithdrawalsGet(currentPage, pageSize)), new ApiCallback<Page<MyKsbTransRec>>() {
            @Override
            public void onSuccess(Page<MyKsbTransRec> data) {
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
