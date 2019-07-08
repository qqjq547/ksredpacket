package com.guochuang.mimedia.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.HttpResponse;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    public static <T> Observable.Transformer<HttpResponse<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<HttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<HttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResponse<T> tHttpResponse) {
                        int code = tHttpResponse.getCode();
                        if (code==Constant.HTTP_STATUS_FORCE_LOGIN){
                            App.getInstance().forceLogin();
                            return null;
                        }
                        if (tHttpResponse.isSuccess()){
                            return createData(tHttpResponse.getResponse());
                        }else {
                            return Observable.error(new ApiException(tHttpResponse.getMessage(), tHttpResponse.getCode()));
                        }
                    }
                });
            }
        };
    }
    public static  Observable.Transformer<com.guochuang.mimedia.http.response.HttpResponse, com.guochuang.mimedia.http.response.HttpResponse> handleResultHttpResponse() {   //compose判断结果
        return new Observable.Transformer<com.guochuang.mimedia.http.response.HttpResponse, com.guochuang.mimedia.http.response.HttpResponse>() {
            @Override
            public Observable<com.guochuang.mimedia.http.response.HttpResponse> call(Observable<HttpResponse> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<com.guochuang.mimedia.http.response.HttpResponse, Observable<com.guochuang.mimedia.http.response.HttpResponse>>() {
                    @Override
                    public Observable<com.guochuang.mimedia.http.response.HttpResponse> call(com.guochuang.mimedia.http.response.HttpResponse tHttpResponse) {
                        int code = tHttpResponse.getCode();
                        if (code==Constant.HTTP_STATUS_FORCE_LOGIN){
                            App.getInstance().forceLogin();
                            return null;
                        }
                        if (tHttpResponse.isSuccess()){
                            return createData(tHttpResponse);
                        }else {
                            return Observable.error(new ApiException(tHttpResponse.getMessage(), tHttpResponse.getCode()));
                        }
                    }
                });
            }
        };
    }
    public static <Bitmap> Observable.Transformer<ResponseBody, android.graphics.Bitmap> handleBitmapResult() {   //compose判断结果
        return new Observable.Transformer<ResponseBody, android.graphics.Bitmap>() {
            @Override
            public Observable<android.graphics.Bitmap> call(Observable<ResponseBody> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<ResponseBody, Observable<android.graphics.Bitmap>>() {
                    @Override
                    public Observable<android.graphics.Bitmap> call(ResponseBody arg0) {
                        android.graphics.Bitmap bitmap=null;
                        try {
                            bitmap=BitmapFactory.decodeStream(arg0.byteStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return createData(bitmap);
                    }
                });
            }
        };
    }
    public static <T> Observable<T> createHttpObservable(Observable<HttpResponse<T>> observable) {
        return observable
                .compose(RxUtil.<HttpResponse<T>>rxSchedulerHelper())
                .compose(RxUtil.<T>handleResult());
    }
    public static Observable<HttpResponse> createHttpResponseObservable(Observable<HttpResponse> observable) {
        return observable
                .compose(RxUtil.<HttpResponse>rxSchedulerHelper())
                .compose(RxUtil.handleResultHttpResponse());
    }

    public static <T> Observable<T> createBaseObservable(Observable<T> observable) {
        return observable
                .compose(RxUtil.<T>rxSchedulerHelper());
    }
    public static <T> Observable<Bitmap> createBmpObservable(Observable<ResponseBody> observable) {
        return observable
                .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
                .compose(RxUtil.<Bitmap>handleBitmapResult());
    }


    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }


    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

    }

}
