package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.mvp.view.RedbagDetailView;
import com.guochuang.mimedia.mvp.view.SquareDetailView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class SquareDetailPresenter extends BasePresenter<SquareDetailView> {
    public SquareDetailPresenter(SquareDetailView view) {
        attachView(view);
    }
    public void getSquareDetail(String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSquareDetail(redPacketUuid)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
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
    public void redPacketPoolOpen(String latitude,String longitude,String redPacketUuid,String password){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketPoolOpen(latitude,longitude,redPacketUuid,password)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
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
    public void redBagPraiseAdd(String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagPraiseAdd(redPacketUuid)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setPraiseAdd(data);
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
    public void redBagPraiseDelete(String redPacketUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagPraiseDelete(redPacketUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setPraiseDelete(data);
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
    public void redBagFavoriteAdd(String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagFavoriteAdd(redPacketUuid)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setFavoriteAdd(data);
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
    public void redBagFavoriteDelete(String redPacketUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagFavoriteDelete(redPacketUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setFavoriteDelete(data);
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
    public void redBagCommentList(int currentPage,int pageSize,String redPacketUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagCommentList(currentPage,pageSize,redPacketUuid)), new ApiCallback<Page<RedPacketReply>>() {
            @Override
            public void onSuccess(Page<RedPacketReply> data) {
                mvpView.setCommentList(data);
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
    public void redBagCommentAdd(String redPacketUuid,String content,long parentId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagCommentAdd(redPacketUuid,content,parentId)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setCommentAdd(data);
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
    public void redBagCommentReply(long commentId,String content) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagCommentReply(commentId,content)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setCommentReply(data);
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
    public void redBagIsFavoritedOrPraised(String redPacketUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redBagIsFavoritedOrPraised(redPacketUuid)), new ApiCallback<FavoriteAndPraise>() {
            @Override
            public void onSuccess(FavoriteAndPraise data) {
                mvpView.setIsFavoritedAndPraised(data);
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
