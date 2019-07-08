package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class RedTextDialog extends Dialog {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    public RedTextDialog(@NonNull Context context, String message) {
        super(context);
        View view = View.inflate(context, R.layout.dialog_red_text, null);
        ButterKnife.bind(this, view);setContentView(view);
        tvMessage.setText(message);
    }

    @Override
    public void show() {
        super.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isShowing()) {
                    dismiss();
                }

            }
        },2000);
    }
}
