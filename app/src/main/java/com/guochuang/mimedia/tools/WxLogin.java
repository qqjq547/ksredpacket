package com.guochuang.mimedia.tools;

import com.guochuang.mimedia.app.App;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public class WxLogin {
    static WxLogin wxLogin;
    public static WxLogin getInstance() {
        if (wxLogin ==null){
            wxLogin =new WxLogin();
        }
        return wxLogin;
    }
    public interface OnResultListener{
         void onResult(String wxCode, String errMsg);
    }
    OnResultListener onResultListener;

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void login(OnResultListener onResultListener) {
        this.onResultListener=onResultListener;
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "ks_wx_login";
        App.getInstance().getWxapi().sendReq(req);
    }
}
