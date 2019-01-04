package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class CityBenefitExplainDialog extends Dialog {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    public CityBenefitExplainDialog(@NonNull Context context, String message) {
        super(context,R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_city_benefit_explain, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        tvMessage.setText(message);
    }


    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        dismiss();
    }
}
