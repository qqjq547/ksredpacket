package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class RainNoticeDialog extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_start)
    TextView tvStart;

    OnResultListener onResultListener;
    public interface OnResultListener{
        void onCancel();
        void onStart();
    }

    public RainNoticeDialog(@NonNull Context context,String title,String message) {
        super(context, R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_rain_notice, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        tvTitle.setText(title);
        tvSummary.setText(message);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @OnClick({R.id.tv_cancel, R.id.tv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                if (onResultListener!=null)
                    onResultListener.onCancel();
                break;
            case R.id.tv_start:
                dismiss();
                if (onResultListener!=null)
                    onResultListener.onStart();
                break;
        }
    }
}
