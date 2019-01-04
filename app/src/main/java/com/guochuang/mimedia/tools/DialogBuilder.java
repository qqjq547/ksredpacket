package com.guochuang.mimedia.tools;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBuilder {

    Context context;
    String title;
    String message;
    boolean cancelable;
    String positiveText;
    View.OnClickListener positiveListener;
    String negativeText;
    View.OnClickListener negativeListener;


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_negative)
    TextView tvNegative;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.tv_positive)
    TextView tvPositive;

    Dialog dialog;

    public DialogBuilder(Context context) {
        this.context = context;
    }
    public DialogBuilder setTitle(int titleResId) {
        this.title = context.getString(titleResId);
        return this;
    }
    public DialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    public DialogBuilder setMessage(int messageResId) {
        this.message = context.getString(messageResId);
        return this;
    }
    public DialogBuilder setMessage(String message) {
        this.message = message;
        return this;
    }
    public DialogBuilder setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public DialogBuilder setPositiveButton(String text, View.OnClickListener listener) {
        this.positiveText = text;
        this.positiveListener = listener;
        return this;
    }

    public DialogBuilder setPositiveButton(int textId, View.OnClickListener listener) {
        this.positiveText = context.getString(textId);
        this.positiveListener = listener;
        return this;
    }

    public DialogBuilder setNegativeButton(String text, View.OnClickListener listener) {
        this.negativeText = text;
        this.negativeListener = listener;
        return this;
    }

    public DialogBuilder setNegativeButton(int textId, View.OnClickListener listener) {
        this.negativeText = context.getString(textId);
        this.negativeListener = listener;
        return this;
    }

    public Dialog create() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
        ButterKnife.bind(this,contentView);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        tvMessage.setText(message);
        if (TextUtils.isEmpty(negativeText)) {
            tvNegative.setVisibility(View.GONE);
        } else {
            tvNegative.setVisibility(View.VISIBLE);
            tvNegative.setText(negativeText);
        }

        if (TextUtils.isEmpty(positiveText)) {
            tvPositive.setVisibility(View.GONE);
        } else {
            tvPositive.setVisibility(View.VISIBLE);
            tvPositive.setText(positiveText);
        }
        if (TextUtils.isEmpty(negativeText)&&TextUtils.isEmpty(positiveText)){
            vLine.setVisibility(View.VISIBLE);
        }else {
            vLine.setVisibility(View.GONE);
        }
        dialog = new Dialog(context);
        dialog.setContentView(contentView);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    @OnClick({R.id.tv_negative, R.id.tv_positive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_negative:
                dialog.cancel();
                if (negativeListener != null) {
                    negativeListener.onClick(view);
                }
                break;
            case R.id.tv_positive:
                dialog.cancel();
                if (positiveListener != null) {
                    positiveListener.onClick(view);
                }
                break;
        }
    }
}