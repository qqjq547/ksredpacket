package com.guochuang.mimedia.http.retrofit;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.http.exception.ApiException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;


public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(ApiException exception);

    public abstract void onFinish();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {//返回的过滤封装好的ApiException
            ApiException apiException = (ApiException) e;
            onFailure(apiException);
        } else {//返回原始的Throwable
            String errMsg = "";
            if (e instanceof ConnectException) {
                errMsg = App.getInstance().getString(R.string.network_error_and_late);
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                errMsg = App.getInstance().getString(R.string.parse_error_and_late);
            } else if (e instanceof HttpException) {
                errMsg = App.getInstance().getString(R.string.http_error_and_late);
            } else if (e instanceof SocketTimeoutException) {
                errMsg = App.getInstance().getString(R.string.timeout_and_late);
            } else {
                errMsg = App.getInstance().getString(R.string.timeout_and_late);
            }
            onFailure(new ApiException(
                    errMsg));
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
