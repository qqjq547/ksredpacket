package com.guochuang.mimedia.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.presenter.KsbTransferPresenter;
import com.guochuang.mimedia.mvp.view.KsbTransferView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.dialog.BottomWithdrawalDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MyKsbTransferActivity extends MvpActivity<KsbTransferPresenter> implements KsbTransferView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_ksb_ksb_num)
    TextView tvKsbKsbNum;
    @BindView(R.id.tv_ksb_price_num)
    TextView tvKsbPriceNum;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_transfer_all)
    TextView tvTransferAll;
    @BindView(R.id.tv_ksb_equivalence)
    TextView tvKsbEquivalence;
    @BindView(R.id.iv_channel_header)
    ImageView ivChannelHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_num)
    TextView tvUserNum;
    @BindView(R.id.ll_channel_all)
    RelativeLayout llChannelAll;
    @BindView(R.id.ll_poundage)
    LinearLayout llPoundage;
    @BindView(R.id.tv_poundage)
    TextView tvPoundage;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_confirm_num)
    TextView tvConfirmNum;
    @BindView(R.id.tv_confirm_hint)
    TextView tvConfirmHint;

    int payType = 0;
    MyKsb myKsb;
    KsbTransfer ksbTransfer;
    PassDialog passDialog;
    BottomWithdrawalDialog bottomWithdrawalDialog;

    @Override
    protected KsbTransferPresenter createPresenter() {
        return new KsbTransferPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ksb_transfer;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_ksb_ksb_transfer_title));
        tvText.setText(getResources().getString(R.string.my_ksb_ksb_transfer_record));
        mvpPresenter.getMyKsb();
        showLoadingDialog(null);
        mvpPresenter.getMyKsbLimit();
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    tvKsbEquivalence.setText("");
                    llPoundage.setVisibility(View.INVISIBLE);
                    return;
                }
                if (myKsb == null || ksbTransfer == null) {
                    return;
                }
                if (Double.parseDouble(etPrice.getText().toString()) >= Double.parseDouble(myKsb.getMoney())) {
                    double surplus = Double.parseDouble(myKsb.getMoney()) % ksbTransfer.getIncrease();
                    if (surplus != 0) {
                        etPrice.setText(String.valueOf((int)(Double.parseDouble(myKsb.getMoney()) - surplus)));
                    }
                } else {
                    double surplus = Double.parseDouble(etPrice.getText().toString()) % ksbTransfer.getIncrease();
                    if (surplus != 0) {
                        etPrice.setText(String.valueOf((int)(Double.parseDouble(etPrice.getText().toString()) - surplus)));
                    }
                }
                tvKsbEquivalence.setText(String.valueOf(DoubleUtil.divide(Double.parseDouble(etPrice.getText().toString()),Double.parseDouble(myKsb.getKsbPrice()))));
                llPoundage.setVisibility(View.VISIBLE);
                tvPoundage.setText(String.valueOf(DoubleUtil.mul(ksbTransfer.getPoundageRate(),Double.parseDouble(etPrice.getText().toString()))));
                etPrice.setSelection(etPrice.getText().toString().length());
            }
        });
    }
   public int calculate(String moneyStr){
        int money=0;
       try {
            money=(int)Double.parseDouble(moneyStr);
       }catch (Exception e){
           e.printStackTrace();
           showShortToast(e.getMessage());
       }
       return money;
   }
    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_transfer_all, R.id.ll_channel_all, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, MyKsbTransRecActivity.class));
                break;
            case R.id.tv_transfer_all:
                if (myKsb != null&&ksbTransfer!=null) {
                    double surplus = Double.parseDouble(myKsb.getMoney()) % ksbTransfer.getIncrease();
                    if (surplus != 0) {
                        etPrice.setText(String.valueOf((int)(Double.parseDouble(myKsb.getMoney()) - surplus)));
                    }
                }
                break;
            case R.id.ll_channel_all:
                if (ksbTransfer == null) {
                    showShortToast(R.string.cannot_config_info);
                    return;
                }
                if (bottomWithdrawalDialog == null) {
                    bottomWithdrawalDialog = new BottomWithdrawalDialog(this, ksbTransfer.getPayTypeList(), new BottomWithdrawalDialog.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            payType = position;
                            switch (ksbTransfer.getPayTypeList().get(payType).getPayType()) {
                                case 2:
                                    ivChannelHeader.setImageResource(R.drawable.ic_my_ksb_transfer_wx);
                                    tvUserName.setText(R.string.wxpay);
                                    tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                                    break;
                                case 3:
                                    ivChannelHeader.setImageResource(R.drawable.ic_my_ksb_transfer_ali);
                                    tvUserName.setText(R.string.alipay);
                                    tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                                    break;
                                default:
                                    GlideImgManager.loadImage(MyKsbTransferActivity.this, ksbTransfer.getPayTypeList().get(payType).getIcon(), ivChannelHeader);
                                    tvUserName.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                                    tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getAccount());
                                    break;
                            }
                        }
                    });
                }
                bottomWithdrawalDialog.setCheckItem(payType);
                bottomWithdrawalDialog.show();
                break;
            case R.id.tv_confirm:
                if (ksbTransfer == null || ksbTransfer.getPayTypeList() == null || ksbTransfer.getPayTypeList().size() == 0) {
                    showShortToast(R.string.cannot_config_info);
                    return;
                }
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
                            try {
                                int money=calculate(etPrice.getText().toString());
                                if (money>0) {
                                    showLoadingDialog(null);
                                    mvpPresenter.userWithdrawals(
                                            money,
                                            ksbTransfer.getPayTypeList().get(payType).getPayType(),
                                            ksbTransfer.getPayTypeList().get(payType).getId(),
                                            ksbTransfer.getPayTypeList().get(payType).getAccount(),
                                            code
                                    );
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                showShortToast(e.getMessage());
                            }

                        }
                    });
                    passDialog.setNeedIdentity(true);
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

    @SuppressLint("StringFormatMatches")
    private boolean doCheck() {
        if (TextUtils.isEmpty(etPrice.getText().toString())) {
            showShortToast(getResources().getString(R.string.transfer_no_ksb_num));
            return false;
        }
        if (Double.parseDouble(etPrice.getText().toString()) < Double.parseDouble(ksbTransfer.getMinWithdrawals())) {
            showShortToast(String.format(getResources().getString(R.string.transfer_ksb_min_num), ksbTransfer.getMinWithdrawals()));
            return false;
        }
//        if (TextUtils.isEmpty(payType)) {
//            showShortToast(getResources().getString(R.string.transfer_no_pay_type));
//            return false;
//        }
        return true;
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        passDialog.dismiss();
        showShortToast(getResources().getString(R.string.transfer_success));
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        setResult(RESULT_OK,getIntent());
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
    public void setConvertData(KsbTransfer data) {
        closeLoadingDialog();
        if (data != null) {
            this.ksbTransfer = data;
            tvConfirmNum.setText(String.valueOf(data.getSurplusTimes()));
            if (!TextUtils.isEmpty(data.getInstruction())){
                tvConfirmHint.setText(data.getInstruction().replace("\\n", "\n"));
            }
            if (data.getPayTypeList().size() > 0) {
                switch (ksbTransfer.getPayTypeList().get(payType).getPayType()) {
                    case 2:
                        ivChannelHeader.setImageResource(R.drawable.ic_my_ksb_transfer_wx);
                        tvUserName.setText(R.string.wxpay);
                        tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                        break;
                    case 3:
                        ivChannelHeader.setImageResource(R.drawable.ic_my_ksb_transfer_ali);
                        tvUserName.setText(R.string.alipay);
                        tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                        break;
                    default:
                        GlideImgManager.loadImage(MyKsbTransferActivity.this, ksbTransfer.getPayTypeList().get(payType).getIcon(), ivChannelHeader);
                        tvUserName.setText(ksbTransfer.getPayTypeList().get(payType).getName());
                        tvUserNum.setText(ksbTransfer.getPayTypeList().get(payType).getAccount());
                        break;
                }
            }
        }
    }

    @Override
    public void setConvertError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setKsbPreiceData(MyKsb data) {
        this.myKsb = data;
        tvKsbKsbNum.setText(String.valueOf(data.getCoin()));
        tvKsbPriceNum.setText(String.valueOf(data.getMoney()));

    }

    @Override
    public void setKsbPreiceError(String msg) {
        showShortToast(msg);
    }
}
