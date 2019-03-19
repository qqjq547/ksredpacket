package com.guochuang.mimedia.mvp.presenter;

import android.util.Log;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.EditRedbagView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class EditRedbagPresenter extends BasePresenter<EditRedbagView> {
    public EditRedbagPresenter(EditRedbagView view) {
        attachView(view);
    }

    public void addRandomRedbag(String userLatitude, String userLongitude, String redPacketLatitude, String redPacketLongitude, String content, String picture, int areaType, int kilometer, Double money, int quantity
            , String urlName, String url, String wechat, String microblog, int isPublicPassword, int isSaveTemplate, int payType, String channelCode, String safetyCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addRandomRedbag(
                userLatitude,
                userLongitude,
                redPacketLatitude,
                redPacketLongitude,
                content,
                picture,
                areaType,
                kilometer,
                money,
                quantity,
                urlName,
                url,
                wechat,
                microblog,
                isPublicPassword,
                isSaveTemplate,
                payType,
                channelCode,
                safetyCode)), new ApiCallback<String>() {
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

    public void addPasswordRedbag(String userLatitude, String userLongitude, String redPacketLatitude, String redPacketLongitude, String content, String picture, int areaType, int kilometer, String password, Double money, int quantity
            , String urlName, String url, String wechat, String microblog, int isPublicPassword, int isSaveTemplate, int payType, String channelCode, String safetyCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addPasswordRedbag(
                userLatitude,
                userLongitude,
                redPacketLatitude,
                redPacketLongitude,
                content,
                picture,
                areaType,
                kilometer,
                password,
                money,
                quantity,
                urlName,
                url,
                wechat,
                microblog,
                isPublicPassword,
                isSaveTemplate,
                payType,
                channelCode,
                safetyCode)), new ApiCallback<String>() {
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

    public void addLuckyRedbag(String userLatitude, String userLongitude, String redPacketLatitude, String redPacketLongitude, String content, String picture, int areaType, int kilometer, Double money, int quantity
            , String urlName, String url, String wechat, String microblog, int isPublicPassword, int isSaveTemplate, int payType, String channelCode, String safetyCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addLuckyRedbag(
                userLatitude,
                userLongitude,
                redPacketLatitude,
                redPacketLongitude,
                content,
                picture,
                areaType,
                kilometer,
                money,
                quantity,
                urlName,
                url,
                wechat,
                microblog,
                isPublicPassword,
                isSaveTemplate,
                payType,
                channelCode,
                safetyCode)), new ApiCallback<String>() {
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

    public void fileUpload(String businessType, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().fileUpload(businessType, body)), new ApiCallback<UploadFile>() {
            @Override
            public void onSuccess(UploadFile data) {
                mvpView.setUploadFile(data);

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


    public void videoFileUpload(String businessType, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().fileUpload(businessType, body)), new ApiCallback<UploadFile>() {
            @Override
            public void onSuccess(UploadFile data) {
                mvpView.uploadVideoSuccess(data);
//                mvpView.setUploadFile(data);
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


    public void getTemplate(String redPacketType) {
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

    public void getLuckyConfig() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getLuckyConfig()), new ApiCallback<LuckyConfig>() {
            @Override
            public void onSuccess(LuckyConfig data) {
                mvpView.setLuckyConfig(data);

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


    public void addVideoReabag(String latitude, String longitude, String redbagLatitude, String redbagLongitude, String content, String picture, int areaType, int kilometer, double money, int quantity, String urlName, String url, String wechat, String microblog, int isPublicPassword, int isSaveTemplate, int payType, String channelCode, String safetyCode,String problemstr,String videoFrame) {

        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addVideoRedbag(
                latitude,
                longitude,
                redbagLatitude,
                redbagLongitude,
                content,
                picture,
                areaType,
                kilometer,
                money,
                quantity,
                urlName,
                url,
                wechat,
                microblog,
                isPublicPassword,
                isSaveTemplate,
                payType,
                channelCode,
                safetyCode,0,problemstr,videoFrame)), new ApiCallback<String>() {
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

    public void addSurveyReabag(String latitude, String longitude, String redbagLatitude, String redbagLongitude, String content, String picture, int areaType, int kilometer, double money, int quantity, String urlName, String url, String wechat, String microblog, int isPublicPassword, int isSaveTemplate, int payType, String channelCode, String safetyCode, String problemstr) {

        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addSurveyRedbag(
                latitude,
                longitude,
                redbagLatitude,
                redbagLongitude,
                content,
                picture,
                areaType,
                kilometer,
                money,
                quantity,
                urlName,
                url,
                wechat,
                microblog,
                isPublicPassword,
                isSaveTemplate,
                payType,
                channelCode,
                safetyCode,1,problemstr)), new ApiCallback<String>() {
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
