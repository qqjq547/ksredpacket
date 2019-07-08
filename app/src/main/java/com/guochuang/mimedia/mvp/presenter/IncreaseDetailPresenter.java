package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RegistUser;
import com.guochuang.mimedia.mvp.view.IncreaseDetailView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class IncreaseDetailPresenter extends BasePresenter<IncreaseDetailView> {
    public IncreaseDetailPresenter(IncreaseDetailView view) {
        attachView(view);
    }
    public void getIncreaseUser(int currentPage,int pageSize,String beginRegisterDate,String endRegisterDate){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getIncreaseUser(currentPage,pageSize,beginRegisterDate,endRegisterDate)), new ApiCallback<Page<RegistUser>>() {
            @Override
            public void onSuccess(Page<RegistUser> data) {
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
