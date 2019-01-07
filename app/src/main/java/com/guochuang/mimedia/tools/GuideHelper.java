package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class GuideHelper {
    public final static String BAIDU_PACKAGENAME = "com.baidu.BaiduMap";
    public final static String GAODE_PACKAGENAME = "com.autonavi.minimap";
    public final static String TENCENT_PACKAGENAME = "com.tencent.map";
   public static void guide(Activity activity,double lat,double lng){
       Intent naviIntent;
       if (isPackageInstalled(activity,BAIDU_PACKAGENAME)){
           naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" +lat + "," + lng));
           activity.startActivity(naviIntent);
       }else if(isPackageInstalled(activity,GAODE_PACKAGENAME)){
           naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat="+ lat +"&dlon="+ lng+"&dname=目的地&dev=0&t=2"));
           activity.startActivity(naviIntent);
       }else if(isPackageInstalled(activity,GAODE_PACKAGENAME)){
           naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=目的地&tocoord=" + lat + "," + lng + "&policy=0&referer=appName"));
           activity.startActivity(naviIntent);
       }else {
           Toast.makeText(activity,"请先安装地图app",Toast.LENGTH_SHORT).show();
       }

   }

    private static boolean isPackageInstalled(Context mContext, String packagename) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        } finally {
            return packageInfo == null ? false : true;
        }
    }

}
