package com.guochuang.mimedia.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.presenter.IdentifyPresenter;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.identify.GenerateSign;
import com.megvii.faceidiol.sdk.manager.IDCardDetectListener;
import com.megvii.faceidiol.sdk.manager.IDCardManager;
import com.megvii.faceidiol.sdk.manager.IDCardResult;
import com.megvii.faceidiol.sdk.manager.UserDetectConfig;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class IdentifyActivity extends MvpActivity<IdentifyPresenter> implements IdentifyView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_card)
    ImageView ivCard;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_name_and_number)
    LinearLayout llNameAndNumber;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_errmsg)
    TextView tvErrmsg;


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

    File file;
    IDCardResult idCardResult;


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
        String desp = getIntent().getStringExtra(Constant.DESCRIPTION);
        tvErrmsg.setText(desp);
        setStatusbar(R.color.bg_red, false);
//        tvSubmit.setEnabled(false);

        GeneralUtil.bankcardAddSpace(etInputBanckCard);
        //获取图片验证码
        showLoadingDialog(null);
        mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
    }

    @OnClick({R.id.iv_back, R.id.iv_card, R.id.lin_content, R.id.tv_submit, R.id.tv_identity_custom, R.id.iv_register_ima_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_card:
            case R.id.lin_content:
                RxPermissions rxPermissions = new RxPermissions(IdentifyActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            identifyInit();
                        } else {
                            showShortToast(R.string.get_pic_permission);
                        }
                    }
                });
                break;
            case R.id.tv_submit:
//                if (file != null) {
//                    showLoadingDialog(null);
//                    mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_ID_CARD, file);
//                }

                String realName = etInputName.getText().toString().trim();
                String idCard = etInputIdcard.getText().toString().trim();
                String cardNumber = GeneralUtil.removeAllSpace(etInputBanckCard.getText().toString().trim());
                String captchastr = etInputVerify.getText().toString().trim();

                if (TextUtils.isEmpty(realName)) {
                    showShortToast(R.string.pelase_input_name);
                    return;
                }
                if (TextUtils.isEmpty(idCard)) {
                    showShortToast(R.string.pelase_input_idcard);
                    return;
                }
                if (TextUtils.isEmpty(cardNumber)) {
                    showShortToast(R.string.please_banck_card);
                    return;
                }

                if (TextUtils.isEmpty(captchastr)) {
                    showShortToast(R.string.pelase_input_verify);
                    return;
                }

                showLoadingDialog(null);
                mvpPresenter.realName(realName, idCard, cardNumber, captcha.getUuid(), captchastr);

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

    private void identifyInit() {
        long currtTime = System.currentTimeMillis() / 1000;
        long expireTime = System.currentTimeMillis() / 1000 + 60 * 60 * 24;
        UserDetectConfig config = new UserDetectConfig();
        config.setCaptureImage(1);
        config.setScreenDirection(1);
        showLoadingDialog(null);
        IDCardManager.getInstance().init(this, GenerateSign.appSign("k-mD2Ve9iZkYahVsBEYW3CLLzH0IUgvu", "3sDQsDPE0-mtM7kfeDAXpVMa24N2utVg", currtTime, expireTime),
                "hmac_sha1", config, new IDCardManager.InitCallBack() {
                    @Override
                    public void initSuccess(String bizToken) {
                        closeLoadingDialog();
                        IDCardManager.getInstance().setIdCardDetectListener(new IDCardDetectListener() {
                            @Override
                            public void onIdCardDetectFinish(IDCardResult idCardResult) {
                                tvSubmit.setEnabled(false);
                                if (idCardResult == null || idCardResult.getResultCode() == 6000) {
                                    return;
                                }
                                Bitmap faceBmp = BitmapFactory.decodeByteArray(idCardResult.getIdCardInfo().getImageFrontside(), 0,
                                        idCardResult.getIdCardInfo().getImageFrontside().length);
                                ivCard.setImageBitmap(faceBmp);
                                file = CommonUtil.saveBitmap(faceBmp, Constant.COMMON_PATH + File.separator + System.currentTimeMillis() + ".png");
                                IdentifyActivity.this.idCardResult = idCardResult;
                                if (idCardResult == null) {
                                    return;
                                }
                                if (idCardResult.getResultCode() != 1001) {
                                    if (idCardResult.getIdCardInfo().getFrontsideLegality().getIdPhoto() < idCardResult.getIdCardInfo().getFrontsideLegality().getIdPhotoThreshold()) {
                                        showShortToast(getResources().getString(R.string.identify_formal_image));
                                        return;
                                    }
                                    if (idCardResult.getIdCardInfo().getName().getQuality() < 0.6) {
                                        showShortToast(getResources().getString(R.string.identify_no_name));
                                        return;
                                    }
                                    if (idCardResult.getIdCardInfo().getIdcardNumber().getQuality() < 0.6) {
                                        showShortToast(getResources().getString(R.string.identify_no_id_card));
                                        return;
                                    }
                                } else {
                                    llNameAndNumber.setVisibility(View.VISIBLE);
                                    if (idCardResult.getIdCardInfo().getFrontsideLegality().getIdPhoto() < idCardResult.getIdCardInfo().getFrontsideLegality().getIdPhotoThreshold()) {
                                        showShortToast(getResources().getString(R.string.identify_formal_image));
                                        return;
                                    }
                                    if (idCardResult.getIdCardInfo().getName().getQuality() < 0.6) {
                                        showShortToast(getResources().getString(R.string.identify_no_name));
                                        return;
                                    } else {
                                        etName.setText(idCardResult.getIdCardInfo().getName().getText());
                                    }
                                    if (idCardResult.getIdCardInfo().getIdcardNumber().getQuality() < 0.6) {
                                        showShortToast(getResources().getString(R.string.identify_no_id_card));
                                        return;
                                    } else {
                                        etNumber.setText(idCardResult.getIdCardInfo().getIdcardNumber().getText());
                                    }
                                    tvSubmit.setEnabled(true);
                                }
                            }
                        });
                        IDCardManager.getInstance().startDetect(IdentifyActivity.this, bizToken, "");
                    }

                    @Override
                    public void initFailed(int resultCode, String resultMessage) {
                        tvSubmit.setEnabled(false);
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == Constant.INTO_IDCARDSCAN_PAGE && resultCode == RESULT_OK) {
//                String path = intent.getStringExtra("path");
//                Bitmap idcardBmp = BitmapFactory.decodeFile(path);
//                if (idcardBmp == null) {
//                    showShortToast(getResources().getString(R.string.identify_no_image));
//                    return;
//                }
//                ivCard.setImageBitmap(idcardBmp);
//                showLoadingDialog(null);
//                file = new File(path);
////                mvpPresenter.ocrIdCard(file);
//            }
//        }
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
    public void setUploadData(UploadFile data) {
        closeLoadingDialog();
        mvpPresenter.userNameAuthAuth(
                etName.getText().toString(),
                etNumber.getText().toString(),
                data.getUrl(),
                GsonUtil.GsonString(idCardResult.getIdCardInfo().getFrontsideLegality())
        );
    }

    @Override
    public void setUploadError(String msg) {
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
