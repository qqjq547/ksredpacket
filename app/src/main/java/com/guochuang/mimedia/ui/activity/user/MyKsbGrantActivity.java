package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
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
import butterknife.OnClick;

public class MyKsbGrantActivity extends MvpActivity<MyKsbGrantPresenter> implements MyKsbGrantView {
    enum ChangeTextView {
        ETIPUTMONEY,

        ETGRANTNUM
    }


    ChangeTextView currentChange;


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_grant_ksb_num)
    TextView tvGrantKsbNum;

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.et_iput_money)
    EditText etIputMoney;


    @BindView(R.id.et_grant_your)
    EditText etGrantYour;
    @BindView(R.id.et_grant_num)
    EditText etGrantNum;

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    PassDialog passDialog;
    MyKsb myKsb;
    private double mRate;


    TextWatcher mInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (currentChange == ChangeTextView.ETGRANTNUM) return;

            try {
                //金钱改变了
                if (TextUtils.isEmpty(s.toString().trim())) {
                    etGrantNum.setText("");
                } else {

                    int index = s.toString().trim().indexOf(".");
                    if (index != -1) {
                        if (s.toString().trim().length() - index > 3) {
                            s = s.toString().trim().substring(0, index + 3);
                            etIputMoney.setText(s);
                        }
                    }


                    if (s.toString().trim().startsWith(".")) {
                        etIputMoney.setText("0.");
                    }
                    //计算ksb
                    double aDouble = Double.valueOf(s.toString().trim());

                    etGrantNum.setText(RMB2Ksb(aDouble, mRate));

                    if (TextUtils.isEmpty(s)) {
                        return;
                    }
                    if (myKsb == null) {
                        return;
                    }
                    if (Double.parseDouble(s.toString()) > Double.parseDouble(myKsb.getMoney())) {
                        etGrantNum.setText(String.valueOf(myKsb.getCoin()));
                        etIputMoney.setText(myKsb.getMoney());
                        etIputMoney.setSelection(myKsb.getMoney().length());
                        return;
                    }

                    if (Double.parseDouble(s.toString()) == Double.parseDouble(myKsb.getMoney())) {
                        etGrantNum.setText(String.valueOf(myKsb.getCoin()));
                    }


                }


            } catch (Exception e) {

            }

            etIputMoney.setSelection(s.toString().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (currentChange == ChangeTextView.ETGRANTNUM) return;


        }
    };


    TextWatcher etGrantNumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (currentChange == ChangeTextView.ETIPUTMONEY) return;

            try {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    etIputMoney.setText("");
                } else {




                    if (s.toString().trim().startsWith(".")) {
                        etGrantNum.setText("0.");
                    }
                    //计算ksb
                    double aDouble;
                    try {
                        aDouble = Double.valueOf(s.toString().trim());
                    } catch (Exception e) {
                        return;
                    }

                    etIputMoney.setText(Ksb2RMB(aDouble, mRate));



                    if (TextUtils.isEmpty(s)) {
                        return;
                    }
                    if (myKsb == null) {
                        return;
                    }
                    if (Double.parseDouble(s.toString()) > Double.parseDouble(myKsb.getCoin())) {
                        etIputMoney.setText(myKsb.getMoney());
                        etGrantNum.setText(myKsb.getCoin());
                        etGrantNum.setSelection(myKsb.getCoin().length());
                        return;
                    }

                    if(Double.parseDouble(s.toString()) == Double.parseDouble(myKsb.getCoin())) {
                        etIputMoney.setText(myKsb.getMoney());
                    }



                }
            } catch (Exception e) {

            }

            etGrantNum.setSelection(s.toString().length());


        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (currentChange == ChangeTextView.ETIPUTMONEY) return;

        }
    };


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

        etGrantNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    currentChange = ChangeTextView.ETGRANTNUM;

                }
            }
        });

        etIputMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    currentChange = ChangeTextView.ETIPUTMONEY;

                }
            }
        });


        etGrantNum.addTextChangedListener(etGrantNumTextWatcher);


        etIputMoney.addTextChangedListener(mInputTextWatcher);


        mvpPresenter.getMyKsb();
    }

    /**
     * 人民币转化为KSB
     *
     * @param rmb
     * @param rate
     */
    private String RMB2Ksb(double rmb, double rate) {
        String s = String.valueOf(rmb / rate);
        int index = s.indexOf(".");
        String substring = s;
        if (s.length() - index > 4) {
            substring = s.substring(0, index + 4);
        }

        return substring;
    }

    /**
     * 人民币转化为KSB
     *
     * @param ksb
     * @param rate
     */
    private String Ksb2RMB(double ksb, double rate) {
        String s = String.valueOf(ksb * rate);
        int index = s.indexOf(".");
        String substring = s;
        if (s.length() - index > 2) {
            substring = s.substring(0, index + 2);
        }

        return substring;

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
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
        showShortToast(getResources().getString(R.string.grant_success));
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        setResult(RESULT_OK, getIntent());
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

    @Override
    public void setKsbPreiceData(MyKsb data) {
        this.myKsb = data;
        tvGrantKsbNum.setText(String.valueOf(data.getCoin()));
        String ksbPrice = data.getKsbPrice();
        mRate = Double.valueOf(ksbPrice);
        tvMoney.setText(Html.fromHtml(data.getMoney() + "<font color='#ff7519S'>元</font>"));

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
