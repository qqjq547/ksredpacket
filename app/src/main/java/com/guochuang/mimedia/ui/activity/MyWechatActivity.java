package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        tvTitle.setText(R.string.my_weixin);
        mvpPresenter.getWechatInfo();
    }

    @OnClick({R.id.iv_back, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_ensure:
                linUnbind.setVisibility(View.GONE);
                linBind.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setData(MyWechat data) {
        closeLoadingDialog();
        linBind.setVisibility(View.VISIBLE);
        GlideImgManager.loadCircleImage(this,data.getAvatar(),ivAvatar);
        tvNickname.setText(data.getNickName());
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
