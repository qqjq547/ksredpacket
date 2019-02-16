package com.guochuang.mimedia.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.SetUpUser;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.SettingPresenter;
import com.guochuang.mimedia.mvp.view.SettingView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import rx.functions.Action1;


public class SettingActivity extends MvpActivity<SettingPresenter> implements SettingView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_contribution_value)
    TextView tvContributionValue;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.lin_avatar)
    LinearLayout linAvatar;
    @BindView(R.id.lin_nickname)
    LinearLayout linNickname;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_inviter)
    TextView tvInviter;
    @BindView(R.id.iv_inviter_arrow)
    ImageView ivInviterArrow;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.lin_feedback)
    LinearLayout linFeedback;
    UserInfo userInfo;
    String path;
    String avatarPath;


    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.setting);
        userInfo = App.getInstance().getUserInfo();
        if (userInfo!=null){
            GlideImgManager.loadCircleImage(this, userInfo.getAvatar(), ivAvatar);
            tvNickname.setText(userInfo.getNickName());
            tvRecommend.setText(userInfo.getInviteCode());
        }
        tvVersion.setText(CommonUtil.getVersionName(this));
        showLoadingDialog(null);
        mvpPresenter.getSetupInfo();
    }

    @OnClick({R.id.iv_back, R.id.lin_avatar, R.id.lin_nickname, R.id.tv_copy, R.id.lin_inviter, R.id.lin_feedback, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();

                break;
            case R.id.lin_avatar:
                RxPermissions rxPermissions = new RxPermissions(SettingActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            ImageSelector.builder()
                                    .useCamera(true)
                                    .setSingle(true)
                                    .setCrop(true)
                                    .setViewImage(true)
                                    .start(SettingActivity.this, Constant.REQUEST_PICK_PICTURE);
                        } else {
                            showShortToast(R.string.get_pic_permission);
                        }
                    }
                });
                break;
            case R.id.lin_nickname:
                startActivityForResult(new Intent(this, SetNickNameActivity.class), Constant.REQUEST_SET_NICKNAME);
                break;
            case R.id.lin_inviter:
                if (ivInviterArrow.getVisibility() == View.VISIBLE) {
                    startActivityForResult(new Intent(this, SetInviterActivity.class), Constant.REQUEST_SET_INVITER);
                }
                break;
            case R.id.tv_copy:
                CommonUtil.copyMsg(this, tvRecommend.getText().toString().trim());
                showShortToast(R.string.copy_success);
                break;
            case R.id.lin_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.tv_exit:
                new DialogBuilder(this)
                        .setMessage(R.string.ensure_exit_account)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLoadingDialog(null);
                                mvpPresenter.userAccoutLogout();
                            }
                        })
                        .create().show();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_PICK_PICTURE:
                    ArrayList<String> images = intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    path = images.get(0);
                    showLoadingDialog(null);
                    mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_AVATAR, new File(path));
                    break;
                case Constant.REQUEST_SET_NICKNAME:
                    String nickName = intent.getStringExtra(Constant.NICKNAME);
                    tvNickname.setText(nickName);
                    break;
                case Constant.REQUEST_SET_INVITER:
                    String inviter = intent.getStringExtra(Constant.INVITER);
                    tvInviter.setText(inviter);
                    tvInviter.setVisibility(View.VISIBLE);
                    ivInviterArrow.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void getSetupInfo(SetUpUser data) {
        closeLoadingDialog();
        tvContributionValue.setText(String.valueOf(data.getContribution()));
        GlideImgManager.loadCircleImage(this, data.getAvatar(), ivAvatar);
        tvNickname.setText(data.getNickName());
        tvRecommend.setText(data.getInviteCode());
        if (TextUtils.isEmpty(data.getMyInviter())) {
            ivInviterArrow.setVisibility(View.VISIBLE);
            tvInviter.setVisibility(View.GONE);
        } else {
            ivInviterArrow.setVisibility(View.GONE);
            tvInviter.setVisibility(View.VISIBLE);
            tvInviter.setText(data.getMyInviter());
        }

    }

    @Override
    public void setData(UploadFile data) {
        closeLoadingDialog();
        avatarPath = data.getUrl();
        showLoadingDialog(null);
        mvpPresenter.setAvatar(tvNickname.getText().toString().trim(), avatarPath);
    }

    @Override
    public void setUpdateAvatar(Boolean data) {
        closeLoadingDialog();
        userInfo.setAvatar(avatarPath);
        App.getInstance().setUserInfo(userInfo);
        GlideImgManager.loadCircleImage(this, userInfo.getAvatar(), ivAvatar);
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void getLogout(String data) {
        closeLoadingDialog();
        App.getInstance().forceLogin();
    }

    @Override
    public void getLogoutError(String data) {
        closeLoadingDialog();
        showShortToast(data);
    }
}
