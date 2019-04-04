package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.tools.Constant;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                    showLoadingDialog(null);
                    mvpPresenter.userAppWechatBind(
                            Constant.TENANTCODE,
                            Constant.SYSTEM_CODE,
                            wxCode
                    );
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
}
