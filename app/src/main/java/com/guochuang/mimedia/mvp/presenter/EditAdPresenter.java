package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.EditAdView;
import com.guochuang.mimedia.mvp.view.EditRedbagView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class EditAdPresenter extends BasePresenter<EditAdView> {
    public EditAdPresenter(EditAdView view) {
        attachView(view);
    }

    public void fileUpload(String businessType, File file, final boolean isIcon) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().fileUpload(businessType, body)), new ApiCallback<UploadFile>() {
            @Override
            public void onSuccess(UploadFile data) {
                if (isIcon){
                    mvpView.setIcon(data);
                }else {
                    mvpView.setUploadFile(data);
                }
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setUploadfail(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getTemplate(String redPacketType){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getTemplate(redPacketType)), new ApiCallback<List<RedbagTemp>>() {
            @Override
            public void onSuccess(List<RedbagTemp> data) {
                mvpView.setTempData(data);

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
