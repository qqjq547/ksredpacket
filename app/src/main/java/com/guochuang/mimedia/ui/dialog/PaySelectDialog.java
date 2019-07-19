package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.mvp.model.PayConfig;
import com.guochuang.mimedia.tools.CommonUtil;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.presenter.PaySelectPresenter;
import com.guochuang.mimedia.mvp.view.PaySelectView;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class PaySelectDialog extends Dialog implements PaySelectView {

    OnResultListener onResultListener;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rbtn_wxpay)
    RadioButton rbtnWxpay;
    @BindView(R.id.rbtn_alipay)
    RadioButton rbtnAlipay;
    @BindView(R.id.rbtn_ksbpay)
    RadioButton rbtnKsbpay;
    @BindView(R.id.rgroup_pay)
    RadioGroup rgroupPay;

    String money;
    String acountQc;
    PaySelectPresenter presenter;

    public interface OnResultListener {
        void onSelectItem(int postion);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public PaySelectDialog(@NonNull Context context, String money) {
        super(context, R.style.dialog_bottom_full);
        presenter=new PaySelectPresenter(this);
        this.money=money;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_pay_select, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rgroupPay.clearCheck();
        presenter.getPayType(CommonUtil.getTypeParams(Constant.TYPE_PURCHASE_REDBAG));
    }

    @Override
    public void show() {
        super.show();
        presenter.calValue(Double.parseDouble(money),Constant.CAL_TYPE_MONEY);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.onUnsubscribe();
    }

    @OnClick({R.id.iv_delete, R.id.rbtn_wxpay, R.id.rbtn_alipay, R.id.rbtn_ksbpay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                dismiss();
                break;
            case R.id.rbtn_wxpay:
                dismiss();
                if (onResultListener!=null)
                    onResultListener.onSelectItem(0);
                break;
            case R.id.rbtn_alipay:
                dismiss();
                if (onResultListener!=null)
                    onResultListener.onSelectItem(1);
                break;
            case R.id.rbtn_ksbpay:
                dismiss();
                if (onResultListener!=null)
                    onResultListener.onSelectItem(2);
                break;
        }
    }
    @Override
    public void setData(CalValue data) {
      if (data!=null){
          acountQc=data.getQc();
          String title = String.format(getContext().getString(R.string.format_pay_amount), money, money);
          SpannableStringBuilder builder = new SpannableStringBuilder(title);
          int moneyIndex = title.indexOf(money);
          builder.setSpan(new TextAppearanceSpan(getContext(), R.style.span_yellow_style), moneyIndex, moneyIndex + money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          int ksbIndex = title.lastIndexOf(money);
          builder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.text_city_yellow)), ksbIndex, ksbIndex + money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          tvTitle.setText(builder);
          double qcDouble = Double.parseDouble(money);
          double accountQcouble=0;
          if (!TextUtils.isEmpty(acountQc)){
              accountQcouble = Double.parseDouble(acountQc);
          }
          if (qcDouble <= accountQcouble) {
              Drawable drawable=getContext().getResources().getDrawable(R.drawable.ic_ksbpay);
              drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
              rbtnKsbpay.setCompoundDrawables(drawable, null, rbtnKsbpay.getCompoundDrawables()[2], null);
              String myKsb = String.format(getContext().getString(R.string.format_ksb_pay), acountQc);
              SpannableStringBuilder builder1 = new SpannableStringBuilder(myKsb);
              int accountIndex = myKsb.indexOf(acountQc);
              builder1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.text_city_yellow)), accountIndex, accountIndex + acountQc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              rbtnKsbpay.setText(builder1);
              rbtnKsbpay.setEnabled(true);
          } else {
              Drawable drawable=getContext().getResources().getDrawable(R.drawable.ic_ksbpay_gray);
              drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
              rbtnKsbpay.setCompoundDrawables(drawable, null, null, null);
              String myKsb = String.format(getContext().getString(R.string.format_ksb_pay), acountQc);
              rbtnKsbpay.setText(myKsb);
              rbtnKsbpay.setTextColor(getContext().getResources().getColor(R.color.text_gray));
              rbtnKsbpay.setEnabled(false);
          }
      }
    }

    @Override
    public void setConfig(PayConfig data) {
      if (data!=null){
         rgroupPay.setVisibility(View.VISIBLE);
         if (data.getWechatPayType()==1){
             rbtnWxpay.setVisibility(View.VISIBLE);
         }else {
             rbtnWxpay.setVisibility(View.GONE);
         }
          if (data.getAliPayType()==1){
              rbtnAlipay.setVisibility(View.VISIBLE);
          }else {
              rbtnAlipay.setVisibility(View.GONE);
          }
          if (data.getKsbPayType()==1){
              rbtnKsbpay.setVisibility(View.VISIBLE);
          }else {
              rbtnKsbpay.setVisibility(View.GONE);
          }
      }
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
