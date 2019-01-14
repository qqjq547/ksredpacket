package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.AlipayAccout;
import com.guochuang.mimedia.mvp.presenter.MyAlipayPresenter;
import com.guochuang.mimedia.mvp.view.MyAlipayView;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.ui.dialog.PassDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAlipayActivity extends MvpActivity<MyAlipayPresenter> implements MyAlipayView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;

    PassDialog passDialog;

    @Override
    protected MyAlipayPresenter createPresenter() {
        return new MyAlipayPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_alipay;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_zfb);
        showLoadingDialog(null);
        mvpPresenter.userInfoAlipayAccout();
    }


    @OnClick({R.id.iv_back, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_ensure:
                if (TextUtils.isEmpty(etName.getText())) {
                    showShortToast(getResources().getString(R.string.input_alipay_name_error));
                    break;
                }
                if (TextUtils.isEmpty(etAccount.getText())) {
                    showShortToast(getResources().getString(R.string.input_alipay_user_error));
                    break;
                }
                if (passDialog == null) {
                    passDialog = new PassDialog(this, new PassDialog.OnPassDialogListener() {
                        @Override
                        public void close() {

                        }

                        @Override
                        public void go() {

                        }

                        @Override
                        public void onNumFull(String code) {
                            showLoadingDialog(null);
                            mvpPresenter.userInfoBindAlipay(
                                    etName.getText().toString(),
                                    etAccount.getText().toString(),
                                    code
                            );
                        }
                    });
                    passDialog.setNeedIdentity(false);
                    passDialog.setBackVisible(false);
                }
                if (passDialog != null && passDialog.isShowing()) {
                    passDialog.dismiss();
                } else {
                    passDialog.show();
                }
                break;
        }
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        new DialogBuilder(this)
                .setTitle(R.string.tip)
                .setMessage(msg)
                .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .create().show();
    }

    @Override
    public void setGetData(AlipayAccout data) {
        closeLoadingDialog();
        if (data != null) {
            if (!TextUtils.isEmpty(data.getAlipayRealName())) {
                etName.setText(data.getAlipayRealName());
//                etName.setEnabled(false);
            }
            if (!TextUtils.isEmpty(data.getAlipayAccount())) {
                etAccount.setText(data.getAlipayAccount());
//                etAccount.setEnabled(false);
            }
            if (!TextUtils.isEmpty(data.getAlipayRealName()) && !TextUtils.isEmpty(data.getAlipayAccount())) {
//                tvEnsure.setVisibility(View.GONE);
                tvEnsure.setText(getResources().getString(R.string.alter));
            }
        }
    }

    @Override
    public void setGetError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
