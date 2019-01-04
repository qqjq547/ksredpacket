package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.MyKsbGrantPresenter;
import com.guochuang.mimedia.mvp.view.MyKsbGrantView;
import com.guochuang.mimedia.ui.dialog.PassDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyKsbGrantActivity extends MvpActivity<MyKsbGrantPresenter> implements MyKsbGrantView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_grant_ksb_num)
    TextView tvGrantKsbNum;
    @BindView(R.id.et_grant_your)
    EditText etGrantYour;
    @BindView(R.id.et_grant_num)
    EditText etGrantNum;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    PassDialog passDialog;
    MyKsb myKsb;

    @Override
    protected MyKsbGrantPresenter createPresenter() {
        return new MyKsbGrantPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ksb_grant;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_ksb_ksb_grant_title));
        tvText.setText(getResources().getString(R.string.my_ksb_ksb_grant_record));
        etGrantNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().contains(".")) {
//                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
//                        charSequence = charSequence.toString().subSequence(0,
//                                charSequence.toString().indexOf(".") + 3);
//                        etGrantNum.setText(charSequence);
//                        etGrantNum.setSelection(charSequence.length());
//                    }
//                }
//                if (charSequence.toString().trim().substring(0).equals(".")) {
//                    charSequence = "0" + charSequence;
//                    etGrantNum.setText(charSequence);
//                    etGrantNum.setSelection(2);
//                }
//
//                if (charSequence.toString().startsWith("0")
//                        && charSequence.toString().trim().length() > 1) {
//                    if (!charSequence.toString().substring(1, 2).equals(".")) {
//                        etGrantNum.setText(charSequence.subSequence(0, 1));
//                        etGrantNum.setSelection(1);
//                        return;
//                    }
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    return;
                }
                if (myKsb == null) {
                    return;
                }
                if (Double.parseDouble(editable.toString()) > Double.parseDouble(myKsb.getCoin())) {
                    etGrantNum.setText(String.valueOf(myKsb.getCoin()));
                }
            }
        });
        mvpPresenter.getMyKsb();
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, MyKsbGrantRecActivity.class));
                break;
            case R.id.tv_confirm:
                if (!doCheck()) {
                    return;
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
                            mvpPresenter.userTransferAdd(
                                    etGrantYour.getText().toString(),
                                    Double.parseDouble(etGrantNum.getText().toString()),
                                    "",
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
        showShortToast(getResources().getString(R.string.grant_success));
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setKsbPreiceData(MyKsb data) {
        this.myKsb = data;
        tvGrantKsbNum.setText(String.valueOf(data.getCoin()));
    }

    @Override
    public void setKsbPreiceError(String msg) {
        showShortToast(msg);
    }

    private boolean doCheck() {
        if (TextUtils.isEmpty(etGrantYour.getText().toString())) {
            showLongToast(getResources().getString(R.string.my_ksb_grant_your_error));
            return false;
        }
        if (TextUtils.isEmpty(etGrantNum.getText().toString())) {
            showLongToast(getResources().getString(R.string.my_ksb_grant_num_error));
            return false;
        }
        return true;
    }
}
