package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.tools.PrefUtil;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/22.
 */

public class AddBidNodeDialog extends Dialog {

    @BindView(R.id.tv_has_pay)
    TextView tvHasPay;
    @BindView(R.id.et_bid_price)
    EditText etBidPrice;
    @BindView(R.id.tv_my_qc)
    TextView tvMyQc;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.btn_confirm)
    ImageButton btnConfirm;
    @BindView(R.id.iv_close)
    ImageView ivClose;


    public interface OnResultListener {
        void onResult(int money);
    }

    OnResultListener onResultListener;

    public AddBidNodeDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = View.inflate(context, R.layout.dialog_add_bid_node, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        tvMyQc.setText(PrefUtil.getInstance().getString(PrefUtil.MONEY,"0"));
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                dismiss();
                break;
            default:
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    @OnClick({R.id.btn_confirm, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
//
                String inputStr=etBidPrice.getText().toString().trim();
                if (TextUtils.isEmpty(inputStr)) {
                    Toast.makeText(getContext(), "输入金额不能为空", Toast.LENGTH_SHORT).show();
                }else if (!cbAgreement.isChecked()){
                    Toast.makeText(getContext(), "请先选择竞购协议", Toast.LENGTH_SHORT).show();
                }else {
                    int money=Integer.parseInt(inputStr);
                    if ((money<3000&&money>10000)||money%100!=0){
                        Toast.makeText(getContext(),"支付金额3000~10000，100的倍数",Toast.LENGTH_SHORT).show();
                    }else {
                        if (onResultListener!=null){
                            onResultListener.onResult(money);
                        }
                        dismiss();
                    }
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

}
