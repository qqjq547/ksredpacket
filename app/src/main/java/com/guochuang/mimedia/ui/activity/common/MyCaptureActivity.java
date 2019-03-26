package com.guochuang.mimedia.ui.activity.common;

import android.Manifest;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.guochuang.mimedia.base.BaseActivity;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.redbag.EditRedbagActivity;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class MyCaptureActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    public static MyCaptureActivity activity;
  @Override
  protected BasePresenter createPresenter() {
    return null;
}
    @Override
    public int getLayout() {
        return R.layout.activity_my_capture;
    }

    public static MyCaptureActivity getActivity() {
        return activity;
    }

    @Override
    public void initViewAndData() {
        activity=this;
        tvTitle.setText(R.string.scan_pay);
        CaptureFragment captureFragment = new CaptureFragment();
        //定制化扫描框UI
        CodeUtils.setFragmentArgs(captureFragment,R.layout.view_qrcode_scan);
        //分析结果回调
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,captureFragment).commit();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            if (!TextUtils.isEmpty(result)){
                Uri uri=Uri.parse(result);
                String uuid=uri.getQueryParameter("uuid");
                if (!TextUtils.isEmpty(uuid)) {
                    startActivity(new Intent(MyCaptureActivity.this, KsbPayActivity.class).putExtra(Constant.USER_ACCOUNT_UUID, uuid));
                    finish();
                    MyCaptureActivity.getActivity().finish();
                }else {
                    showShortToast(R.string.scan_error);
                }
            }
        }

        @Override
        public void onAnalyzeFailed() {
            showShortToast(R.string.scan_fail);
        }
    };


}
