package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CommentInfo;
import com.guochuang.mimedia.mvp.view.CommentInfoView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class CommentInfoPresenter extends BasePresenter<CommentInfoView> {
    public CommentInfoPresenter(CommentInfoView view) {
        attachView(view);
    }
    public void getUserInfoComment(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getUserInfoComment(currentPage,pageSize)), new ApiCallback<Page<CommentInfo>>() {
            @Override
            public void onSuccess(Page<CommentInfo> data) {
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
    public void infoCommentDelete(long commentId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().infoCommentDelete(commentId)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setCommentDelete(data);
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
