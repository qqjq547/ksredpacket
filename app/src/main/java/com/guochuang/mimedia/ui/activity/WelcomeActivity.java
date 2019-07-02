package com.guochuang.mimedia.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ProgressBar;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.guochuang.mimedia.app.AdInfoService;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.VersionMsg;
import com.guochuang.mimedia.mvp.presenter.WelcomePresenter;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.activity.user.LoginActivity;
import com.guochuang.mimedia.ui.activity.user.SetMobileAcitivity;
import com.guochuang.mimedia.ui.dialog.UpgradeDialog;
import com.guochuang.mimedia.ui.dialog.VersionUpdateDialog;
import com.sz.gcyh.KSHongBao.R;


public class WelcomeActivity extends MvpActivity<WelcomePresenter> implements WelcomeView {

    boolean finishUpdate=false;
    boolean finishCountDown=false;

    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViewAndData() {
        mvpPresenter.getVersion(Constant.SYSTEM_CODE_ANDROID, String.valueOf(CommonUtil.getVersionCode(this)));
        startService(new Intent(this, AdInfoService.class));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishCountDown=true;
                next();
            }
        },2000);

    }

    private void next() {
        if (finishCountDown&&finishUpdate) {
            if (getPref().getBoolean(PrefUtil.FIRSTOPEN, true)) {
                startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                getPref().setBoolean(PrefUtil.FIRSTOPEN, false);
            } else {
                if (TextUtils.isEmpty(getPref().getUserToken())) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                } else {
                    if (TextUtils.isEmpty(getPref().getString(PrefUtil.MOBILE, null))
                            &&TextUtils.isEmpty(getPref().getString(PrefUtil.EMAIL, null))
                            &&App.getInstance().getUserInfo()==null) {
                        startActivity(new Intent(WelcomeActivity.this, SetMobileAcitivity.class));
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }
                }
            }
            finish();
        }
    }

    @Override
    public void setVersion(final VersionMsg data) {
        if (data!=null){
            final boolean isForce=data.getIsForce()>0;
            long time=getPref().getLong(PrefUtil.UPGRADE_NOTICE,0);
            if (isForce||(System.currentTimeMillis()-time>data.getRemindIntervalMinute()*60*1000)){
                String content=data.getTitle()+"\n"+data.getDescription();
                content=content.replace("\\n", "\n");
                final VersionUpdateDialog versionUpdateDialog=new VersionUpdateDialog(this,content,isForce);
                versionUpdateDialog.setOnResultListener(new VersionUpdateDialog.OnResultListener() {
                    @Override
                    public void onRefuse() {
                        finishUpdate=true;
                        next();
                    }
                    @Override
                    public void onUpdate() {
                        if (!isForce) {
                            versionUpdateDialog.dismiss();
                        }
                        startUpgrade(data.getUrl());
                    }
                });
                versionUpdateDialog.setCancelable(false);
                versionUpdateDialog.show();
                getPref().setLong(PrefUtil.UPGRADE_NOTICE,System.currentTimeMillis());
                return;
            }
        }
        finishUpdate=true;
        next();
    }
    private void startUpgrade(String downloadUrl) {
        if(TextUtils.isEmpty(downloadUrl)){
            return;
        }
        if (downloadUrl.endsWith(".apk")){
            AllenVersionChecker
                    .getInstance()
                    .downloadOnly(UIData.create().setDownloadUrl(downloadUrl))
                    .setDirectDownload(true)
                    .setForceRedownload(true)
                    .setCustomDownloadingDialogListener(new CustomDownloadingDialogListener() {
                        @Override
                        public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                            UpgradeDialog downloadDialog=new UpgradeDialog(context);
                            return downloadDialog;
                        }
                        @Override
                        public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                            ProgressBar progressBar = dialog.findViewById(R.id.pb_upgrade);
                            progressBar.setProgress(progress);
                        }
                    })
                    .executeMission(this);
        }else if(downloadUrl.contains(".html")){
            IntentUtils.startOutWebActivity(this,downloadUrl);
        }
    }

    @Override
    public void setError(String msg) {
        showShortToast(msg);
    }
}
