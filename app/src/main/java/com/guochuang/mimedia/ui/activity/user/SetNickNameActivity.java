package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.SetNickNamePresenter;
import com.guochuang.mimedia.mvp.view.SetNickNameView;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class SetNickNameActivity extends MvpActivity<SetNickNamePresenter> implements SetNickNameView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    UserInfo userInfo;
    String nickName;

    @Override
    protected SetNickNamePresenter createPresenter() {
        return new SetNickNamePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_set_nickname;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.nickname);
       userInfo= App.getInstance().getUserInfo();
       if (userInfo!=null){
           nickName=userInfo.getNickName();
           etNickname.setText(userInfo.getNickName());
       }
    }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
                nickName=etNickname.getText().toString().trim();
                showLoadingDialog(null);
                mvpPresenter.addUserInfo(nickName);
                break;
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.setup_success);
        userInfo.setNickName(nickName);
        App.getInstance().setUserInfo(userInfo);
        Intent intent=new Intent();
        intent.putExtra(Constant.NICKNAME,nickName);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
