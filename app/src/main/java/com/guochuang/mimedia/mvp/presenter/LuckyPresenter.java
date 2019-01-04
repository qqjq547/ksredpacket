package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.LuckyResult;
import com.guochuang.mimedia.mvp.view.BidPriceView;
import com.guochuang.mimedia.mvp.view.LuckyView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class LuckyPresenter extends BasePresenter<LuckyView> {
    public LuckyPresenter(LuckyView view) {
        attachView(view);
    }
    public void openLuckyRedPacket(String redPacketUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().openLuckyRedPacket(redPacketUuid)), new ApiCallback<LuckyResult>() {
            @Override
            public void onSuccess(LuckyResult data) {
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
