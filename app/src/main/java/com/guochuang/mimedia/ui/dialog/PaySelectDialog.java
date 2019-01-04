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
    String ksb;
    String acountKsb;
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
    }

    @Override
    public void show() {
        super.show();
        presenter.calValue(Double.parseDouble(money),Constant.CAL_TYPE_COIN);
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
          ksb= data.getCoinByMoney();
          acountKsb=data.getCoin();
          String title = String.format(getContext().getString(R.string.format_pay_amount), money, ksb);
          SpannableStringBuilder builder = new SpannableStringBuilder(title);
          int moneyIndex = title.indexOf(money);
          builder.setSpan(new TextAppearanceSpan(getContext(), R.style.span_yellow_style), moneyIndex, moneyIndex + money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          int ksbIndex = title.lastIndexOf(ksb);
          builder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.text_city_yellow)), ksbIndex, ksbIndex + ksb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          tvTitle.setText(builder);
          double ksbDouble = Double.parseDouble(ksb);
          double accountKsbDouble=0;
          if (!TextUtils.isEmpty(acountKsb)){
              accountKsbDouble = Double.parseDouble(acountKsb);
          }
          if (ksbDouble <= accountKsbDouble) {
              Drawable drawable=getContext().getResources().getDrawable(R.drawable.ic_ksbpay);
              drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
              rbtnKsbpay.setCompoundDrawables(drawable, null, rbtnKsbpay.getCompoundDrawables()[2], null);
              String myKsb = String.format(getContext().getString(R.string.format_ksb_pay), acountKsb);
              SpannableStringBuilder builder1 = new SpannableStringBuilder(myKsb);
              int accountIndex = myKsb.indexOf(acountKsb);
              builder1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.text_city_yellow)), accountIndex, accountIndex + acountKsb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              rbtnKsbpay.setText(builder1);
              rbtnKsbpay.setEnabled(true);
          } else {
              Drawable drawable=getContext().getResources().getDrawable(R.drawable.ic_ksbpay_gray);
              drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
              rbtnKsbpay.setCompoundDrawables(drawable, null, null, null);
              String myKsb = String.format(getContext().getString(R.string.format_ksb_pay), acountKsb);
              rbtnKsbpay.setText(myKsb);
              rbtnKsbpay.setTextColor(getContext().getResources().getColor(R.color.text_gray));
              rbtnKsbpay.setEnabled(false);
          }
      }
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
