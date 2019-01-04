package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.SetInviterPresenter;
import com.guochuang.mimedia.mvp.presenter.SetNickNamePresenter;
import com.guochuang.mimedia.mvp.view.SetInviterView;
import com.guochuang.mimedia.mvp.view.SetNickNameView;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class SetInviterActivity extends MvpActivity<SetInviterPresenter> implements SetInviterView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_inviter)
    EditText etInviter;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    String inviterCode;

    @Override
    protected SetInviterPresenter createPresenter() {
        return new SetInviterPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_set_inviter;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.inviter);

    }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
                inviterCode=etInviter.getText().toString().trim();
                showLoadingDialog(null);
                mvpPresenter.addUserInfo(inviterCode);
                break;
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.setup_success);
        Intent intent=new Intent();
        intent.putExtra(Constant.INVITER,inviterCode);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
