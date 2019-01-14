package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.activity.user.IdentifyActivity;
import com.guochuang.mimedia.ui.activity.user.TradePwdActivity;
import com.guochuang.mimedia.ui.adapter.KeyBoardAdapter;
import com.guochuang.mimedia.view.CodeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassDialog extends Dialog {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.alter_login_pay_pass_cv)
    CodeView alterLoginPayPassCv;
    @BindView(R.id.gv_keybord)
    GridView gvKeybord;

    private ArrayList<Map<String, String>> valueList;
    private Context context;
    private OnPassDialogListener onPassDialogListener;
    private boolean needIdentity = false;

    public PassDialog(@NonNull Context context, final OnPassDialogListener onPassDialogListener) {
        super(context, R.style.dialog_bottom);
        this.onPassDialogListener = onPassDialogListener;
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_virtual_keyboard, null);
        ButterKnife.bind(this, view);
        setContentView(view);

        alterLoginPayPassCv.setListener(new CodeView.Listener() {
            @Override
            public void onValueChanged(String value) {

            }

            @Override
            public void onComplete(String value) {
                PassDialog.this.dismiss();
                onPassDialogListener.onNumFull(value);
            }
        });

        valueList = new ArrayList<>();
        setView();
        gvKeybord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < 11 && i != 9) {    //点击0~9按钮
                    alterLoginPayPassCv.input(valueList.get(i).get("name"));
                } else {
                    if (i == 11) {      //点击退格键
                        alterLoginPayPassCv.delete();
                    }
                }
            }
        });

        //默认设置
        setCanceledOnTouchOutside(true);
        Window window = PassDialog.this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    public Dialog setBackVisible(boolean visible) {
        if (visible) {
            imgBack.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public void setNeedIdentity(boolean needIdentity) {
        this.needIdentity = needIdentity;
    }


    @Override
    public void show() {
        if (needIdentity) {
            if (PrefUtil.getInstance().getInt(PrefUtil.IDENTITY, 0) == 0) {
                ((MvpActivity) context).showShortToast(context.getResources().getString(R.string.pls_set_identify));
                context.startActivity(new Intent(context, IdentifyActivity.class));
                return;
            }
        }
        if (PrefUtil.getInstance().getInt(PrefUtil.SAFECODE, 0) == 0) {
            ((MvpActivity) context).showShortToast(context.getResources().getString(R.string.pls_set_safe_code));
            context.startActivity(new Intent(context, TradePwdActivity.class));
            return;
        }
        super.show();
    }

    private void setView() {

        /* 初始化按钮上应该显示的数字 */
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }

        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gvKeybord.setAdapter(keyBoardAdapter);
    }

    @OnClick({R.id.imgBack, R.id.alter_login_pay_pass_forget_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                dismiss();
                onPassDialogListener.close();
                break;
            case R.id.alter_login_pay_pass_forget_tv:
                onPassDialogListener.go();
                context.startActivity(new Intent(context, TradePwdActivity.class));
                break;
        }
    }

    @Override
    protected void onStop() {
        alterLoginPayPassCv.setCode("");
        super.onStop();
    }

    public interface OnPassDialogListener {
        void close();

        void go();

        void onNumFull(String code);
    }
}
