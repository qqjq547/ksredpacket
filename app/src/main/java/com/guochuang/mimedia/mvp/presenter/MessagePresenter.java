package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Message;
import com.guochuang.mimedia.mvp.view.MessageView;
import com.guochuang.mimedia.tools.RxUtil;


/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class MessagePresenter extends BasePresenter<MessageView> {
    public MessagePresenter(MessageView view) {
        attachView(view);
    }
    public void getMessageList(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMessageList(currentPage,pageSize)), new ApiCallback<Page<Message>>() {
            @Override
            public void onSuccess(Page<Message> data) {
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
}
