package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.MyQC;
import com.guochuang.mimedia.mvp.view.MyQCDetailsView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

import retrofit2.http.Query;

public class MyQCDetailsPresenter extends BasePresenter<MyQCDetailsView> {
    public MyQCDetailsPresenter(MyQCDetailsView view) {
        attachView(view);

    }

    public void getQCDetail(String type, String startIndex, int pageSize) {

//        @Query("type") String type,
//        @Query("coinType") String coinType,
//        @Query("startIndex") String startIndex,
//        @Query("pageSize") int pageSize

        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getCoinRecord(type,Constant.QC_DETAIL,startIndex,pageSize)), new ApiCallback<List<KsbRecord>>() {
            @Override
            public void onSuccess(List<KsbRecord> data) {
                mvpView.setQCDetail(data);
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

    public void getSubject(String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDictionaryType(type)), new ApiCallback<List<DictionaryType>>() {
            @Override
            public void onSuccess(List<DictionaryType> data) {
                mvpView.setSubject(data);

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
