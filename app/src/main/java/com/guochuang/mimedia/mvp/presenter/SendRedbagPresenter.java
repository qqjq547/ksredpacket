package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.mvp.view.SendRedbagView;
import com.guochuang.mimedia.tools.RxUtil;

public class SendRedbagPresenter extends BasePresenter<SendRedbagView> {
    public SendRedbagPresenter(SendRedbagView view) {
        attachView(view);
    }
    public void getSendRedbagList(int currentPage,int pageSize) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSendRedbagList(currentPage,pageSize)), new ApiCallback<Page<RedbagRecord>>() {
            @Override
            public void onSuccess(Page<RedbagRecord> data) {
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
