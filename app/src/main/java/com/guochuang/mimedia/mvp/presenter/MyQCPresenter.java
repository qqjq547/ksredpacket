package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyQC;
import com.guochuang.mimedia.mvp.view.MyQCView;
import com.guochuang.mimedia.tools.RxUtil;


/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class MyQCPresenter extends BasePresenter<MyQCView> {
    public MyQCPresenter(MyQCView view) {
        attachView(view);
    }
    public void getMyQC(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyQC()), new ApiCallback<MyQC>() {
            @Override
            public void onSuccess(MyQC data) {
                mvpView.setMyQC(data);
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
