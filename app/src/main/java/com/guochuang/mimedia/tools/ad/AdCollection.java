package com.guochuang.mimedia.tools.ad;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.PrefUtil;

public class AdCollection {
    Context context;
    AdInfo adInfo ;
    TencentAd tencentAd;
    BaiduAd baiduAd;
    VungleAd vungleAd;

    public static final String TYPE_GDT = "tencent";
    public static final String TYPE_BD = "baidu";
    public static final String TYPE_VUNGLE = "vungle";

    public static final String TYPE_SPLASH = "1000";
    public static final String TYPE_BANNER = "1001";
    public static final String TYPE_INFO = "1002";

    public TencentAd defaultTencentAd;
    public BaiduAd defaultBaiduAd;

    public void init(Context context){
        this.context=context;
        String adInfoStr = PrefUtil.getInstance().getString(PrefUtil.ADVER_MESSAGE, "");
        if(!TextUtils.isEmpty(adInfoStr)){
             adInfo = GsonUtil.GsonToBean(adInfoStr,AdInfo.class);
            for (AdInfo.VendorAdListBeanX beanX:adInfo.getVendorAdList()){
                    if (TextUtils.equals(beanX.getVendorType(),adInfo.getDefaultVendorType())){
                        switch (adInfo.getDefaultVendorType()){
                            case TYPE_GDT:
                                defaultTencentAd=new TencentAd(adInfo.getDefaultAppId());
                                break;
                            case TYPE_BD:
                                defaultBaiduAd=new BaiduAd(context,adInfo.getDefaultAppId());
                                break;
                        }
                    }
            }
        }
    }
    public void showSplash(ViewGroup viewGroup,OnShowResultListener onShowResultListener){
          if (adInfo==null){
            for (AdInfo.VendorAdListBeanX beanX:adInfo.getVendorAdList()){
               for (AdInfo.VendorAdListBeanX.VendorAdListBean bean:beanX.getVendorAdList()){
                   if (TextUtils.equals(bean.getType(),TYPE_SPLASH)){
                       switch (bean.getType()){
                           case TYPE_GDT:
                               if(tencentAd==null){
                                   tencentAd=new TencentAd(bean.getAppId());
                               }
                               tencentAd.showSplash(context,viewGroup,bean.getLocationId(),onShowResultListener);
                               break;
                           case TYPE_BD:
                               if(baiduAd==null){
                                   baiduAd=new BaiduAd(context,bean.getAppId());
                               }
                               baiduAd.showSplash(viewGroup,bean.getLocationId(),onShowResultListener);
                               break;
                       }
                       return;
                   }
               }
            }
          }
    }
    public void showBannerForInfo(ViewGroup viewGroup,OnShowResultListener onShowResultListener){
        if (adInfo==null){
            for (AdInfo.VendorAdListBeanX beanX:adInfo.getVendorAdList()){
                for (AdInfo.VendorAdListBeanX.VendorAdListBean bean:beanX.getVendorAdList()){
                    if (TextUtils.equals(bean.getType(),TYPE_BANNER)){
                        switch (bean.getType()){
                            case TYPE_GDT:
                                if(tencentAd==null){
                                    tencentAd=new TencentAd(bean.getAppId());
                                }
                                tencentAd.showBanner(context,viewGroup,getInfoLp(context),bean.getLocationId(),onShowResultListener);
                                break;
                            case TYPE_BD:
                                if(baiduAd==null){
                                    baiduAd=new BaiduAd(context,bean.getAppId());
                                }
                                baiduAd.showBanner(viewGroup,getInfoLp(context),bean.getLocationId(),onShowResultListener);
                                break;
                        }
                        return;
                    }
                }
            }
        }
    }
    public void showBannerForRedbag(ViewGroup viewGroup,OnShowResultListener onShowResultListener){
        if (adInfo==null){
            for (AdInfo.VendorAdListBeanX beanX:adInfo.getVendorAdList()){
                for (AdInfo.VendorAdListBeanX.VendorAdListBean bean:beanX.getVendorAdList()){
                    if (TextUtils.equals(bean.getType(),TYPE_BANNER)){
                        switch (bean.getType()){
                            case TYPE_GDT:
                                if(tencentAd==null){
                                    tencentAd=new TencentAd(bean.getAppId());
                                }
                                tencentAd.showBanner(context,viewGroup,getRedbagLp(context),bean.getLocationId(),onShowResultListener);
                                break;
                            case TYPE_BD:
                                if(baiduAd==null){
                                    baiduAd=new BaiduAd(context,bean.getAppId());
                                }
                                baiduAd.showBanner(viewGroup,getRedbagLp(context),bean.getLocationId(),onShowResultListener);
                                break;
                        }
                        return;
                    }
                }
            }
        }
    }
    public void showVideo(OnVideoShowResultListener listener){
       if (vungleAd!=null){
           vungleAd=new VungleAd(context);
       }
        vungleAd.showVideo(listener);
    }


    public ViewGroup.LayoutParams getInfoLp(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width / 20 * 3;
        return new ViewGroup.LayoutParams(width, height);
    }
    public ViewGroup.LayoutParams getRedbagLp(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width / 3 * 2;
        return new ViewGroup.LayoutParams(width, height);
    }

}
