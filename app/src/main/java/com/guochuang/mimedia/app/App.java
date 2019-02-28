package com.guochuang.mimedia.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.guochuang.mimedia.mvp.model.MyObjectBox;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.activity.user.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.guochuang.mimedia.tools.PrefUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.ArrayList;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jpush.android.api.JPushInterface;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class App extends Application {
        private static App instance;
        private ArrayList<Activity> activityList = new ArrayList<Activity>();
        private BoxStore boxStore;
        private IWXAPI wxapi;
        private UserInfo userInfo;
        static {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
                @Override
                public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                    layout.setPrimaryColorsId(android.R.color.white);//全局设置主题颜色
                    return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
                }
            });
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
                @Override
                public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                    //指定为经典Footer，默认是 BallPulseFooter
                    return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
                }
            });
        }

        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
            PrefUtil.init(this);
            boxStore = MyObjectBox.builder().androidContext(this).build();
            Box<UserInfo> box=boxStore.boxFor(UserInfo.class);
            if (box.getAll().size()>0){
                userInfo=box.getAll().get(0);
            }
            JPushInterface.setDebugMode(Constant.isDebug);
            JPushInterface.init(this);
            //注册全局事件监听类
            JShareInterface.setDebugMode(Constant.isDebug);
            JShareInterface.init(this);
            SDKInitializer.initialize(this);
            SDKInitializer.setCoordType(CoordType.BD09LL);
            wxapi = WXAPIFactory.createWXAPI(this,"wx3f027d9298bbbed4", true);
            wxapi.registerApp("wx3f027d9298bbbed4");
            new File(Constant.COMPRESS_DIR_PATH).mkdirs();
            new File(Constant.COMMON_PATH).mkdirs();
            QbSdk.preInit(this, new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished() {

                }
            });

        }
    public static App getInstance(){
        return instance;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    
    public void addActivity(Activity a) {
        activityList.add(a);
    }

    public void removeActivity(Activity a) {
        activityList.remove(a);
    }

    public Activity lastActivity() {
            if (activityList.size()>0) {
                return activityList.get(activityList.size() - 1);
            }else {
                return null;
            }
    }
    public void forceLogin() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityList.clear();
        App.getInstance().getBoxStore().boxFor(UserInfo.class).removeAll();
        String adConfig=PrefUtil.getInstance().getString(PrefUtil.ADVER_MESSAGE,"");
        PrefUtil.getInstance().clean();
        PrefUtil.getInstance().setBoolean(PrefUtil.FIRSTOPEN, false);
        PrefUtil.getInstance().setString(PrefUtil.ADVER_MESSAGE,adConfig);
        if (lastActivity()!=null&&lastActivity() instanceof LoginActivity){
            return;
        }
        Intent in=new Intent(this ,LoginActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }
    public void finishActivity() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    public BoxStore getBoxStore() {
        return boxStore;
    }

    public IWXAPI getWxapi() {
        return wxapi;
    }

    public void setUserInfo(UserInfo userInfo) {
        Box<UserInfo> box=boxStore.boxFor(UserInfo.class);
        box.put(userInfo);
        this.userInfo=box.getAll().get(0);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

}
