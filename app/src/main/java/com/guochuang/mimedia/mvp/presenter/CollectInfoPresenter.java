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

public class CollectInfoPresenter extends BasePresenter<InfoListView> {
    public CollectInfoPresenter(InfoListView view) {
        attachView(view);
    }
    public void getInfoFavoriteList(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getUserInfofavorite(currentPage,pageSize)), new ApiCallback<Page<InfoItem>>() {
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
