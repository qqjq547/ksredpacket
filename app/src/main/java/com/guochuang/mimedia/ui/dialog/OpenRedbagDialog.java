package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.tools.LogUtil;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class OpenRedbagDialog extends Dialog {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.et_word)
    EditText etWord;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    Redbag redbag;

    OnOpenResultListener onOpenResultListener;
    public interface OnOpenResultListener{
        void onOpenResult(String password);
    }


    public OpenRedbagDialog(@NonNull Context context) {
        super(context,R.style.dialog_bottom_full);
        View view = View.inflate(context, R.layout.dialog_open_redbag, null);
        ButterKnife.bind(this, view);
        setContentView(view);
    }

    public void setOnOpenResultListener(OnOpenResultListener onOpenResultListener) {
        this.onOpenResultListener = onOpenResultListener;
    }

    public void setRedbag(Redbag redbag){
        this.redbag=redbag;
        etWord.setText("");
        GlideImgManager.loadCircleImage(getContext(),redbag.getAvatar(),ivAvatar,1,Color.WHITE);
        tvName.setText(redbag.getNickName());
        if (redbag.getRoleType().equals(Constant.ROLETYPE_SYSTEM)){
            tvSummary.setText(redbag.getBestWishes());
            etWord.setVisibility(View.INVISIBLE);
        }else {
            if (redbag.getType().equals(Constant.RED_PACKET_TYPE_PASSWORD)){
                if (TextUtils.isEmpty(redbag.getPassword())){
                    tvSummary.setText(redbag.getBestWishes());
                }else {
                    tvSummary.setText(String.format(getContext().getString(R.string.format_password),redbag.getPassword()));
                }
                etWord.setVisibility(View.VISIBLE);
            }else {
                tvSummary.setText(redbag.getBestWishes());
                etWord.setVisibility(View.INVISIBLE);
            }
        }
    }


    @OnClick({R.id.tv_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open:
                String password=etWord.getText().toString().trim();
                if (redbag.getRoleType().equals(Constant.ROLETYPE_PERSON)&&redbag.getType().equals(Constant.RED_PACKET_TYPE_PASSWORD)){
                    if (TextUtils.isEmpty(etWord.getText().toString().trim())){
                        Toast.makeText(getContext(),getContext().getString(R.string.password_not_empty),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(redbag.getPassword())&&!TextUtils.equals(redbag.getPassword(),password)){
                        Toast.makeText(getContext(),getContext().getString(R.string.pls_input_right_pwd),Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (onOpenResultListener!=null)
                onOpenResultListener.onOpenResult(password);
                dismiss();
                break;
        }
    }
}
