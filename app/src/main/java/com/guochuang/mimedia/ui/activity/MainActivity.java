package com.guochuang.mimedia.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.guochuang.mimedia.mvp.model.Remind;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.OssManager;
import com.guochuang.mimedia.ui.dialog.RemindDialog;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NameAuthAndSafety;
import com.guochuang.mimedia.mvp.model.RainMsg;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.model.VersionMsg;
import com.guochuang.mimedia.mvp.presenter.MainPresenter;
import com.guochuang.mimedia.mvp.view.MainView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.pay.AliPay;
import com.guochuang.mimedia.ui.dialog.RainNoticeDialog;
import com.guochuang.mimedia.ui.dialog.UpgradeDialog;
import com.guochuang.mimedia.ui.dialog.VersionUpdateDialog;
import com.guochuang.mimedia.ui.fragment.CircleFragment;
import com.guochuang.mimedia.ui.fragment.GameFragment;
import com.guochuang.mimedia.ui.fragment.InfoFragment;
import com.guochuang.mimedia.ui.fragment.MyFragment;
import com.guochuang.mimedia.ui.fragment.RedbagFragment;
import com.guochuang.mimedia.view.BadgeView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.objectbox.Box;
import rx.Subscriber;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.rbtn_info)
    RadioButton rbtnInfo;
    @BindView(R.id.rbtn_game)
    RadioButton rbtnGame;
    @BindView(R.id.rbtn_redbag)
    RadioButton rbtnRedbag;
    @BindView(R.id.rbtn_circle)
    RadioButton rbtnCircle;
    @BindView(R.id.rbtn_user)
    RadioButton rbtnUser;
    @BindView(R.id.rgroup_main)
    RadioGroup rgroupMain;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.v_fake_user)
    View vFakeUser;
    BadgeView badgeView;
    long mExitTime;
    Toast toast;
    int currentId = -1;
    Fragment[] fragments = new Fragment[5];
    boolean curRedbg = false;
    MainReceiver mainReceiver;
    boolean showErrorDelay=false;//是否正在延时显示错误toast
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewAndData() {
        instance=this;
        setStatusbar(R.color.bg_white,true);
        if (getIntent().getBooleanExtra(Constant.FROMLOGIN,true)){
            CommonUtil.syncCookie(this,ApiClient.HTML_URL);
        }
        fragments[0] = new InfoFragment();
        fragments[1] = new GameFragment();
        fragments[2] = new RedbagFragment();
        fragments[3] = new CircleFragment();
        fragments[4] = new MyFragment();
        rgroupMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_info:
                        setStatusBar(false);
                        selectTab(0);
                        break;
                    case R.id.rbtn_game:
                        setStatusBar(false);
                        selectTab(1);
                        break;
                    case R.id.rbtn_redbag:
                        setStatusBar(false);
                        selectTab(2);
                        break;
                    case R.id.rbtn_circle:
                        setStatusBar(false);
                        selectTab(3);
                        break;
                    case R.id.rbtn_user:
                        setStatusBar(true);
                        selectTab(4);
                        break;
                }
            }
        });
        rbtnRedbag.setChecked(true);
        mainReceiver=new MainReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.ACTION_CHANGE_COIN);
        filter.addAction(Constant.ACTION_CHANGE_AGENT);
        registerReceiver(mainReceiver,filter);
        mvpPresenter.getUserInfo();
        mvpPresenter.getRainMsg();
        mvpPresenter.getVersion(Constant.SYSTEM_CODE_ANDROID, String.valueOf(CommonUtil.getVersionCode(this)));
        mvpPresenter.messageIsNews();
        mvpPresenter.isNameAuthAndSafetyCode();
//        String channel=CommonUtil.getAppMetaData(this,Constant.JPUSH_CHANNEL);
//        if (!TextUtils.equals(channel,Constant.CHANNEL_DEFAULT)){
//            mvpPresenter.marketSwitch(channel,String.valueOf(CommonUtil.getVersionCode(this)));
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMsgDotView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainReceiver);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            toast = Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT);
            toast.show();
            mExitTime = System.currentTimeMillis();
        } else {
            toast.cancel();
            App.getInstance().finishActivity();
        }
    }


    @Override
    public void setUserInfo(UserInfo data) {
        App.getInstance().setUserInfo(data);
        CommonUtil.syncCookie(this,ApiClient.HTML_URL);
        ((MyFragment)fragments[4]).setUpUser();
    }

    @Override
    public void setRain(final RainMsg data) {
        if(data!=null&&data.isYes()) {
            String[] arr=data.getMessage().split("\n");
            String title=arr[0];
            String message=arr.length>1?arr[1]:"";
            RainNoticeDialog dialog=new RainNoticeDialog(this,title,message);
            dialog.setOnResultListener(new RainNoticeDialog.OnResultListener() {
                @Override
                public void onCancel() {
                    mvpPresenter.setRainTip(data.getRedPacketRainUuid());
                }

                @Override
                public void onStart() {
                    if (data.getValidSecond()>0&&data.getQuantity()>0)
                        IntentUtils.startRedbagRainActivityForResult(MainActivity.this, data.getValidSecond()*1000,data.getQuantity(),data.getRedPacketRainUuid());
                }
            });
            dialog.show();
        }
    }

    @Override
    public void setRainTip(Boolean data) {
        showShortToast(R.string.late_can_get);
    }

    @Override
    public void setVersion(final VersionMsg data) {
        mvpPresenter.getRemind();
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
           }
       }
    }


    @Override
    public void setError(String msg) {
       if (!showErrorDelay){
           showShortToast(msg);
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   showErrorDelay=false;
               }
           },3000);
       }

    }

    @Override
    public void setNameAuthSafefy(NameAuthAndSafety data) {
        getPref().setInt(PrefUtil.IDENTITY,data.getIsNameAuth());
        getPref().setInt(PrefUtil.SAFECODE,data.getIsSafetyCode());
    }

    @Override
    public void setMessageIsNews(Boolean data) {
        getPref().setBoolean(PrefUtil.MSGISNEW,data);
        setMsgDotView();
    }

    @Override
    public void setRemind(Remind data) {
      if (data!=null){
          long dateTime=getPref().getLong(PrefUtil.LAST_REMIND_TIME,0)+data.getIntervalMinute()*60000;
          LogUtil.d(CommonUtil.dateToString(new Date(dateTime),Constant.FORMAT_DATE));
          if (System.currentTimeMillis()>dateTime){
              getPref().setLong(PrefUtil.LAST_REMIND_TIME,System.currentTimeMillis());
              new RemindDialog(this,data.getPicture(),data.getLink()).show();
          }
      }
    }

    @Override
    public void setMarketSwitch(Integer data) {
        if (data!=null){//1为开启开关
            getPref().setString(PrefUtil.MARKET_SWITCH,data.intValue()==1?Constant.SWITCH_HIDE:Constant.SWITCH_SHOW);
            CommonUtil.syncCookie(this,ApiClient.HTML_URL);
        }
    }


    public void selectTab(int index) {
        if (currentId == index) {
            return;
        }
        if (currentId >= 0) {
            switchContent(fragments[currentId], fragments[index]);
        } else {
            switchContent(null, fragments[index]);
        }
        currentId = index;
    }

    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(R.id.content, to).commit();
        } else if (from != to) {
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.content, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    protected void setStatusBar(boolean isRed) {
        if (curRedbg==isRed){
            return;
        }
        curRedbg=isRed;
        if (isRed){
            setStatusbar(R.color.bg_red,false);
        }else {
            setStatusbar(R.color.bg_white,true);
        }
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
    public void setMsgDotView(){
        if (badgeView==null)
        badgeView= new BadgeView(this,vFakeUser);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.text_white));
        badgeView.setBadgeBackgroundColor(Color.RED);
        badgeView.setTextSize(6);
        int width=CommonUtil.dip2px(this,8);
        badgeView.setWidth(width);
        badgeView.setHeight(width);
        badgeView.setBadgeMargin(CommonUtil.dip2px(this,20),CommonUtil.dip2px(this,5)); //各边间隔
        if (getPref().getBoolean(PrefUtil.MSGISNEW,false)) {
            badgeView.show();
        }else {
            badgeView.hide();
        }
    }
    class MainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            switch (intent.getAction()){
                case Constant.ACTION_CHANGE_COIN:
                    ((RedbagFragment)fragments[2]).refreshWallet();
                    break;
                case Constant.ACTION_CHANGE_AGENT:
                    ((RedbagFragment)fragments[2]).refreshUserRole();
                    ((MyFragment)fragments[4]).refreshUseRole();
                    break;
                case Constant.ACTION_CHANGE_AAA:
                    ((MyFragment)fragments[4]).refreshMyAAA();
                    break;
            }
        }
    }
    public void clearMarker(){
        ((RedbagFragment)fragments[2]).clearMarker();
    }
    public void startNestAd(){
        ((RedbagFragment)fragments[2]).openNestAd();
        ((MyFragment)fragments[4]).openNestAd();
    }
}

