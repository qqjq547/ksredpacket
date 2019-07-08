package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RedbagUser;
import com.guochuang.mimedia.mvp.view.RedbagJoinedView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class RedbagJoinedPresenter extends BasePresenter<RedbagJoinedView> {
    public RedbagJoinedPresenter(RedbagJoinedView view) {
        attachView(view);
    }
    public void getRedbagUserDetail(String redPacketUuid,String startIndex,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRedbagUserDetail(redPacketUuid,startIndex,pageSize)), new ApiCallback<List<RedbagUser>>() {
            @Override
            public void onSuccess(List<RedbagUser> data) {
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
