package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.RedbagRainView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class RedbagRainPresenter extends BasePresenter<RedbagRainView> {
    public RedbagRainPresenter(RedbagRainView view) {
        attachView(view);
    }
    public void getRainMsg(String redPacketRainUuid,int quantity){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketRainDraw(redPacketRainUuid,quantity)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
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
