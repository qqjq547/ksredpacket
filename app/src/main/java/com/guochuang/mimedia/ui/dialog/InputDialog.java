package com.guochuang.mimedia.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/22.
 */

public class InputDialog extends Dialog {
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_publish)
    TextView tvPublish;

    String replyContent;

    public interface OnResultListener{
        void onReplyText(String content);
    }
    OnResultListener onResultListener;

    public InputDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = View.inflate(context, R.layout.dialog_input, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }
    public void clearText(){
        etInput.setText("");
    }
    public void setHint(String hint){
        etInput.setHint(hint);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    dismiss();
                    break;
                default:
                    break;
            }
        return super.dispatchKeyEvent(event);
    }

    @OnClick({R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_publish:
                replyContent=etInput.getText().toString().trim();
                if (TextUtils.isEmpty(replyContent)){
                    Toast.makeText(getContext(),R.string.reply_content_not_empty,Toast.LENGTH_SHORT).show();
                }else {
                    if (onResultListener!=null)
                        onResultListener.onReplyText(replyContent);
                }
                break;
        }
    }
}
