package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RainRecord;
import com.guochuang.mimedia.mvp.view.WelfareView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class WelfarePresenter extends BasePresenter<WelfareView> {
    public WelfarePresenter(WelfareView view) {
        attachView(view);
    }
    public void getRainRecordList(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().redPacketRainGetList(currentPage,pageSize)), new ApiCallback<Page<RainRecord>>() {
            @Override
            public void onSuccess(Page<RainRecord> data) {
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
