package com.guochuang.mimedia.ui.activity.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PayeeUser;
import com.guochuang.mimedia.mvp.presenter.MyCapturePresenter;
import com.guochuang.mimedia.mvp.view.MyCaptureView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.user.AaaTransferActivity;
import com.sz.gcyh.KSHongBao.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class MyCaptureActivity extends MvpActivity<MyCapturePresenter> implements MyCaptureView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    public static MyCaptureActivity activity;
  @Override
  protected MyCapturePresenter createPresenter() {
    return new MyCapturePresenter(this);
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
                Uri uri=Uri.parse(result);
//                String uuid=uri.getQueryParameter("uuid");
//                if (!TextUtils.isEmpty(uuid)) {
//                    showLoadingDialog(null);
//                    mvpPresenter.queryUserInfoByAccountUuid(uuid);
//                    MyCaptureActivity.getActivity().finish();
//                    return;
//                }
                String type=uri.getQueryParameter("type");
                String chain=uri.getQueryParameter("chain");
                String digiCurrcy=uri.getQueryParameter("digiCurrcy");
                String address=uri.getQueryParameter("address");
                //type=2表示钱包地址，chain=1表示外链
                if (TextUtils.equals(type,"2")&&TextUtils.equals(chain,"1")&&!TextUtils.isEmpty(address)){
                    if (TextUtils.equals(digiCurrcy,Constant.DIGITAL_CURRENCY_AAA)){
                        MyCaptureActivity.getActivity().finish();
                        startActivity(new Intent(MyCaptureActivity.this,AaaTransferActivity.class).putExtra(Constant.ADDRESS,address));
                        return;
                    }else if(TextUtils.equals(digiCurrcy,Constant.DIGITAL_CURRENCY_SEAL)){
                        MyCaptureActivity.getActivity().finish();
                        startActivity(new Intent(MyCaptureActivity.this,AaaTransferActivity.class).putExtra(Constant.ADDRESS,address));
                        return;
                    }
                }
                startActivity(new Intent(MyCaptureActivity.this,ScanResultActivity.class).putExtra(Constant.DATA,result));
                finish();
            }
        }

        @Override
        public void onAnalyzeFailed() {
            showShortToast(R.string.scan_fail);
        }
    };


    @Override
    public void setData(PayeeUser data) {
        closeLoadingDialog();
        if (data!=null){
            startActivity(new Intent(MyCaptureActivity.this, KsbPayActivity.class).putExtra(Constant.PAYEE_USER, data));
            finish();
        }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}
