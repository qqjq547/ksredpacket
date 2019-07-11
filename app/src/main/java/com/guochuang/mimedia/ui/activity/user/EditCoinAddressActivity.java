package com.guochuang.mimedia.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.EditCoinAddressPresenter;
import com.guochuang.mimedia.mvp.view.CoinAddress;
import com.guochuang.mimedia.mvp.view.EditCoinAddressView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.common.MyCaptureActivity;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class EditCoinAddressActivity extends MvpActivity<EditCoinAddressPresenter> implements EditCoinAddressView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_add)
    Button btnAdd;
    boolean isAdd=true;
    CoinAddress coinAddress;

    @Override
    protected EditCoinAddressPresenter createPresenter() {
        return new EditCoinAddressPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_coin_address;
    }

    @Override
    public void initViewAndData() {
        isAdd=getIntent().getBooleanExtra(Constant.ISADD,false);
        if (isAdd){
            tvTitle.setText(R.string.add_out_address);
            btnDelete.setVisibility(View.GONE);
        }else {
            tvTitle.setText(R.string.edit_out_address);
            coinAddress=(CoinAddress)getIntent().getSerializableExtra(Constant.COIN_ADDRESS);
            etName.setText(coinAddress.getAlias());
            etAddress.setText(coinAddress.getChainAddress());
            etAddress.setEnabled(false);
        }

    }
    @OnClick({R.id.iv_back, R.id.iv_scan, R.id.btn_delete, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_scan:
                new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivityForResult(new Intent(EditCoinAddressActivity.this,MyCaptureActivity.class),Constant.REQUEST_CAPTURE);
                        } else {
                            showShortToast(R.string.get_camera_permission);
                        }
                    }
                });
                break;
            case R.id.btn_delete:
                if (coinAddress!=null) {
                    showLoadingDialog(null);
                    mvpPresenter.delAddress(coinAddress.getAddressBookId(),coinAddress.getChainAddress());
                }
                break;
            case R.id.btn_add:
                String alias=etName.getText().toString().trim();
                String address=etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(alias)){
                    showShortToast(R.string.input_address_area_name);
                }else if(TextUtils.isEmpty(address)){
                    showShortToast(R.string.input_out_address);
                }else {
                    if (isAdd){
                        showLoadingDialog(null);
                        mvpPresenter.addAddress(Constant.CHAIN_TYPE_ETH,address,alias,"");
                    }else {
                        if (coinAddress!=null) {
                            showLoadingDialog(null);
                            mvpPresenter.editAddress(coinAddress.getAddressBookId(), address, alias, "");
                        }
                    }
                }

                break;
        }
    }

    @Override
    public void setAddData(Boolean data) {
        closeLoadingDialog();
       setResult(RESULT_OK,getIntent());
       finish();
    }

    @Override
    public void setDelData(Boolean data) {
        closeLoadingDialog();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setEditData(Boolean data) {
        closeLoadingDialog();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==Constant.REQUEST_CAPTURE){
                String address=data.getStringExtra(Constant.ADDRESS);
                etAddress.setText(address);
            }
        }
    }
}
