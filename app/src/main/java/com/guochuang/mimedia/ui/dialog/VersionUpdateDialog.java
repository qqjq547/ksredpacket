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

public class VersionUpdateDialog extends Dialog {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.v_divide)
    View vDivide;
    @BindView(R.id.tv_update)
    TextView tvUpdate;


    public interface OnResultListener {
        void onRefuse();
        void onUpdate();
    }
    OnResultListener onResultListener;
    public VersionUpdateDialog(@NonNull Context context,String content,boolean isForce) {
        super(context, R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_version_update, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        tvContent.setText(content);
        if (isForce){
            vDivide.setVisibility(View.GONE);
            tvRefuse.setVisibility(View.GONE);
        }else {
            vDivide.setVisibility(View.VISIBLE);
            tvRefuse.setVisibility(View.VISIBLE);
        }
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @OnClick({R.id.tv_refuse, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refuse:
                dismiss();
                if (onResultListener!=null){
                    onResultListener.onRefuse();
                }
                break;
            case R.id.tv_update:
                if (onResultListener!=null){
                    onResultListener.onUpdate();
                }
                break;
        }
    }

}
