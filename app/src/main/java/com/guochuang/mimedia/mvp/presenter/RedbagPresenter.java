package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.HomeRegion;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.model.NestHomeAd;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.view.RedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class RedbagPresenter extends BasePresenter<RedbagView> {
    public RedbagPresenter(RedbagView view) {
        attachView(view);
    }
    public void redPacketGet(String latitude,String longitude){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketGet(latitude,longitude)), new ApiCallback<List<Redbag>>() {
            @Override
            public void onSuccess(List<Redbag> data) {
                mvpView.setSystemRedbag(data);

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
    public void getLocationRedabg(String latitude,String longitude){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getLocationRedabg(latitude,longitude)), new ApiCallback<List<Redbag>>() {
            @Override
            public void onSuccess(List<Redbag> data) {
                mvpView.setLocationRedbag(data);

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
    public void redPacketOpen(String latitude,String longitude,String systemRedPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketOpen(latitude,longitude,systemRedPacketUuid)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
                mvpView.setRedbagDetail(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());
                if (exception.getStatusCode()==Constant.HTTP_STATUS_REDBAD_INVALID){
                  mvpView.setRedbagInvalid();
                }

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
                mvpView.setRedbagDetail(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());
                if (exception.getStatusCode()==Constant.HTTP_STATUS_REDBAD_INVALID){
                    mvpView.setRedbagInvalid();
                }

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void redPacketPoolOpen(String latitude,String longitude,String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketPoolOpenSurvey(latitude,longitude,redPacketUuid)), new ApiCallback<RedbagDetail>() {
            @Override
            public void onSuccess(RedbagDetail data) {
                mvpView.setRedbagDetail(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());
                if (exception.getStatusCode()==Constant.HTTP_STATUS_REDBAD_INVALID){
                    mvpView.setRedbagInvalid();
                }

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void getWalletCoinAndMoney() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getMyKsb()), new ApiCallback<MyKsb>() {
            @Override
            public void onSuccess(MyKsb data) {
                mvpView.setCoinAndMoney(data);

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
    public void getScrollBar() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getScrollBar()), new ApiCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                mvpView.setScrollbar(data);

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
    public void getUserRole() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getUserRole()), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setUserRole(data);

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
    public void getHomeRegion(String latitude,String longitude) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getHomeRegion(latitude,longitude)), new ApiCallback<HomeRegion>() {
            @Override
            public void onSuccess(HomeRegion data) {
                mvpView.setHomeRegion(data);

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
    public void getKilometre() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getKilometre()), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.setKilometre(data);

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
    public void getHomeAd(String latitude,String longitude) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getHomeAd(latitude,longitude)), new ApiCallback<List<NestHomeAd>>() {
            @Override
            public void onSuccess(List<NestHomeAd> data) {
                mvpView.setHomeAd(data);
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
    public void userStatistics(String latitude,String longitude) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userStatistics(latitude,longitude)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {

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
    public void getIsQualified(String latitude,String longitude) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getIsQualified(latitude,longitude)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setIsQualified(data);

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
