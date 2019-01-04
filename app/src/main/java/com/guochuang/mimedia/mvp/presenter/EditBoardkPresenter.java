package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.EditBoardView;
import com.guochuang.mimedia.mvp.view.FeedbackView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class EditBoardkPresenter extends BasePresenter<EditBoardView> {
    public EditBoardkPresenter(EditBoardView view) {
        attachView(view);
    }
    public void fileUpload(String businessType, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().fileUpload(businessType,body)), new ApiCallback<UploadFile>() {
            @Override
            public void onSuccess(UploadFile data) {
                mvpView.setUploadFile(data);

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

    public void addRegioNotice(long regionId,String content,String picture,String urlName,String url){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addRegioNotice(regionId,content,picture,urlName,url)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setNotice(data);
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
    public void updateRegioNotice(long regionId,String content,String picture,String urlName,String url){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().updateRegioNotice(regionId,content,picture,urlName,url)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setNotice(data);
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
    public void getRegioNotice(long regionId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRegioNotice(regionId)), new ApiCallback<BoardDetail>() {
            @Override
            public void onSuccess(BoardDetail data) {
                mvpView.getNotice(data);
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
