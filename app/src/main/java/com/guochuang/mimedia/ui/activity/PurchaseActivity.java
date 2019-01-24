package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.pay.AliPay;
import com.guochuang.mimedia.tools.pay.WxPay;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.presenter.PurchasePresenter;
import com.guochuang.mimedia.mvp.view.PurchaseView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class PurchaseActivity extends MvpActivity<PurchasePresenter> implements PurchaseView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rbtn_alipay)
    RadioButton rbtnAlipay;
    @BindView(R.id.rbtn_wxpay)
    RadioButton rbtnWxpay;
    @BindView(R.id.rbtn_ksbpay)
    RadioButton rbtnKsbpay;
    @BindView(R.id.rg_way)
    RadioGroup rgWay;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_equal_ksb)
    TextView tvEqualKsb;
    @BindView(R.id.cb_read)
    CheckBox cbRead;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.bt_ensure)
    Button btEnsure;

    int purchaseType=0;
    String money;
    String ksb;
    String acountKsb;
    long regionId=0;
    int payType=0;
    int payNumber=0;
    long snatchId=0;
    @Override
    protected PurchasePresenter createPresenter() {
        return new PurchasePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_purchase;
    }

    @Override
    public void initViewAndData() {
        purchaseType=getIntent().getIntExtra(Constant.PURCHASE_TYPE,0);
        money=getIntent().getStringExtra(Constant.MONEY);
        regionId=getIntent().getLongExtra(Constant.REGIONID,0);
        acountKsb=getPref().getString(PrefUtil.COIN,"");
        payNumber=getIntent().getIntExtra(Constant.PAYNUMBER,0);
        snatchId=getIntent().getLongExtra(Constant.SNATCHID,0);
        if (purchaseType==Constant.TYPE_PURCHASE_REGION){
            tvTitle.setText(R.string.buy_city_owner);
            tvAgreement.setText(R.string.city_buy_agreement);
        }else if(purchaseType==Constant.TYPE_PURCHASE_AGENT){
            tvTitle.setText(R.string.bug_agent);
            tvAgreement.setText(R.string.agent_agreement);
        }else if(purchaseType==Constant.TYPE_PURCHASE_HONEYCOMB){
            tvTitle.setText(R.string.bug_honycomb);
            tvAgreement.setText(R.string.hongycomb_agreement);
        }else if(purchaseType==Constant.TYPE_PURCHASE_SNATCH){
            tvTitle.setText(R.string.buy_snatch);
            tvAgreement.setText(R.string.snatch_agreement);
        }
        tvAmount.setText(String.valueOf(money));
        rbtnKsbpay.setText(String.format(getString(R.string.format_yuan),String.valueOf(money)));
        showLoadingDialog(null);
        mvpPresenter.calValue(Double.parseDouble(money),Constant.CAL_TYPE_COIN);
    }


    @OnClick({R.id.iv_back, R.id.tv_agreement,R.id.bt_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_agreement:
                if (purchaseType==Constant.TYPE_PURCHASE_REGION){
                    IntentUtils.startWebActivity(this,getString(R.string.city_buy_agreement),Constant.URL_AGREEMENT);
                }else if(purchaseType==Constant.TYPE_PURCHASE_AGENT){
                    IntentUtils.startWebActivity(this,getString(R.string.agent_agreement),Constant.URL_AGREEMENT_AGENT);
                }else if(purchaseType==Constant.TYPE_PURCHASE_HONEYCOMB){
                    IntentUtils.startWebActivity(this,getString(R.string.hongycomb_agreement),Constant.URL_HONYCOMB_RULE);
                }else if(purchaseType==Constant.TYPE_PURCHASE_SNATCH){
                    IntentUtils.startWebActivity(this,getString(R.string.snatch_agreement),Constant.URL_DUOBAO_RULE);
                }
                break;
            case R.id.bt_ensure:
                if (rbtnWxpay.isChecked()) {
                    payType = Constant.PAY_TYPE_WXPAY;
                } else if (rbtnAlipay.isChecked()) {
                    payType = Constant.PAY_TYPE_ALIPAY;
                } else if(rbtnKsbpay.isChecked()){
                    payType = Constant.PAY_TYPE_KSB;
                }else {
                    showShortToast(R.string.pls_select_pay_type);
                    return;
                }
                if (cbRead.isChecked()){
                    if (payType == Constant.PAY_TYPE_KSB) {
                        new PassDialog(PurchaseActivity.this, new PassDialog.OnPassDialogListener() {
                            @Override
                            public void close() {

                            }

                            @Override
                            public void go() {

                            }

                            @Override
                            public void onNumFull(String code) {
                                startPay(code);
                            }
                        }).setBackVisible(false).show();
                        return;
                    }
                    startPay(null);
                }else {
                    showShortToast(R.string.pls_select_agreement);
                }
                break;
        }
    }
    public void startPay(String safetyCode){
        showLoadingDialog(null);
        if (purchaseType==Constant.TYPE_PURCHASE_REGION){
            mvpPresenter.buyCityOwner(regionId, Double.parseDouble(money),payType,Constant.CHANNEL_CODE_ANDROID,safetyCode);
        }else if(purchaseType==Constant.TYPE_PURCHASE_AGENT){
            mvpPresenter.upgradeAgent(Constant.CHANNEL_CODE_ANDROID,payType, Double.parseDouble(money),safetyCode);
        }else if(purchaseType==Constant.TYPE_PURCHASE_HONEYCOMB){
            mvpPresenter.appCreateOrder(Constant.CHANNEL_CODE_ANDROID,payNumber,payType,getPref().getLongitude(),getPref().getLatitude(),safetyCode);
        }else if(purchaseType==Constant.TYPE_PURCHASE_SNATCH){
            mvpPresenter.createSnatchOrder(Constant.CHANNEL_CODE_ANDROID,payType,snatchId,payNumber,getPref().getLongitude(),getPref().getLatitude(),safetyCode);
        }
    }
    public void setKsbText(){
        String title = String.format(getString(R.string.format_equal_ksb), ksb);
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        int ksbIndex = title.lastIndexOf(ksb);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), ksbIndex, ksbIndex + ksb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvEqualKsb.setText(builder);
        double ksbDouble = Double.parseDouble(ksb);
        double accountKsbDouble=0;
        if (!TextUtils.isEmpty(acountKsb)){
            accountKsbDouble = Double.parseDouble(acountKsb);
        }
        if (ksbDouble <= accountKsbDouble) {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_ksbpay);
            drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
            rbtnKsbpay.setCompoundDrawables(drawable, null, rbtnKsbpay.getCompoundDrawables()[2], null);
            String myKsb = String.format(getString(R.string.format_ksb_pay), acountKsb);
            SpannableStringBuilder builder1 = new SpannableStringBuilder(myKsb);
            int accountIndex = myKsb.indexOf(acountKsb);
            builder1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), accountIndex, accountIndex + acountKsb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            rbtnKsbpay.setText(builder1);
            rbtnKsbpay.setEnabled(true);
        } else {
            Drawable drawable=getResources().getDrawable(R.drawable.ic_ksbpay_gray);
            drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
            rbtnKsbpay.setCompoundDrawables(drawable, null, null, null);
            String myKsb = String.format(getString(R.string.format_ksb_pay), acountKsb);
            rbtnKsbpay.setText(myKsb);
            rbtnKsbpay.setTextColor(getResources().getColor(R.color.text_gray));
            rbtnKsbpay.setEnabled(false);
        }
    }
    public void showPayResult(boolean success,String errmsg){
        if (success){
            new DialogBuilder(this)
                    .setTitle(R.string.tip)
                    .setMessage(R.string.pay_success)
                    .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
                            if (purchaseType==Constant.TYPE_PURCHASE_REGION){
                               sendBroadcast(new Intent(Constant.ACTION_CHANGE_CITY));
                            }
                            setResult(RESULT_OK);
                            finish();
                        }
                    }).create().show();
        }else {
            new DialogBuilder(this)
                    .setTitle(R.string.tip)
                    .setMessage(TextUtils.isEmpty(errmsg)?getString(R.string.pay_fail):errmsg)
                    .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).create().show();
        }
    }
    public void payResult(Order order){
        switch (payType){
            case  Constant.PAY_TYPE_WXPAY:
                if (TextUtils.isEmpty(order.getVendorResponse())){
                    showShortToast(R.string.can_get_order);
                    return;
                }
                WxPay.getInstance().pay(order.getVendorResponse(), new WxPay.OnResultListener() {
                    @Override
                    public void onResult(boolean success, String errMsg) {
                        showPayResult(success,errMsg);
                    }
                });
                break;
            case  Constant.PAY_TYPE_ALIPAY:
                if (TextUtils.isEmpty(order.getVendorResponse())){
                    showShortToast(R.string.can_get_order);
                    return;
                }
                AliPay.getInstance().pay(this, order.getVendorResponse(), new AliPay.OnResultListener() {
                    @Override
                    public void onResult(boolean success, String errMsg) {
                        showPayResult(success,errMsg);
                    }
                });
                break;
            case  Constant.PAY_TYPE_KSB:
                showPayResult(true,null);
                break;
        }
    }
    @Override
    public void setBuyCity(String data) {
        closeLoadingDialog();
        if (!TextUtils.isEmpty(data)) {
            Order order=GsonUtil.GsonToBean(data,Order.class);
            payResult(order);
        }else {
            showShortToast(R.string.can_get_order);
        }
    }

    @Override
    public void setUpgradeAgent(Order data) {
        closeLoadingDialog();
        if (data!=null){
            payResult(data);
        }else {
            showShortToast(R.string.can_get_order);
        }
    }

    @Override
    public void setBuyHonyComb(Order data) {
        closeLoadingDialog();
        if (data!=null){
            payResult(data);
        }else {
            showShortToast(R.string.can_get_order);
        }
    }
    @Override
    public void setSnatch(Order data) {
        closeLoadingDialog();
        if (data!=null){
            payResult(data);
        }else {
            showShortToast(R.string.can_get_order);
        }
    }

    @Override
    public void setPayResult(CalValue data) {
        closeLoadingDialog();
        ksb=data.getCoinByMoney();
        acountKsb=data.getCoin();
        setKsbText();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
