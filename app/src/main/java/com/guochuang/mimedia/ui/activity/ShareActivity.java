package com.guochuang.mimedia.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.tools.LogUtil;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.QrCode;
import com.guochuang.mimedia.mvp.presenter.SharePresenter;
import com.guochuang.mimedia.mvp.view.ShareView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.dialog.ShareDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ShareActivity extends MvpActivity<SharePresenter> implements ShareView {
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_copy_link)
    TextView tvCopyLink;
    @BindView(R.id.tv_share_friend)
    TextView tvShareFriend;

    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initViewAndData() {
        GlideImgManager.loadImage(this,App.getInstance().getUserInfo().getTwoDimensional(),ivQrcode);
    }

    @OnClick({R.id.iv_back, R.id.tv_copy_link, R.id.tv_share_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_copy_link:
                CommonUtil.copyMsg(this,App.getInstance().getUserInfo().getInviteUrl());
                showShortToast(R.string.copy_success);
                break;
            case R.id.tv_share_friend:
                String url=App.getInstance().getUserInfo().getTwoDimensional();
                if (!TextUtils.isEmpty(url))
                    dealBitmap();
                break;
        }
    }

    @Override
    public void setData(QrCode data) {
        closeLoadingDialog();
       if (data!=null){
           GlideImgManager.loadImage(this,data.getUrl(),ivQrcode);
       }
    }

    @Override
    public void setError(String msg) {
     closeLoadingDialog();
     showShortToast(msg);
    }
    private Bitmap getViewBitmap(View view) {
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }
    public void dealBitmap(){
        Bitmap bitmap= getViewBitmap(flContent);
        if (bitmap==null){
            showShortToast(R.string.cant_get_qrcode);
            return;
        }
        File file= CommonUtil.saveBitmap(bitmap,Constant.COMMON_PATH+File.separator+"qrcode.png");
        share(file);
    }
    public void share(File file){
        ShareDialog shareDialog=new ShareDialog(ShareActivity.this);
        shareDialog.setImagePath(file.getAbsolutePath());
        shareDialog.setOnShareResultListener(new ShareDialog.OnShareResultListener() {
            @Override
            public void onSuccess(String platform) {

            }

            @Override
            public void onError(String errMsg) {
                showShortToast(errMsg);
            }

            @Override
            public void onCancel() {

            }
        });
        shareDialog.show();
    }
}
