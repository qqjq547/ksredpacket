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

public class RainResultDialog extends Dialog {



    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_ksb)
    TextView tvKsb;
    @BindView(R.id.tv_sure)
    TextView tvSure;


    OnResultListener onResultListener;
    public interface OnResultListener {
        void onSure();
    }

    public RainResultDialog(@NonNull Context context, String message, String ksbMsg) {
        super(context, R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_rain_result, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        tvMessage.setText(message);
        tvKsb.setText(ksbMsg);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }
    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        dismiss();
        if (onResultListener!=null){
            onResultListener.onSure();
        }
    }

}
