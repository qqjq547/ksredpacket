package com.guochuang.mimedia.ui.activity.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.presenter.AddAddressPresenter;
import com.guochuang.mimedia.mvp.view.AddAddressView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.ui.dialog.SelectAreaDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAddressActivity extends MvpActivity<AddAddressPresenter> implements AddAddressView {
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
    SelectAreaDialog selectAreaDialog;
    String name;
    String mobile;
    String province;
    String city;
    String district;
    String address;
    String userAddressUuid;

    @Override
    protected AddAddressPresenter createPresenter() {
        return new AddAddressPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.received_address);
        Address address =(Address)getIntent().getSerializableExtra(Constant.ADDRESS);
        if (address!=null){
            userAddressUuid=address.getUuid();
            etName.setText(address.getTrackName());
            etMobile.setText(address.getTrackMobile());
            province=address.getProvince();
            city=address.getCity();
            district=address.getDistrict();
            if (TextUtils.equals(province,city)){
                tvArea.setText(province+district);
            }else {
                tvArea.setText(province+city+district);
            }
            etAddress.setText(address.getAddress());
        }
        if (TextUtils.isEmpty(userAddressUuid)){
            tvDelete.setVisibility(View.GONE);
        }else {
            tvDelete.setVisibility(View.VISIBLE);
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
                            AddAddressActivity.this.province=province;
                            AddAddressActivity.this.city=city;
                            district=area;
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
                new DialogBuilder(this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.ensure_delete_address)
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLoadingDialog(null);
                                mvpPresenter.delAddress(userAddressUuid);
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .create().show();
                break;
            case R.id.tv_confirm:
                name=etName.getText().toString().trim();
                mobile=etMobile.getText().toString().trim();
                address=etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    showShortToast(R.string.name_not_empty);
                }else if(TextUtils.isEmpty(mobile)){
                    showShortToast(R.string.mobile_not_empty);
                }else if (mobile.length() < 11) {
                    showShortToast(getResources().getString(R.string.input_phone_error));
                }else if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(district)){
                    showShortToast(R.string.address_not_empty);
                }else if(TextUtils.isEmpty(address)){
                    showShortToast(R.string.address_detail_not_empty);
                }else {
                    showLoadingDialog(null);
                    if (TextUtils.isEmpty(userAddressUuid)){
                        mvpPresenter.addAddress(name,mobile,province,city,district,address,0);
                    }else {
                        mvpPresenter.updateAddress(userAddressUuid,name,mobile,province,city,district,address);
                    }
                }
                break;
        }
    }

    @Override
    public void setData(Boolean data) {
         closeLoadingDialog();
         showShortToast(R.string.add_address_success);
         setResult(RESULT_OK,getIntent());
         finish();
    }

    @Override
    public void setDelData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.del_address_success);
        setResult(RESULT_OK,getIntent());
        finish();
    }
    @Override
    public void setUpdateData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.update_address_success);
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setError(String msg) {
       closeLoadingDialog();
       showShortToast(msg);
    }
}
