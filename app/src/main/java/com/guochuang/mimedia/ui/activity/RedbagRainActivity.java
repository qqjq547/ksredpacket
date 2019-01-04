package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.RedbagRainPresenter;
import com.guochuang.mimedia.mvp.view.RedbagRainView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.dialog.RainNoticeDialog;
import com.guochuang.mimedia.ui.dialog.RainResultDialog;
import com.guochuang.mimedia.view.rain.MeteorShowerSurface;

import butterknife.BindView;

public class RedbagRainActivity extends MvpActivity<RedbagRainPresenter> implements RedbagRainView {
    @BindView(R.id.meteor_surface)
    MeteorShowerSurface meteorSurface;
    String redPacketRainUuid;
    @Override
    protected RedbagRainPresenter createPresenter() {
        return new RedbagRainPresenter(this) ;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_redbag_rain;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.bg_rain,false);
       int duration = getIntent().getIntExtra(Constant.RAIN_DURATION,0);
       int count = getIntent().getIntExtra(Constant.RAIN_COUNT,0);
       redPacketRainUuid=getIntent().getStringExtra(Constant.RAIN_UUID);
       if (duration>0&&count>0){
           meteorSurface.setParams(duration,count);
           meteorSurface.setmGameListener(new MeteorShowerSurface.GameListener() {
               @Override
               public void preGame() {

               }

               @Override
               public void inGameInterval() {

               }

               @Override
               public void postGame(int score) {
                   showLoadingDialog(null);
                   mvpPresenter.getRainMsg(redPacketRainUuid,score);
               }
           });
           meteorSurface.post(new Runnable() {
               @Override
               public void run() {
                   meteorSurface.start();
               }
           });
       }else {
           showShortToast(R.string.params_error);
           finish();
       }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        meteorSurface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        meteorSurface.onPause();
    }

    @Override
    public void setData(String data) {
        closeActivity(data);
    }

    @Override
    public void setError(String msg) {
        closeActivity(msg);
    }
    public void closeActivity(String msg){
        closeLoadingDialog();
        String message;
        String ksbMsg="";
        if (TextUtils.isEmpty(msg)){
            message=getString(R.string.unknow_error_and_late);
        }else {
            String[] arr=msg.split("\n");
            message=arr[0];
            ksbMsg=arr.length>1?arr[1]:"";
        }
        RainResultDialog dialog=new RainResultDialog(this,message,ksbMsg);
        dialog.setOnResultListener(new RainResultDialog.OnResultListener() {
            @Override
            public void onSure() {
                setResult(RESULT_OK);
                finish();
            }
        });
        dialog.show();
    }
}
