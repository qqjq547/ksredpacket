package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class IdentifyPresenter extends BasePresenter<IdentifyView> {
    public IdentifyPresenter(IdentifyView view) {
        attachView(view);
    }

    public void userNameAuthAuth(
            String realName,
            String idCard,
            String idCardPicture,
            String vendorResponse
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userNameAuthAuth(realName, idCard, idCardPicture, vendorResponse)), new ApiCallback<String>() {
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
                mvpView.setUploadData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setUploadError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getRegisterImageVerify(String mobile) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userPhoneCaptcha(mobile)), new ApiCallback<Captcha>() {
            @Override
            public void onSuccess(Captcha data) {
                mvpView.setVerifyData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 实名认证
     * @param realName
     * @param idCard
     * @param cardNumber
     * @param uuid
     * @param captchastr
     */
    public void realName(String realName, String idCard, String cardNumber, String uuid, String captchastr) {

        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                realName(realName,idCard,cardNumber,uuid,captchastr)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });


    }

//    public void ocrIdCard(
//            File file
//    ) {
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        addSubscription(RxUtil.createBaseObservable(ApiClient.getInstance().newInstance(ApiClient.OCR_ID_CARD).
//                ocrIdCard(KeyUtil.API_KEY, KeyUtil.API_SECRET, body)), new ApiCallback<MegviiSerach>() {
//            @Override
//            public void onSuccess(MegviiSerach data) {
//                mvpView.setOCRData(data);
//
//            }
//
//            @Override
//            public void onFailure(ApiException exception) {
//                mvpView.setOCRError(exception.getMessage());
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//    }
}
