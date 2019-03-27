package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.CardAddPresenter;
import com.guochuang.mimedia.mvp.view.CardAddView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.dialog.PassDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardAddActivity extends MvpActivity<CardAddPresenter> implements CardAddView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;

    long id;
    String name;
    String cardNumber;
    String cardMobile;
    PassDialog passDialog;
    TextWatcher tw;

    @Override
    protected CardAddPresenter createPresenter() {
        return new CardAddPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_card_add;
    }

    @Override
    public void initViewAndData() {
        if (getIntent().getIntExtra(Constant.CARDID, 0) != 0) {
            tvTitle.setText(R.string.add_bankcard);
        } else {
            tvTitle.setText(R.string.alter_bankcard);
        }
        id = getIntent().getLongExtra(Constant.CARDID, 0);
        name = getIntent().getStringExtra(Constant.NAME);
        cardNumber = getIntent().getStringExtra(Constant.CARDNUMBER);
        cardMobile = getIntent().getStringExtra(Constant.CARDMOBILE);
        if (id > 0) {
            tvTitle.setText(R.string.bankcard_msg);
            etName.setText(name);
            etNumber.setText(cardNumber);
            etMobile.setText(cardMobile);
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText(R.string.add_bankcard);
            tvDelete.setVisibility(View.GONE);
        }
        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString().trim().replace(" ", "");
                String result = "";
                if (str.length() >= 4) {
                    etNumber.removeTextChangedListener(tw);
                    for (int i = 0; i < str.length(); i++) {
                        result += str.charAt(i);
                        if ((i + 1) % 4 == 0) {
                            result += " ";
                        }
                    }
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    etNumber.setText(result);
                    etNumber.addTextChangedListener(tw);
                    etNumber.setSelection(etNumber.getText().toString().length());//焦点到输入框最后位置
                }
            }
        };
        etNumber.addTextChangedListener(tw);
    }


    @OnClick({R.id.iv_back, R.id.tv_ensure, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_ensure:
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
                            if (getIntent().getIntExtra(Constant.CARDID, 0) == 0) {
                                mvpPresenter.userCardAdd(
                                        "",
                                        etMobile.getText().toString(),
                                        etName.getText().toString(),
                                        etNumber.getText().toString().replaceAll(" ", ""),
                                        "",
                                        code
                                );
                            } else {
                                mvpPresenter.userCardUpdate(
                                        String.valueOf(getIntent().getIntExtra(Constant.CARDID, 0)),
                                        etMobile.getText().toString(),
                                        etName.getText().toString(),
                                        etNumber.getText().toString().replaceAll(" ", ""),
                                        "",
                                        code
                                );
                            }
                        }
                    });
                    passDialog.setNeedIdentity(false);
                    passDialog.setBackVisible(false);
                }
                if (passDialog.isShowing()) {
                    passDialog.dismiss();
                } else {
                    passDialog.show();
                }
                break;
            case R.id.tv_delete:
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
                            mvpPresenter.userCardUnbind(
                                    String.valueOf(getIntent().getLongExtra(Constant.CARDID, 0)),
                                    code
                            );
                        }
                    });
                    passDialog.setBackVisible(false);
                }
                if (passDialog.isShowing()) {
                    passDialog.dismiss();
                } else {
                    passDialog.show();
                }
                break;
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        passDialog.dismiss();
        showShortToast(getResources().getString(R.string.success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.clearCode();
        }
    }

    private boolean doCheck() {
        if (etName.getText().length() <= 0) {
            showShortToast(getResources().getString(R.string.input_name_error));
            return false;
        }
        if (etNumber.getText().length() <= 0) {
            showShortToast(getResources().getString(R.string.input_bank_error));
            return false;
        }
        if (etMobile.getText().length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        return true;
    }
}
