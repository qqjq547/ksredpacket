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
 * Created by Administrator on 2017-06-24 0024.
 */

public class RedbagTypeDialog extends Dialog {

    OnItemClickListener onOpenResultListener;

    public interface OnItemClickListener {
        void onRandom();
        void onPassword();
        void onLucky();
    }
    public RedbagTypeDialog(@NonNull Context context,OnItemClickListener onOpenResultListener) {
        super(context, R.style.dialog_bottom_full);
        this.onOpenResultListener=onOpenResultListener;
        View view = View.inflate(context, R.layout.dialog_redbag_type, null);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.lin_random, R.id.lin_password,R.id.lin_lucky, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_random:
                if (onOpenResultListener!=null){
                    onOpenResultListener.onRandom();
                }
                cancel();
                break;
            case R.id.lin_password:
                if (onOpenResultListener!=null){
                    onOpenResultListener.onPassword();
                }
                cancel();
                break;
            case R.id.lin_lucky:
                if (onOpenResultListener!=null){
                    onOpenResultListener.onLucky();
                }
                cancel();
                break;
            case R.id.tv_cancel:
                cancel();
                break;
        }
    }

}
