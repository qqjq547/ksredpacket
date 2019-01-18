package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.dialog.SelectAreaDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.cb_default)
    CheckBox cbDefault;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    int mode=Constant.MODE_ADDRESS_CHECK;
    SelectAreaDialog selectAreaDialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.received_address);
        mode=getIntent().getIntExtra(Constant.ADDRESS_MODE,Constant.MODE_ADDRESS_CHECK);
        if (mode==Constant.MODE_ADDRESS_CHECK){
            tvDelete.setVisibility(View.VISIBLE);
        }else {
            tvDelete.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_area, R.id.tv_delete,R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_area:
                if (selectAreaDialog==null) {
                    selectAreaDialog = new SelectAreaDialog(this);
                    selectAreaDialog.setOnSelectListener(new SelectAreaDialog.OnSelectListener() {
                        @Override
                        public void onResult(String province, String city, String area) {
                            if (TextUtils.equals(province,city)){
                                tvArea.setText(province+area);
                            }else {
                                tvArea.setText(province+city+area);
                            }
                        }

                    });
                }
                selectAreaDialog.show();
                break;
            case R.id.tv_delete:
                break;
            case R.id.tv_confirm:
                break;
        }
    }
}
