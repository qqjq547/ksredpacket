/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.sz.gcyh.KSHongBao.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.tools.WxLogin;
import com.sz.gcyh.KSHongBao.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getWxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() != 1) {
            finish();
            return;
        }

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                WxLogin.getInstance().getOnResultListener().onResult(code,null);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                WxLogin.getInstance().getOnResultListener().onResult(null,getString(R.string.authorize_fail));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                WxLogin.getInstance().getOnResultListener().onResult(null,getString(R.string.authorize_fail));
                break;
                default:
                WxLogin.getInstance().getOnResultListener().onResult(null,getString(R.string.authorize_fail));
                    break;
        }
        finish();
    }
}
