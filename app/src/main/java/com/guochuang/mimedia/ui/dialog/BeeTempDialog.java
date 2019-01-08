package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.sz.gcyh.KSHongBao.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/22.
 */

public class BeeTempDialog extends Dialog {

    public BeeTempDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_bee_temp, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
       dismiss();
    }
}
