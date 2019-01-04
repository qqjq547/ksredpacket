package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.view.InfoView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class InfoPresenter extends BasePresenter<InfoView> {
    public InfoPresenter(InfoView view) {
        attachView(view);
    }
    public void categoryGet(String systemCode,String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().categoryGet(systemCode,type)), new ApiCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> data) {
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
