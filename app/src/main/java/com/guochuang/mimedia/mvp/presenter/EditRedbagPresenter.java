package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AddReqDtoListBean;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.EditRedbagConfig;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.QuestionOpt;
import com.guochuang.mimedia.mvp.model.RedBagConfig;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.view.EditRedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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


    public void getTemplate(String redPacketType) {
        if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO) || redPacketType.equals(Constant.RED_PACKET_TYPE_SURVEY)) {
            addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getSurveytTemplate(redPacketType)), new ApiCallback<List<RedbagTemp>>() {
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
        } else {
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

    public void getEditRedbagConfig() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getConfig()), new ApiCallback<EditRedbagConfig>() {
            @Override
            public void onSuccess(EditRedbagConfig data) {
                mvpView.setConfig(data);

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


    public void addVideoReabag(String latitude, String longitude, String redbagLatitude, String redbagLongitude,
                               String content, int areaType, int kilometer, double money,
                               int quantity, String urlName, String url, String wechat, String microblog,
                               int isPublicPassword, int isSaveTemplate, int payType, String channelCode,
                               String safetyCode, List<AddReqDtoListBean> addReqDtoListBeanArr,String videoUrl,String coverUrl) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        QuestionOpt questionOpt=new QuestionOpt();
        questionOpt.setAddReqDtoList(addReqDtoListBeanArr);
        questionOpt.setChannelCode(channelCode);
        questionOpt.setAreaType(areaType);
        questionOpt.setContent(content);
        questionOpt.setIsPublicPassword(isPublicPassword);
        questionOpt.setIsSaveTemplate(isSaveTemplate);
        questionOpt.setKilometer(kilometer);
        questionOpt.setMicroblog(microblog);
        questionOpt.setMoney(money);
        questionOpt.setPayType(payType);
        questionOpt.setQuantity(quantity);
        questionOpt.setRedPacketLatitude(redbagLatitude);
        questionOpt.setRedPacketLongitude(redbagLongitude);
        questionOpt.setRedPacketType(Constant.RED_PACKET_TYPE_VIDEO);
        questionOpt.setSafetyCode(safetyCode);
        questionOpt.setSurveyType(0);
        questionOpt.setUrl(url);
        questionOpt.setUrlName(urlName);
        questionOpt.setWechat(wechat);
        questionOpt.setUserLatitude(latitude);
        questionOpt.setUserLongitude(longitude);
        questionOpt.setVideoUrl(videoUrl);
        questionOpt.setCoverUrl(coverUrl);


        RequestBody body = RequestBody.create(JSON, GsonUtil.GsonString(questionOpt));
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                addVideoRedbag(body)), new ApiCallback<String>() {
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

    public void addSurveyReabag(String latitude, String longitude, String redbagLatitude, String redbagLongitude,
                                String content, String picture, int areaType, int kilometer, double money,
                                int quantity, String urlName, String url, String wechat, String microblog,
                                int isPublicPassword, int isSaveTemplate, int payType, String channelCode,
                                String safetyCode, List<AddReqDtoListBean> addReqDtoListBeanArr) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        QuestionOpt questionOpt=new QuestionOpt();
        questionOpt.setAddReqDtoList(addReqDtoListBeanArr);
        questionOpt.setChannelCode(channelCode);
        questionOpt.setAreaType(areaType);
        questionOpt.setContent(content);
        questionOpt.setIsPublicPassword(isPublicPassword);
        questionOpt.setIsSaveTemplate(isSaveTemplate);
        questionOpt.setKilometer(kilometer);
        questionOpt.setMicroblog(microblog);
        questionOpt.setMoney(money);
        questionOpt.setPayType(payType);
        questionOpt.setPicture(picture);
        questionOpt.setQuantity(quantity);
        questionOpt.setRedPacketLatitude(redbagLatitude);
        questionOpt.setRedPacketLongitude(redbagLongitude);
        questionOpt.setRedPacketType(Constant.RED_PACKET_TYPE_SURVEY);
        questionOpt.setSafetyCode(safetyCode);
        questionOpt.setSurveyType(1);
        questionOpt.setUrl(url);
        questionOpt.setUrlName(urlName);
        questionOpt.setUserLatitude(latitude);
        questionOpt.setUserLongitude(longitude);
        questionOpt.setWechat(wechat);

        RequestBody body = RequestBody.create(JSON, GsonUtil.GsonString(questionOpt));
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                addSurveyRedbag(body)), new ApiCallback<String>() {
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



    /**
     * 获取问题
     *
     * @param surveyId
     * @param redpackId
     */
    public void getProblems(long surveyId, String redpackId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getProblems(surveyId, redpackId)), new ApiCallback<LookVideoResult>() {
            @Override
            public void onSuccess(LookVideoResult data) {
                mvpView.setProblems(data);
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


    /**
     * 校验配置
     *
     * @param redPacketType
     */
    public void getConfig(String redPacketType) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getConfig(redPacketType)), new ApiCallback<RedBagConfig>() {
            @Override
            public void onSuccess(RedBagConfig data) {
                mvpView.checkConfigSuccess(data);
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
