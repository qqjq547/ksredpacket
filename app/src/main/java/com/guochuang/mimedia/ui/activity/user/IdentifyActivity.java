package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.tools.GeneralUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.IdentifyPresenter;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class IdentifyActivity extends MvpActivity<IdentifyPresenter> implements IdentifyView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_name_and_number)
    LinearLayout llNameAndNumber;


    @BindView(R.id.iv_register_ima_verify)
    ImageView ivRegisterImaVerify;
    @BindView(R.id.et_input_ima_verify)
    EditText etInputVerify;
    @BindView(R.id.et_input_banck_card)
    EditText etInputBanckCard;
    @BindView(R.id.et_input_idcard)
    EditText etInputIdcard;
    @BindView(R.id.et_input_name)
    EditText etInputName;


    Captcha captcha;

    @Override
    protected IdentifyPresenter createPresenter() {
        return new IdentifyPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_identify;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.identification);

        GeneralUtil.bankcardAddSpace(etInputBanckCard);
        //获取图片验证码
        showLoadingDialog(null);
        mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.tv_identity_custom, R.id.iv_register_ima_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_submit:
                String realName = etInputName.getText().toString().trim();
                String idCard = etInputIdcard.getText().toString().trim();
//                String cardNumber = GeneralUtil.removeAllSpace(etInputBanckCard.getText().toString().trim());
                String captchastr = etInputVerify.getText().toString().trim();

                if (TextUtils.isEmpty(realName)) {
                    showShortToast(R.string.pelase_input_name);
                    return;
                }
                if (TextUtils.isEmpty(idCard)) {
                    showShortToast(R.string.pelase_input_idcard);
                    return;
                }
//                if (TextUtils.isEmpty(cardNumber)) {
//                    showShortToast(R.string.please_banck_card);
//                    return;
//                }

                if (TextUtils.isEmpty(captchastr)) {
                    showShortToast(R.string.pelase_input_verify);
                    return;
                }

                showLoadingDialog(null);
                mvpPresenter.realName(realName, idCard, captcha.getUuid(), captchastr);

                break;
            case R.id.tv_identity_custom:
                Intent intent = getIntent();
                intent.setClass(this, IdentifyCustomActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.iv_register_ima_verify:
                showLoadingDialog(null);
                mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
                break;
        }
    }


    @Override
    public void setData(String data) {
        closeLoadingDialog();
        getPref().setInt(PrefUtil.IDENTITY, 1);
        showShortToast(getResources().getString(R.string.success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }


    @Override
    public void setVerifyError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setVerifyData(Captcha data) {
        closeLoadingDialog();
        this.captcha = data;
        GlideImgManager.loadCornerImage(this, data.getUrl(), ivRegisterImaVerify, 8);
    }

}
