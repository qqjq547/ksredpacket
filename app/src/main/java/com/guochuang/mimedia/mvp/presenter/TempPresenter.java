package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.view.TempView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class TempPresenter extends BasePresenter<TempView> {
    public TempPresenter(TempView view) {
        attachView(view);
    }
    public void getTemplate(String redPacketType) {
        if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)||redPacketType.equals(Constant.RED_PACKET_TYPE_SURVEY)){
            addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSurveytTemplate(redPacketType)), new ApiCallback<List<RedbagTemp>>() {
                @Override
                public void onSuccess(List<RedbagTemp> data) {
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
        }else {
            addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getTemplate(redPacketType)), new ApiCallback<List<RedbagTemp>>() {
                @Override
                public void onSuccess(List<RedbagTemp> data) {
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

    public void deleteTemplate(String redPacketType,long templateId){
        if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)||redPacketType.equals(Constant.RED_PACKET_TYPE_SURVEY)) {
            addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().deleteSurveyTemplate(templateId)), new ApiCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    mvpView.setDeleteTemp(data);

                }

                @Override
                public void onFailure(ApiException exception) {
                    mvpView.setError(exception.getMessage());

                }

                @Override
                public void onFinish() {

                }
            });
        }else {
            addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().deleteTemplate(templateId)), new ApiCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    mvpView.setDeleteTemp(data);

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

}
