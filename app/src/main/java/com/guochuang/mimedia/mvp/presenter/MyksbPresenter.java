package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.KsbTrend;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.view.MyksbView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class MyksbPresenter extends BasePresenter<MyksbView> {
    public MyksbPresenter(MyksbView view) {
        attachView(view);
    }
    public void getMyKsb(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyKsb()), new ApiCallback<MyKsb>() {
            @Override
            public void onSuccess(MyKsb data) {
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
    public void getKsbTrend(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getKsbTrend()), new ApiCallback<List<KsbTrend>>() {
            @Override
            public void onSuccess(List<KsbTrend> data) {
                mvpView.setKsbTrend(data);
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
    public void getMyAAA(String digitalCurrency){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyAAA(digitalCurrency)), new ApiCallback<MyAAA>() {
            @Override
            public void onSuccess(MyAAA data) {
                mvpView.setMyAaa(data);
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
