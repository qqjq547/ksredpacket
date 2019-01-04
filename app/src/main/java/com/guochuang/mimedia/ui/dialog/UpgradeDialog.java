package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class UpgradeDialog extends Dialog {

    @BindView(R.id.pb_upgrade)
    ProgressBar pbUpgrade;

    public UpgradeDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_upgrade, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
    }
    public void setProgress(int progress){
        pbUpgrade.setProgress(progress);
    }
}
