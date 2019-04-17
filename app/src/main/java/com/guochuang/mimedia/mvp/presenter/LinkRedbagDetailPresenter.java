package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.mvp.view.LinkRedbagDetailView;
import com.guochuang.mimedia.mvp.view.RedbagDetailView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class LinkRedbagDetailPresenter extends BasePresenter<LinkRedbagDetailView> {
    public LinkRedbagDetailPresenter(LinkRedbagDetailView view) {
        attachView(view);
    }

    public void getRedPacketInfo(String redPacketUuid,String redPacketType,String startIndex){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRedPacketInfo(redPacketUuid,redPacketType,startIndex)), new ApiCallback<RedbagInfo>() {
            @Override
            public void onSuccess(RedbagInfo data) {
                mvpView.setInfo(data);

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
