package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.SetUpUser;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.SettingView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class SettingPresenter extends BasePresenter<SettingView> {
    public SettingPresenter(SettingView view) {
        attachView(view);
    }
    public void fileUpload(String businessType, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().fileUpload(businessType,body)), new ApiCallback<UploadFile>() {
            @Override
            public void onSuccess(UploadFile data) {
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
    public void getSetupInfo(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSetupInfo()), new ApiCallback<SetUpUser>() {
            @Override
            public void onSuccess(SetUpUser data) {
                mvpView.getSetupInfo(data);
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
    public void setAvatar(String nickName,String avatar){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().setAvatar(nickName,avatar)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setUpdateAvatar(data);
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

    public void userAccoutLogout(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAccoutLogout()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.getLogout(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.getLogoutError(exception.getMessage());
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
