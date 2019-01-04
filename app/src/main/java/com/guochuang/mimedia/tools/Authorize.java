package com.guochuang.mimedia.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sz.gcyh.KSHongBao.R;

import cn.jiguang.share.android.api.AuthListener;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.model.AccessTokenInfo;
import cn.jiguang.share.android.model.BaseResponseInfo;
import cn.jiguang.share.android.model.UserInfo;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Authorize {
    Context context;
    public interface OnAuthorizeResultListener{
        void onSucess(UserInfo userInfo);
        void onError(String errorMsg);
        void onCancel();
    }
    OnAuthorizeResultListener resultListener;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String pl=msg.obj.toString();
            authorizeShow(pl);
        }
    };
    public Authorize(Context context, OnAuthorizeResultListener resultListener) {
        this.context = context;
        this.resultListener=resultListener;
    }

    public void authorizeShow(String platform) {
        if (JShareInterface.isSupportAuthorize(platform)) {
            if (JShareInterface.isAuthorize(platform)) {
                JShareInterface.getUserInfo(platform,mAuthListener) ;
            } else {
                JShareInterface.authorize(platform, mAuthListener);
            }
        } else {
            resultListener.onError(context.getString(R.string.not_find_wechat_app));
        }

    }

    AuthListener mAuthListener = new AuthListener() {
        @Override
        public void onComplete(Platform platform, int action, BaseResponseInfo data) {
            LogUtil.d("onComplete:" + platform + ",action:" + action + ",data:" + data);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    if (data instanceof AccessTokenInfo) {        //授权信息
                        String token = ((AccessTokenInfo) data).getToken();//token
                        long expiration = ((AccessTokenInfo) data).getExpiresIn();//token有效时间，时间戳
                        String refresh_token = ((AccessTokenInfo) data).getRefeshToken();//refresh_token
                        String openid = ((AccessTokenInfo) data).getOpenid();//openid
                        //授权原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        LogUtil.d( "openid:" + openid + ",token:" + token + ",expiration:" + expiration + ",refresh_token:" + refresh_token);
                        LogUtil.d("originData:" + originData);
                        Message message=new Message();
                        message.obj=platform.getName();
                        handler.sendMessageDelayed(message,500);
                    }
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    break;
                case Platform.ACTION_USER_INFO:
                    if (data instanceof UserInfo) {      //第三方个人信息
                        String openid = ((UserInfo) data).getOpenid();  //openid
                        String name = ((UserInfo) data).getName();  //昵称
                        String imageUrl = ((UserInfo) data).getImageUrl();  //头像url
                        int gender = ((UserInfo) data).getGender();//性别, 1表示男性；2表示女性
                        //个人信息原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        LogUtil.d( "openid:" + openid + ",name:" + name + ",gender:" + gender + ",imageUrl:" + imageUrl);
                        LogUtil.d("originData:" + originData);
                        resultListener.onSucess((UserInfo)data);
                    }
                    break;
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            LogUtil.d("onError:" + platform + ",action:" + action + ",errorCode:" + errorCode+",error:" + error.getStackTrace());
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "授权失败";
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    toastMsg = "删除授权失败";
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "获取个人信息失败";
                    break;
            }
            resultListener.onError(toastMsg);

        }

        @Override
        public void onCancel(Platform platform, int action) {
            LogUtil.d("onCancel:" + platform + ",action:" + action);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "取消授权";
                    break;
                // TODO: 2017/6/23 删除授权不存在取消
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "取消获取个人信息";
                    break;
            }
            resultListener.onCancel();

        }
    };

}
