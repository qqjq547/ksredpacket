package com.guochuang.mimedia.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PayCode;
import com.guochuang.mimedia.mvp.presenter.MyPayCodePresenter;
import com.guochuang.mimedia.mvp.view.MyPayCodeView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.common.KsbPayActivity;
import com.guochuang.mimedia.ui.activity.common.MyCaptureActivity;
import com.guochuang.mimedia.view.SquareImageView;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Line;
import rx.functions.Action1;

public class MyPayCodeActivity extends MvpActivity<MyPayCodePresenter> implements MyPayCodeView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_code)
    SquareImageView ivCode;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_identify)
    TextView tvIdentify;

    @BindView(R.id.lin_print)
    LinearLayout linPrint;
    @BindView(R.id.iv_frame_code)
    ImageView ivFrameCode;
    @BindView(R.id.tv_frame_name)
    TextView tvFrameName;

    @Override
    protected MyPayCodePresenter createPresenter() {
        return new MyPayCodePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_pay_code;
    }

    @Override
    public void initViewAndData() {
       setStatusbar(R.color.bg_pay_code,false);
       tvTitle.setText(R.string.pay_code_title);
       ivImage.setImageResource(R.drawable.ic_scan_white);
       showLoadingDialog(null);
       mvpPresenter.queryQrcode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPref().getInt(PrefUtil.IDENTITY,0)==0){
            tvRecord.setVisibility(View.INVISIBLE);
            tvSave.setVisibility(View.INVISIBLE);
            tvIdentify.setVisibility(View.VISIBLE);
        }else {
            tvRecord.setVisibility(View.VISIBLE);
            tvSave.setVisibility(View.VISIBLE);
            tvIdentify.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_image, R.id.iv_code, R.id.tv_save, R.id.tv_record,R.id.tv_identify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image:
                new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivity(new Intent(MyPayCodeActivity.this, MyCaptureActivity.class));
                        }else {
                            showShortToast(R.string.get_camera_permission);
                        }
                    }
                });
                break;
            case R.id.iv_code:
                break;
            case R.id.tv_save:
                RxPermissions rxPermissions=new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Bitmap bitmap=getViewBitmap(linPrint);
                            String filePath=Constant.COMMON_PATH +File.separator+ System.currentTimeMillis() + ".png";
                            CommonUtil.saveBitmap(bitmap,filePath);
                            showShortToast(R.string.save_image_success);
                            Uri uri = Uri.fromFile(new File(filePath));
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        }else {
                            showShortToast(R.string.get_camera_permission);
                        }
                    }
                });

                break;
            case R.id.tv_record:
                startActivity(new Intent(this,MyKsbDetailsActivity.class).putExtra(Constant.DEFAULT_CODE,Constant.KSB_CODE_PAYMENT));
                break;
            case R.id.tv_identify:
                startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                break;
        }
    }
    private Bitmap getViewBitmap(View view) {
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }

    @Override
    public void setData(PayCode data) {
        closeLoadingDialog();
        if (data!=null){
            GlideImgManager.loadImage(this,data.getQrcode(),ivCode);
            GlideImgManager.loadImage(this,data.getQrcode(),ivFrameCode);
            tvFrameName.setText(data.getNickName()+" "+String.format(getString(R.string.format_kuahao),data.getRealName()));
        }
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
            switch (requestCode){
                case Constant.REFRESH:
                    showLoadingDialog(null);
                    mvpPresenter.queryQrcode();
                    break;

            }
        }
    }
}
