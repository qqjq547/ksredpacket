package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class BeeNestDialog extends Dialog {

    @BindView(R.id.tv_this_ad)
    TextView tvThisAd;
    @BindView(R.id.tv_other_ad)
    TextView tvOtherAd;
    @BindView(R.id.tv_report)
    TextView tvReport;


    OnItemClikListener onItemClikListener;
    public interface OnItemClikListener{
        void onBidThis();
        void onBidOther();
        void onReport();
    }
    public BeeNestDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_beenest_more, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public BeeNestDialog setOnItemClikListener(OnItemClikListener onItemClikListener) {
        this.onItemClikListener = onItemClikListener;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({
            R.id.tv_this_ad,
            R.id.tv_other_ad,
            R.id.tv_report,
            R.id.tv_close,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_this_ad:
                onItemClikListener.onBidThis();
                break;
            case R.id.tv_other_ad:
                onItemClikListener.onBidOther();
                break;
            case R.id.tv_report:
                onItemClikListener.onReport();
                break;
            case R.id.tv_close:
                break;
        }
        dismiss();
    }



}
