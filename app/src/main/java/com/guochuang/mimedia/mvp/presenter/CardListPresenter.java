package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CardList;
import com.guochuang.mimedia.mvp.view.CardListView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class CardListPresenter extends BasePresenter<CardListView> {
    public CardListPresenter(CardListView view) {
        attachView(view);
    }

    public void userBankCardList() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBandCardList()), new ApiCallback<List<CardList>>() {
            @Override
            public void onSuccess(List<CardList> data) {
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
