package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.model.SnatchShow;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.EditBoardView;
import com.guochuang.mimedia.mvp.view.ShowListView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class ShowListPresenter extends BasePresenter<ShowListView> {
    public ShowListPresenter(ShowListView view) {
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
    public void addSnatchShow(long snatchId,String content,List<String> imgs){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().addSnatchShow(snatchId,content,imgs)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setShowList(data);
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
    public void getSnatchShow(long snatchShowId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().querySnatchShow(snatchShowId)), new ApiCallback<SnatchShow>() {
            @Override
            public void onSuccess(SnatchShow data) {
                mvpView.getShowList(data);
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
