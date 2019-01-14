package com.guochuang.mimedia.tools.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.activity.EditRedbagActivity;
import com.sz.gcyh.KSHongBao.wxapi.WXPayEntryActivity;

import java.util.Map;

public class AliPay {
    static AliPay aliPay;
    public static AliPay getInstance() {
        if (aliPay==null){
            aliPay=new AliPay();
        }
        return aliPay;
    }
    public interface OnResultListener{
        void onResult(boolean success,String errMsg);
    }
    public interface OnAuthResultListener{
        void onSuccess(AuthResult result);
        void onFail(String errmsg);
    }


    public void pay(final Activity activity, final String jsonParams, final OnResultListener listener) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                final Map< String, String > result = alipay.payV2(jsonParams, true);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PayResult payResult = new PayResult(result);
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        boolean success=TextUtils.equals(resultStatus, "9000");
                        listener.onResult(success,resultInfo);
                    }
                });
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public void auth(final Activity activity, final String jsonParams, final OnAuthResultListener listener) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                AuthTask authTask=new AuthTask(activity);
                final Map< String, String > result = authTask.authV2(jsonParams, true);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AuthResult authResult = new AuthResult(result,true);
                        String resultInfo = authResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = authResult.getResultStatus();
                        boolean success=TextUtils.equals(resultStatus, "9000");
                        if (success){
                            listener.onSuccess(authResult);
                        }else {
                            listener.onFail(resultInfo);
                        }
                    }
                });
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
