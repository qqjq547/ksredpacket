package com.guochuang.mimedia.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.guochuang.mimedia.app.App;

public class SystemUtil {

    /**
     * 获取网络状态
     *
     * @return
     */
    public static String getAPNType() {
        String netType = NetType.NETWORK_NO_NET;
        ConnectivityManager connMgr = (ConnectivityManager) App.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        TelephonyManager mTelephonyManager = (TelephonyManager) App.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        netType = getNetworkClass(mTelephonyManager.getNetworkType());
        return netType;
    }

    /**
     * 判断移动网络的类型
     *
     * @param networkType
     * @return 移动网络类型
     */
    private static final String getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetType.NETWORK_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetType.NETWORK_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetType.NETWORK_4G;
            default:
                return NetType.NETWORK_UNKNOW;
        }
    }

    /**
     * 设备唯一标示符
     */
    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(App.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId == null) {
            return "";
        }
        return deviceId;
    }

    /**
     * 设备id
     */
    public static String getAndroidId() {
        String androidId = Settings.System.getString(App.getInstance().getContentResolver(), Settings.System.ANDROID_ID);
        if (androidId == null) {
            return "";
        }
        return androidId;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取app版本
     */
    public static String getAppVersion() {

        try {
            return App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取分辨率
     */
    public static String getDeviceResolution() {
        WindowManager wm = (WindowManager) (App.getInstance().getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;
        return mScreenWidth + "*" + mScreenHeight;
    }

    public static class NetType {
        public static String NETWORK_WIFI = "WIFI";
        public static String NETWORK_2G = "2G";
        public static String NETWORK_3G = "2G";
        public static String NETWORK_4G = "2G";
        public static String NETWORK_UNKNOW = "未知";
        public static String NETWORK_NO_NET = "未连接网络";
    }
}
