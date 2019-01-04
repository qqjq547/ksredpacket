package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.Reply;

import com.guochuang.mimedia.mvp.view.InfoDetailView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class InfoDetailPresenter extends BasePresenter<InfoDetailView> {
    public InfoDetailPresenter(InfoDetailView view) {
        attachView(view);
    }
    public void beginRead(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().begin_read(articleUuid)), new ApiCallback<InfoDetail>() {
            @Override
            public void onSuccess(InfoDetail data) {
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
    public void endRead(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().end_read(articleUuid)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setReadEnd(data);

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
    public void commentGet(int currentPage,int pageSize,String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().commentGet(currentPage,pageSize,articleUuid)), new ApiCallback<Page<Reply>>() {
            @Override
            public void onSuccess(Page<Reply> data) {
                mvpView.getComment(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setPageError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void commentAdd(String articleUuid,String content,long parentId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().commentAdd(articleUuid,content,parentId)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.addComment(data);

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
    public void favoriteAdd(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().favoriteAdd(articleUuid)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.addFavorite(data);

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
    public void favoriteDetele(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().favoriteDelete(articleUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.deleteFavorite(data);

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
    public void praiseAdd(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().praiseAdd(articleUuid)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.addPraise(data);

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
    public void praiseDelete(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().praiseDelete(articleUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.deletePraise(data);

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
    public void reportAdd(String articleUuid,String content,String types){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().reportAdd(articleUuid,content,types)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.addReport(data);

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
    public void dictionaryGet(String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDictionaryType(type)), new ApiCallback<List<DictionaryType>>() {
            @Override
            public void onSuccess(List<DictionaryType> data) {
                mvpView.getReportItem(data);

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
    public void readingGenerate(String articleUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().readingGenerate(articleUuid)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.readingGenerate(data);

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
    public void shareAdd(String articleUuid,String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().shareAdd(articleUuid,type)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.shareAdd(data);

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
