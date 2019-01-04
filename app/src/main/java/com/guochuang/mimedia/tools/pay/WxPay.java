package com.guochuang.mimedia.tools.pay;

import com.google.gson.Gson;
import com.guochuang.mimedia.app.App;
import com.tencent.mm.opensdk.modelpay.PayReq;

public class WxPay {
    static WxPay wxPay;
    public static WxPay getInstance() {
        if (wxPay==null){
            wxPay=new WxPay();
        }
        return wxPay;
    }
    public interface OnResultListener{
         void onResult(boolean success,String errMsg);
    }
    OnResultListener onResultListener;

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void pay(String res, OnResultListener onResultListener) {
        this.onResultListener=onResultListener;
        final WxPayElement wxPayElement;
        Gson gson = new Gson();
        wxPayElement = gson.fromJson(res, WxPayElement.class);
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = wxPayElement.getAppid();
                request.partnerId = wxPayElement.getPartnerid();
                request.prepayId = wxPayElement.getPrepayid();
                request.packageValue = wxPayElement.getPackageValue();
                request.nonceStr = wxPayElement.getNoncestr();
                request.timeStamp = wxPayElement.getTimestamp() + "";
                request.sign = wxPayElement.getSign();
                App.getInstance().getWxapi().sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
