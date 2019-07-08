package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.view.DragView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.Redbag;
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
    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    @BindView(R.id.drag_view)
    DragView mDragView;


    Redbag redbag;

    boolean mIsDrag;
    Context mContext;
    OnOpenResultListener onOpenResultListener;

    public interface OnOpenResultListener{
        void onOpenResult(String password);
    }


    public OpenRedbagDialog(@NonNull Context context) {
        super(context,R.style.dialog_bottom_full);
        mContext = context;
        View  view = View.inflate(context, R.layout.dialog_open_redbag, null);
        ButterKnife.bind(this, view);
        mDragView.setOnCheckLisenter(new DragView.OnCheckLisenter() {
            @Override
            public void checkSuccess() {
                onOpenResultListener.onOpenResult(etWord.getText().toString().trim());
                dismiss();
            }
        });

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
            //系统红包  做处理

            if(mIsDrag) {
                mLlRoot.setBackgroundResource(R.drawable.bg_drag_open_redbag);
                mDragView.setVisibility(View.VISIBLE);
                ivOpen.setVisibility(View.GONE);
                etWord.setVisibility(View.GONE);
            }




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


    @OnClick({R.id.iv_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_open:
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


    public void setDrag(boolean isDrag){
        mIsDrag = isDrag;
    }
}
