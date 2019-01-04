package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.ShareBenefit;
import com.guochuang.mimedia.mvp.view.ShareBenefitDetailView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class ShareBenefitDetailPresenter extends BasePresenter<ShareBenefitDetailView> {
    public ShareBenefitDetailPresenter(ShareBenefitDetailView view) {
        attachView(view);
    }
    public void getKsbBonusDetailsList(String startIndex,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getKsbBonusDetailsList(startIndex,pageSize)), new ApiCallback<List<ShareBenefit>>() {
            @Override
            public void onSuccess(List<ShareBenefit> data) {
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
