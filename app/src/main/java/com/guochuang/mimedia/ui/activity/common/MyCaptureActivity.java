package com.guochuang.mimedia.ui.activity.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PayeeUser;
import com.guochuang.mimedia.mvp.presenter.MyCapturePresenter;
import com.guochuang.mimedia.mvp.view.MyCaptureView;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import butterknife.BindView;
import butterknife.OnClick;

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
        initFrame();
    }

    private void initFrame() {
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
                Intent intent=getIntent();
                intent.putExtra(Constant.ADDRESS,result);
                setResult(RESULT_OK,intent);
                MyCaptureActivity.getActivity().finish();
            }
        }

        @Override
        public void onAnalyzeFailed() {
            showShortToast(R.string.scan_fail);
        }
    };

}
