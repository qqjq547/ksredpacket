package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.BenefitType;
import com.guochuang.mimedia.ui.adapter.CityBenefitAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class CityPreBuyDialog extends Dialog {

    @BindView(R.id.et_bid_price)
    EditText etBidPrice;
    @BindView(R.id.tv_my_qc)
    TextView tvMyQc;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;

    OnConfirmListener listener;
    public interface OnConfirmListener {
        void onConfirm(String price);
    }

    public CityPreBuyDialog(@NonNull Context context, String myQc, final OnConfirmListener onCheckListener) {
        super(context, R.style.dialog_bottom_full);
        tvMyQc.setText(myQc);
        listener=onCheckListener;

    }
    @OnClick(R.id.tv_ensure)
    public void onViewClicked() {
        dismiss();
        if (listener!=null){
            listener.onConfirm(etBidPrice.getText().toString().trim());
        }
    }


}
