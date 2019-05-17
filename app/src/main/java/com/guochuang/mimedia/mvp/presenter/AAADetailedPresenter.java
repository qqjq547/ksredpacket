package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AAADetail;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.view.AAADetailedView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class AAADetailedPresenter extends BasePresenter<AAADetailedView> {
    public AAADetailedPresenter(AAADetailedView view) {
        attachView(view);
    }

    /**
     * 获取AAA明细列表
     * @param currentPage
     * @param pageSize
     */
    public void getAAADetailedList(String digitalCurrency,int currentPage, int pageSize,String type) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getAAADetailedList(digitalCurrency,currentPage,pageSize,type)), new ApiCallback<Page<AAADetail>>() {
            @Override
            public void onSuccess(Page<AAADetail> data) {
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
