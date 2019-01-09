package com.guochuang.mimedia.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.presenter.IdentifyCustomPresenter;
import com.guochuang.mimedia.mvp.presenter.IdentifyPresenter;
import com.guochuang.mimedia.mvp.view.IdentifyCustomView;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.tools.identify.GenerateSign;
import com.megvii.faceidiol.sdk.manager.IDCardDetectListener;
import com.megvii.faceidiol.sdk.manager.IDCardManager;
import com.megvii.faceidiol.sdk.manager.IDCardResult;
import com.megvii.faceidiol.sdk.manager.UserDetectConfig;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class IdentifyCustomActivity extends MvpActivity<IdentifyCustomPresenter> implements IdentifyCustomView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_card_front)
    ImageView ivCardFront;
    @BindView(R.id.iv_card_back)
    ImageView ivCardBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    String frontPath,backPath;
    String name;
    String number;
    String frontUrl,backUrl;
    @Override
    protected IdentifyCustomPresenter createPresenter() {
        return new IdentifyCustomPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_identify_custom;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.identity_custom);
        setStatusbar(R.color.bg_red, false);
    }

    @OnClick({R.id.iv_back, R.id.iv_card_front,R.id.iv_card_back,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_card_front:
                new RxPermissions(IdentifyCustomActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            ImageSelector.builder()
                                    .useCamera(true)
                                    .setSingle(true)
                                    .start(IdentifyCustomActivity.this, Constant.REQUEST_CARD_FRONT);
                        }else {
                            showShortToast(R.string.get_pic_permission);
                        }
                    }
                });
                break;
            case R.id.iv_card_back:
                new RxPermissions(IdentifyCustomActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    ImageSelector.builder()
                                            .useCamera(true)
                                            .setSingle(true)
                                            .start(IdentifyCustomActivity.this, Constant.REQUEST_CARD_BACK);
                                }else {
                                    showShortToast(R.string.get_pic_permission);
                                }
                            }
                        });
                break;
            case R.id.tv_submit:
                name=etName.getText().toString().trim();
                number=etNumber.getText().toString().trim();
                if (TextUtils.isEmpty(frontPath)){
                    showShortToast(R.string.front_pic_cant_empty);
                }else if(TextUtils.isEmpty(backPath)){
                    showShortToast(R.string.back_pic_cant_empty);
                }else if(TextUtils.isEmpty(name)){
                    showShortToast(R.string.name_cant_empty);
                }else if(TextUtils.isEmpty(number)){
                    showShortToast(R.string.number_pic_cant_empty);
                }else {
                    showLoadingDialog(null);
                    mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_ID_CARD, new File(frontPath),true);
                }
                break;

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CARD_FRONT:
                    ArrayList<String> images1 = intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    GlideImgManager.loadImage(this,new File(images1.get(0)),ivCardFront);
                    break;
                case Constant.REQUEST_CARD_BACK:
                    ArrayList<String> images2 = intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    GlideImgManager.loadImage(this,new File(images2.get(0)),ivCardBack);
                    break;
            }
        }
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
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
    public void setUploadData(UploadFile data, boolean isFront) {
        if (isFront){
            frontUrl=data.getUrl();
            mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_ID_CARD, new File(backPath),false);
        }else {
            backUrl=data.getUrl();
            closeLoadingDialog();
//            mvpPresenter.userNameAuthAuth();
        }

    }


    @Override
    public void setUploadError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
