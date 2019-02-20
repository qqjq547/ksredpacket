package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.Reply;
import com.guochuang.mimedia.mvp.view.BeeNestView;
import com.guochuang.mimedia.mvp.view.InfoDetailView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class BeeNestPresenter extends BasePresenter<BeeNestView> {
    public BeeNestPresenter(BeeNestView view) {
        attachView(view);
    }
    public void getNestAd(long nestInfoId,String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getNestAd(nestInfoId,type)), new ApiCallback<NestAd>() {
            @Override
            public void onSuccess(NestAd data) {
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

    public void favoriteAdd(long nestInfoId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().nestAddFavorite(nestInfoId)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
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
    public void favoriteDetele(long nestInfoId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().nestDelFavorite(nestInfoId)), new ApiCallback<Boolean>() {
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
    public void reportAdd(long nestInfoId,String content,String types){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().nestReport(nestInfoId,content,types)), new ApiCallback<Integer>() {
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
}
