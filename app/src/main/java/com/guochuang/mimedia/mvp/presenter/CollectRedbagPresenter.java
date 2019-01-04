package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CommentRedbag;
import com.guochuang.mimedia.mvp.view.CollectRedbagView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class CollectRedbagPresenter extends BasePresenter<CollectRedbagView> {
    public CollectRedbagPresenter(CollectRedbagView view) {
        attachView(view);
    }
    public void getUserRedbagCollect(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getFavoriteRedbag(currentPage,pageSize)), new ApiCallback<Page<CommentRedbag>>() {
            @Override
            public void onSuccess(Page<CommentRedbag> data) {
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
