package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.common.KsbPayActivity;
import com.guochuang.mimedia.ui.activity.common.MyCaptureActivity;
import com.guochuang.mimedia.view.SquareImageView;
import com.sz.gcyh.KSHongBao.R;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Line;

public class MyPayCodeActivity extends MvpActivity {
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
    protected BasePresenter createPresenter() {
        return null;
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
        GlideImgManager.loadImage(this,App.getInstance().getUserInfo().getTwoDimensional(),ivCode);
        GlideImgManager.loadImage(this,App.getInstance().getUserInfo().getTwoDimensional(),ivFrameCode);
        tvFrameName.setText(App.getInstance().getUserInfo().getNickName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPref().getInt(PrefUtil.IDENTITY,0)==0){
            tvRecord.setVisibility(View.INVISIBLE);
            tvIdentify.setVisibility(View.VISIBLE);
        }else {
            tvRecord.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(this, MyCaptureActivity.class);
                startActivityForResult(intent,Constant.REQUEST_SCAN_CODE);
                break;
            case R.id.iv_code:
                break;
            case R.id.tv_save:
                linPrint.setVisibility(View.INVISIBLE);
                Bitmap bitmap=getViewBitmap(linPrint);
                String filePath=Constant.COMMON_PATH +File.separator+ System.currentTimeMillis() + ".png";
                CommonUtil.saveBitmap(bitmap,filePath);
                showShortToast(R.string.save_image_success);
                Uri uri = Uri.fromFile(new File(filePath));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                break;
            case R.id.tv_record:
                startActivity(new Intent(this,MyKsbDetailsActivity.class).putExtra(Constant.INCOME_TYPE,4l));
                break;
            case R.id.tv_identify:
                startActivity(new Intent(this, IdentifyActivity.class));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==Constant.REQUEST_SCAN_CODE){
                if(null != data){
                    Bundle bundle = data.getExtras();
                    if(bundle == null){
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        showShortToast(result);
                        startActivity(new Intent(this,KsbPayActivity.class));
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        showShortToast(R.string.scan_fail);
                    }
                }
            }
        }
    }
    private Bitmap getViewBitmap(View view) {
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }
}
