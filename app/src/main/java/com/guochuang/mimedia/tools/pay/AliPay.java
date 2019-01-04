package com.guochuang.mimedia.tools.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
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

}
