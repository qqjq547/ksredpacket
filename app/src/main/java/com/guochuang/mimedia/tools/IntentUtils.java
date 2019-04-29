package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.ui.activity.FengWoLinkWebViewActivity;
import com.guochuang.mimedia.ui.activity.beenest.BeeNestActivity;
import com.guochuang.mimedia.ui.activity.beenest.BidBrandActivity;
import com.guochuang.mimedia.ui.activity.beenest.EditAdActivity;
import com.guochuang.mimedia.ui.activity.common.ImagePreviewActivity;
import com.guochuang.mimedia.ui.activity.redbag.CheckQuestionActivity;
import com.guochuang.mimedia.ui.activity.redbag.EditRedbagActivity;
import com.guochuang.mimedia.ui.activity.redbag.LinkRedbagDetailActivity;
import com.guochuang.mimedia.ui.activity.redbag.LookSurevyActivity;
import com.guochuang.mimedia.ui.activity.redbag.LookVideoProblemActivity;
import com.guochuang.mimedia.ui.activity.redbag.RedbagDetailActivity;
import com.guochuang.mimedia.ui.activity.redbag.VideoPreviewActivity2;
import com.guochuang.mimedia.ui.activity.user.CardAddActivity;
import com.guochuang.mimedia.ui.activity.city.CityBidRecordActivity;
import com.guochuang.mimedia.ui.activity.city.CityDetailActivity;
import com.guochuang.mimedia.ui.activity.common.ImageActivity;
import com.guochuang.mimedia.ui.activity.operation.IncomeDetailActivity;
import com.guochuang.mimedia.ui.activity.operation.IncreaseDetailActivity;
import com.guochuang.mimedia.ui.activity.info.InfoDetailActivity;
import com.guochuang.mimedia.ui.activity.redbag.LuckyActivity;
import com.guochuang.mimedia.ui.activity.MainActivity;
import com.guochuang.mimedia.ui.activity.common.PurchaseActivity;
import com.guochuang.mimedia.ui.activity.user.RecommendDetailActivity;
import com.guochuang.mimedia.ui.activity.redbag.RedbagJoinedActivity;
import com.guochuang.mimedia.ui.activity.redbag.RedbagRainActivity;
import com.guochuang.mimedia.ui.activity.redbag.SquareDetailActivity;
import com.guochuang.mimedia.ui.activity.common.WebActivity;

import java.util.ArrayList;

public class IntentUtils {
    public static void startInfoDetailActivity(Activity activity,String title,String articleUuid){
        Intent intent=new Intent(activity,InfoDetailActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.ARTICLEUUID,articleUuid);
        activity.startActivity(intent);
    }
    public static void startInfoDetailActivityForResult(Activity activity,String title,String articleUuid,int position){
        Intent intent=new Intent(activity,InfoDetailActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.ARTICLEUUID,articleUuid);
        intent.putExtra(Constant.POSITION,position);
        intent.putExtra(Constant.FROM_COLLECT,true);
        activity.startActivityForResult(intent,Constant.REQUEST_COLLECT);
    }
    public static void startMainActivity(Activity activity,boolean fromLogin) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Constant.FROMLOGIN,fromLogin);
        activity.startActivity(intent);
    }
    public static void startCardAddActivity(Activity activity,long id,String name,String number,String mobile) {
        Intent intent = new Intent(activity, CardAddActivity.class);
        intent.putExtra(Constant.CARDID,id);
        intent.putExtra(Constant.NAME,name);
        intent.putExtra(Constant.CARDNUMBER,number);
        intent.putExtra(Constant.CARDMOBILE,mobile);
        activity.startActivity(intent);
    }
    public static void startRedbagDetailActivity(Activity activity, RedbagDetail redbagDetail,String redPacketUuid,String roleType,String redPacketType) {
        Intent intent = new Intent(activity, RedbagDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_DETAIL,redbagDetail);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.ROLE_TYPE,roleType);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        activity.startActivity(intent);
    }
    public static void startLinkRedbagDetailActivity(Activity activity, RedbagDetail redbagDetail,String redPacketUuid,String roleType,String redPacketType) {
        Intent intent = new Intent(activity, LinkRedbagDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_DETAIL,redbagDetail);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.ROLE_TYPE,roleType);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        activity.startActivity(intent);
    }
    public static void startRedbagDetailActivity(Activity activity, String redPacketUuid,String roleType,String redPacketType,String startIndex) {
        Intent intent = new Intent(activity, RedbagDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.ROLE_TYPE,roleType);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        intent.putExtra(Constant.START_INDEX,startIndex);
        activity.startActivity(intent);
    }
    public static void startLinkRedbagDetailActivity(Activity activity, String redPacketUuid,String roleType,String redPacketType,String startIndex) {
        Intent intent = new Intent(activity, LinkRedbagDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.ROLE_TYPE,roleType);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        intent.putExtra(Constant.START_INDEX,startIndex);
        activity.startActivity(intent);
    }
    public static void startRedbagDetailActivityForResult(Activity activity, String redPacketUuid,String roleType,String redPacketType,int position) {
        Intent intent = new Intent(activity, RedbagDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.ROLE_TYPE,roleType);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        intent.putExtra(Constant.FROM_COLLECT,true);
        intent.putExtra(Constant.POSITION,position);
        activity.startActivityForResult(intent,Constant.REQUEST_COLLECT);
    }
    public static void startWebActivity(Activity activity,String title, String  url) {
        Intent intent = new Intent(activity,WebActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.URL,url);
        activity.startActivity(intent);
    }
    public static void startWebActivity(Activity activity,String title, String  url,boolean hasRefresh) {
        Intent intent = new Intent(activity,WebActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.URL,url);
        intent.putExtra(Constant.HAS_REFRESH,hasRefresh);
        activity.startActivity(intent);
    }
    public static void startImageActivity(Activity activity,String  url) {
        Intent intent = new Intent(activity,ImageActivity.class);
        intent.putExtra(Constant.URL,url);
        activity.startActivity(intent);
    }
    public static void startOutWebActivity(Activity activity, String  url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void startEditRedbagActivity(Activity activity, String redPacketType) {
        Intent intent = new Intent(activity, EditRedbagActivity.class);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        activity.startActivity(intent);
    }
    public static void startWechatApp(Activity activity){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void startWeiboApp(Activity activity){
        try {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("sinaweibo://splash"));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void startRedbagRainActivityForResult(Activity activity, int duration,int count,String redPacketRainUuid) {
        Intent intent = new Intent(activity, RedbagRainActivity.class);
        intent.putExtra(Constant.RAIN_DURATION,duration);
        intent.putExtra(Constant.RAIN_COUNT,count);
        intent.putExtra(Constant.RAIN_UUID,redPacketRainUuid);
        activity.startActivityForResult(intent,Constant.REQUEST_DEFAULT);
    }
    public static void startCityDetailActivity(Activity activity, long regionId) {
        Intent intent = new Intent(activity, CityDetailActivity.class);
        intent.putExtra(Constant.REGIONID,regionId);
        activity.startActivity(intent);
    }
    public static void startCityBidRecordActivity(Activity activity, long regionId) {
        Intent intent = new Intent(activity, CityBidRecordActivity.class);
        intent.putExtra(Constant.REGIONID,regionId);
        activity.startActivity(intent);
    }
    public static void startPurchaseActivity(Activity activity, int purchaseType,long snatchId,int unitPrice,int payNumber,String money) {
        Intent intent = new Intent(activity, PurchaseActivity.class);
        intent.putExtra(Constant.PURCHASE_TYPE,purchaseType);
        intent.putExtra(Constant.SNATCHID,snatchId);
        intent.putExtra(Constant.PRICE,unitPrice);
        intent.putExtra(Constant.PAYNUMBER,payNumber);
        intent.putExtra(Constant.MONEY,money);
        activity.startActivityForResult(intent,Constant.REQUEST_PURCHASE);
    }
    public static void startPurchaseActivity(Activity activity, int purchaseType,String money,long regionId) {
        Intent intent = new Intent(activity, PurchaseActivity.class);
        intent.putExtra(Constant.PURCHASE_TYPE,purchaseType);
        intent.putExtra(Constant.MONEY,money);
        intent.putExtra(Constant.REGIONID,regionId);
        activity.startActivityForResult(intent,Constant.REQUEST_PURCHASE);
    }
    public static void startPurchaseActivity(Activity activity, int purchaseType,int payNumber,String money) {
        Intent intent = new Intent(activity, PurchaseActivity.class);
        intent.putExtra(Constant.PURCHASE_TYPE,purchaseType);
        intent.putExtra(Constant.MONEY,money);
        intent.putExtra(Constant.PAYNUMBER,payNumber);
        activity.startActivityForResult(intent,Constant.REQUEST_PURCHASE);
    }
    public static void startPurchaseActivity(Activity activity, int purchaseType,long nestLocationId,int price,String money,int days,String startDate,String nestLatitude,String nestLongitude) {
        Intent intent = new Intent(activity, PurchaseActivity.class);
        intent.putExtra(Constant.PURCHASE_TYPE,purchaseType);
        intent.putExtra(Constant.NESTLOCATIONID,nestLocationId);
        intent.putExtra(Constant.PRICE,price);
        intent.putExtra(Constant.MONEY,money);
        intent.putExtra(Constant.DAYS,days);
        intent.putExtra(Constant.STARTDATE,startDate);
        intent.putExtra(Constant.NESTLATITUDE,nestLatitude);
        intent.putExtra(Constant.NESTLONGITUDE,nestLongitude);
        activity.startActivityForResult(intent,Constant.REQUEST_PURCHASE);
    }
    public static void startRedbagJoinedActivity(Activity activity, String redPacketUuid,String avatar,String name,String ksb,String money,String areaType,String drawNumber,String total) {
        Intent intent = new Intent(activity, RedbagJoinedActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.AVATAR,avatar);
        intent.putExtra(Constant.NAME,name);
        intent.putExtra(Constant.KSB,ksb);
        intent.putExtra(Constant.MONEY,money);
        intent.putExtra(Constant.AREATYPE,areaType);
        intent.putExtra(Constant.DRAWNUMBER,drawNumber);
        intent.putExtra(Constant.TOTAL,total);
        activity.startActivity(intent);
    }
    public static void startIncomeDetailActivity(Activity activity,long statisticsId, String income_type,String time_type, String startDate, String endDate, String parentType, String sonType) {
        Intent intent = new Intent(activity, IncomeDetailActivity.class);
        intent.putExtra(Constant.STATISTICSID,statisticsId);
        intent.putExtra(Constant.INCOME_TYPE,income_type);
        intent.putExtra(Constant.TIME_TYPE,time_type);
        intent.putExtra(Constant.START_DATE,startDate);
        intent.putExtra(Constant.END_DATE,endDate);
        intent.putExtra(Constant.PARENT_TYPE,parentType);
        intent.putExtra(Constant.SON_TYPE,sonType);
        activity.startActivity(intent);
    }
    public static void startIncreaseDetailActivity(Activity activity, String time_type, String startDate, String endDate) {
        Intent intent = new Intent(activity, IncreaseDetailActivity.class);
        intent.putExtra(Constant.TIME_TYPE,time_type);
        intent.putExtra(Constant.START_DATE,startDate);
        intent.putExtra(Constant.END_DATE,endDate);
        activity.startActivity(intent);
    }
    public static void startLuckyActivity(Activity activity, String redPacketUuid) {
        Intent intent = new Intent(activity, LuckyActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        activity.startActivity(intent);
    }
    public static void startSquareDetailActivity(Activity activity,String redPacketRainUuid) {
        Intent intent = new Intent(activity, SquareDetailActivity.class);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketRainUuid);
        activity.startActivity(intent);
    }
    public static void startRecommendDetailActivity(Activity activity, RecommendData data, boolean isAgent) {
        Intent intent = new Intent(activity, RecommendDetailActivity.class);
        intent.putExtra(Constant.RECOMMENDDATA,data);
        intent.putExtra(Constant.ISAGENT,isAgent);
        activity.startActivity(intent);
    }
    public static void startImagePreviewActivity(Activity activity, int position, String... urls) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(Constant.ARGS_ARRAY, urls);
        intent.putExtra(Constant.ARGS_POSITION, position);
        activity.startActivity(intent);
    }

    public static void startImagePreviewActivity(Activity activity, int position, ArrayList<String> urls) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(Constant.ARGS_LIST, urls);
        intent.putExtra(Constant.ARGS_POSITION, position);
        activity.startActivity(intent);
    }
    public static void startEditAdActivity(Activity activity, long nestInfoId,long nestLocationId,long nestSuccessId,boolean update) {
        Intent intent = new Intent(activity, EditAdActivity.class);
        intent.putExtra(Constant.NESTINFOID, nestInfoId);
        intent.putExtra(Constant.NESTLOCATIONID, nestLocationId);
        intent.putExtra(Constant.NESTSUCCESSID, nestSuccessId);
        intent.putExtra(Constant.NESTUPDATE, update);
        activity.startActivityForResult(intent,Constant.REQUEST_EDIT_NESTAD);
    }
    public static void startBidBrandActivity(Activity activity, long nestLocationId, String latitude,String longitude) {
        Intent intent = new Intent(activity, BidBrandActivity.class);
        intent.putExtra(Constant.NESTLOCATIONID, nestLocationId);
        intent.putExtra(Constant.LATITUDE, latitude);
        intent.putExtra(Constant.LONGITUDE, longitude);
        activity.startActivity(intent);
    }
    public static void startVideoPreviewActivity(Activity activity, String videoPath){
        Intent intent = new Intent(activity, VideoPreviewActivity2.class);
        intent.putExtra(Constant.VIDEO_PATH, videoPath);
        activity.startActivity(intent);
    }
    public static void startBeeNestActivity(Activity activity, long nestInfoId,long nestLocationId,boolean isPreview) {
        Intent intent = new Intent(activity, BeeNestActivity.class);
        intent.putExtra(Constant.NESTINFOID, nestInfoId);
        intent.putExtra(Constant.NESTLOCATIONID, nestLocationId);
        intent.putExtra(Constant.ISPREVIEW, isPreview);
        activity.startActivity(intent);
    }
    public static void startBeeNestActivity(Activity activity, NestAd ad) {
        Intent intent = new Intent(activity, BeeNestActivity.class);
        intent.putExtra(Constant.NESTAD, ad);
        activity.startActivity(intent);
    }

    public static void startLookVideoProblemActivity(Activity activity,  long surveyId, String redPacketUuid) {
        Intent intent = new Intent(activity, LookVideoProblemActivity.class);
        intent.putExtra(Constant.SURVEYID,surveyId);
        intent.putExtra(Constant.RED_PACKET_ID,redPacketUuid);
        activity.startActivity(intent);
    }

    public static void startLookSurevyActivity(Activity activity, long surveyId, String redPacketUuid) {
        Intent intent = new Intent(activity, LookSurevyActivity.class);
        intent.putExtra(Constant.SURVEYID,surveyId);
        intent.putExtra(Constant.RED_PACKET_ID,redPacketUuid);
        activity.startActivity(intent);
    }
    public static void startCheckQuestionActivity(Activity activity, String redPacketType,long surveyId, String redPacketUuid) {
        Intent intent = new Intent(activity, CheckQuestionActivity.class);
        intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
        intent.putExtra(Constant.RED_PACKET_UUID,redPacketUuid);
        intent.putExtra(Constant.SURVEYID,surveyId);
        activity.startActivity(intent);
    }

    public static void startVideoPreviewActivityForResult(Activity activity, String path) {
        Intent intent = new Intent(activity, VideoPreviewActivity2.class);
        intent.putExtra(Constant.VIDEO_PATH, path);
        intent.putExtra(Constant.COUNTDOWN_GONE, false);
        activity.startActivityForResult(intent,Constant.REQUEST_STARTPREVIDEO_CODE);
    }

    public static void startFengWoLinkWebViewActivity(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, FengWoLinkWebViewActivity.class);
        intent.putExtra(Constant.TITLE,title);
        intent.putExtra(Constant.URL,url);
        activity.startActivityForResult(intent,Constant.REQUEST_STARTPRE_FENGWOLINKWEB);
    }
}
