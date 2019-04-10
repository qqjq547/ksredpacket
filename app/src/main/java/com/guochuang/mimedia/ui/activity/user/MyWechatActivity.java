package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guochuang.mimedia.mvp.model.PhoneEntity;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.WxLogin;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MyWechat;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.MyWechatPresenter;
import com.guochuang.mimedia.mvp.view.MyWechatView;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * 判断是谁开启MyWechatActivity
 */
public class MyWechatActivity extends MvpActivity<MyWechatPresenter> implements MyWechatView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.lin_bind)
    LinearLayout linBind;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.lin_unbind)
    LinearLayout linUnbind;

    String mMobile;
    String mPassword;
    private Box<PhoneEntity> mBox;
    @Override
    protected MyWechatPresenter createPresenter() {
        return new MyWechatPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_wechat;
    }

    @Override
    public void initViewAndData() {
        linBind.setVisibility(View.GONE);
        linUnbind.setVisibility(View.GONE);

        BoxStore boxStore = App.getInstance().getBoxStore();
        mBox = boxStore.boxFor(PhoneEntity.class);

        String whoOpen = getIntent().getStringExtra(Constant.WHO_OPEN_MYWECHATACTIVITY);
        if(SafeCenterActivity.class.getSimpleName().equals(whoOpen)){
            //已绑定展示信息
            tvTitle.setText(R.string.my_weixin);
            linBind.setVisibility(View.VISIBLE);
            mvpPresenter.getWechatInfo();
        }else if(RegisterActivity.class.getSimpleName().equals(whoOpen)) {
            //还未绑定
            tvTitle.setText(R.string.type_login_register_success);
            linUnbind.setVisibility(View.VISIBLE);
            mMobile =  getIntent().getStringExtra(Constant.UESRPHONE_KEY);
            mPassword =  getIntent().getStringExtra(Constant.UESRPASSWORLD_KEY);
        }else if(LoginActivity.class.getSimpleName().equals(whoOpen)) {
            tvTitle.setText(R.string.type_login_apllay_weixin);
            mMobile =  getIntent().getStringExtra(Constant.UESRPHONE_KEY);
            mPassword =  getIntent().getStringExtra(Constant.UESRPASSWORLD_KEY);
            linUnbind.setVisibility(View.VISIBLE);
        }




    }

    @OnClick({R.id.iv_back, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_ensure:

                applayWeiXin();

                break;
        }
    }

    /**
     * 绑定微信
     */
    private void applayWeiXin() {
        WxLogin.getInstance().login(new WxLogin.OnResultListener() {
            @Override
            public void onResult(String wxCode, String errMsg) {
                if (TextUtils.isEmpty(errMsg)) {
//                    showLoadingDialog(null);
//                    mvpPresenter.userAppWechatBind(mMobile,mPassword,Constant.SYSTEM_CODE,wxCode);
                    mvpPresenter.mobileBindWechat(mMobile,mPassword,Constant.SYSTEM_CODE,wxCode);
                    Log.e("onResult: ",wxCode );

                } else {
                    showShortToast(errMsg);
                }
            }
        });
    }

    @Override
    public void setData(MyWechat data) {
        closeLoadingDialog();
        linBind.setVisibility(View.VISIBLE);
        GlideImgManager.loadCircleImage(this, data.getAvatar(), ivAvatar);
        tvNickname.setText(data.getNickName());
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setBindWxError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setBindWxData(String data) {
        closeLoadingDialog();
        linUnbind.setVisibility(View.GONE);
        linBind.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.my_weixin);
        mvpPresenter.getWechatInfo();
    }


    /**
     * 绑定成功 并且登录
     * @param data
     */
    @Override
    public void setBindSuccessAndLoginData(String data) {
        closeLoadingDialog();
        savePhone();
        UserLogin userLogin = new Gson().fromJson(CommonUtil.baseDecrypt(data.split("\\.")[1]), UserLogin.class);
        getPref().setString(PrefUtil.USER_TOKEN, data);
//        if (TextUtils.isEmpty(userLogin.getMobile())) {
//            Intent intent = new Intent(this, BindingPhoneAcitivity.class);
//            startActivity(intent);
//            finish();
//        } else {
            getPref().setString(PrefUtil.MOBILE, userLogin.getMobile());
            IntentUtils.startMainActivity(this, true);
            //关闭登录

            App.getInstance().finishActivity(LoginActivity.class);
            finish();
//        }

    }

    private void savePhone() {

        List<PhoneEntity> phoneEntitys = mBox.getAll();
        for (int i = 0; i < phoneEntitys.size(); i++) {
            if (mMobile.equals(phoneEntitys.get(i).phone)) {
                mBox.remove(phoneEntitys.get(i));
            }
        }

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setPhone(mMobile);
        mBox.put(phoneEntity);
    }


}
