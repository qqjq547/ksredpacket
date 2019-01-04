package com.guochuang.mimedia.mvp.presenter;

import android.graphics.Bitmap;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.mvp.view.ImagePreviewView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

public class ImagePreviewPresneter extends BasePresenter<ImagePreviewView> {

    public ImagePreviewPresneter(ImagePreviewView view) {
        attachView(view);
    }

    public void downloadPicture(String path){
        addSubscription(RxUtil.createBmpObservable(ApiClient.getInstance().getApiStores().
                downloadPicture(path)), new ApiCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data) {
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
