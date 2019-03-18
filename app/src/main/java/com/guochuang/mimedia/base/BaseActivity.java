package com.guochuang.mimedia.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.mvp.view.MainView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.CustomProDialog;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.SystemUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {
    private CompositeSubscription mCompositeSubscription;
    private static PrefUtil pref;
    private Dialog proDialog;
    private Integer orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private ImmersionBar immersionBar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (orientation!=null){
            setRequestedOrientation(orientation);
        }
        ButterKnife.bind(this);
        App.getInstance().addActivity(this);
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
        if (immersionBar!=null) {
            ImmersionBar.with(this).destroy();
        }
        App.getInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (CommonUtil.isShowKeyBoard(this)) {
            CommonUtil.hideInput(this, getCurrentFocus());
            return;
        }
        super.onBackPressed();
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {

        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showLoadingDialog(int resid) {
        String msg = getResources().getString(resid);
        showLoadingDialog(msg);
    }

    public void showLoadingDialog(String title) {
        if (proDialog != null && proDialog.isShowing()) {
            proDialog.cancel();
        }
        proDialog = CustomProDialog.show(this, title, false, null);
    }

    public void showLongToast(String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(int resId) {
        Toast.makeText(getApplication(), resId, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(int resId) {
        Toast.makeText(getApplication(), resId, Toast.LENGTH_SHORT).show();
    }

    public void closeLoadingDialog() {
        try {
            if (proDialog != null && proDialog.isShowing()) {
                proDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrefUtil getPref() {
        if (pref == null) {
            pref = PrefUtil.getInstance();
        }
        return pref;
    }


    public abstract int getLayout();

    public abstract void initViewAndData();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (CommonUtil.isShouldHideInput(v, ev)) {
                if(CommonUtil.hideInputMethod(this, v)) {
//                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public void setStatusbar(int bgColor,boolean darkFont){
        if (immersionBar==null) {
            immersionBar = ImmersionBar.with(this);
        }
        immersionBar.statusBarColor(bgColor)
                .statusBarDarkFont(darkFont)
                .fitsSystemWindows(true)
                .init();
    }
}
