package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.view.InfoListView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class InfoListPresenter extends BasePresenter<InfoListView> {
    public InfoListPresenter(InfoListView view) {
        attachView(view);
    }
    public void getRecommendList(int currentPage,int pageSize,int categoryId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRecommendList(currentPage,pageSize,categoryId)), new ApiCallback<Page<InfoItem>>() {
            @Override
            public void onSuccess(Page<InfoItem> data) {
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
