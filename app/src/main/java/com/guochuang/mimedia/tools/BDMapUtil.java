package com.guochuang.mimedia.tools;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;


import java.util.ArrayList;
import java.util.List;

public class BDMapUtil {

    private BaiduMap bm;
    private boolean isFirstLoc = true;
    private LocaLatlon locaLatlon;
    private MyLocationListener locationListener;
    private LocationClient mLocationClient;     //声明LocationClient类
    private LocationClientOption option;
    private int LOCA_TIME = 3000;
    private BDLocation location;
    private SuggestionSearch suggestionSearch;
    private GeoCoder geoCoder;
    private Context context;

    public BDMapUtil(Context context) {
        this.context = context;
        this.LOCA_TIME = 0;
    }

    public BDMapUtil(BaiduMap bm, Context context) {
        this.context = context;
        this.bm = bm;
    }

    public void setFirstLoc(boolean firstLoc) {
        isFirstLoc = firstLoc;
    }

    public void loaction(LocaLatlon locaLatlon) {
        this.locaLatlon = locaLatlon;
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(App.getInstance());
            option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            //可选，设置定位模式，默认高精度
            //LocationMode.Hight_Accuracy：高精度；
            //LocationMode. Battery_Saving：低功耗；
            //LocationMode. Device_Sensors：仅使用设备；

            option.setCoorType("bd09ll");
            //可选，设置返回经纬度坐标类型，默认gcj02
            //gcj02：国测局坐标；
            //bd09ll：百度经纬度坐标；
            //bd09：百度墨卡托坐标；
            //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

            option.setScanSpan(LOCA_TIME);
            //可选，设置发起定位请求的间隔，int类型，单位ms
            //如果设置为0，则代表单次定位，即仅定位一次，默认为0
            //如果设置非0，需设置1000ms以上才有效

            option.setOpenGps(true);
            //可选，设置是否使用gps，默认false
            //使用高精度和仅用设备两种定位模式的，参数必须设置为true

            option.setLocationNotify(true);
            //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

            option.setIgnoreKillProcess(false);
            //可选，定位SDK内部是一个service，并放到了独立进程。
            //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

//        option.SetIgnoreCacheException(false);
//        //可选，设置是否收集Crash信息，默认收集，即参数为false

            option.setWifiCacheTimeOut(5 * 60 * 1000);
            //可选，7.2版本新增能力
            //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

            option.setEnableSimulateGps(false);
            //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

            mLocationClient.setLocOption(option);
            //mLocationClient为第二步初始化过的LocationClient对象
            //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
            //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

            locationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(locationListener);    //注册监听函数
            //开启定位
            mLocationClient.start();
        } else {
            mLocationClient.restart();
        }
    }

    public void locRestart(){
        mLocationClient.restart();
    }

    public boolean getLocState(){
        return mLocationClient.isStarted();
    }

    public void stopLocation() {
        if (getLocState()) {
            mLocationClient.stop();
        }
    }

    public void search(String address, String city, final PositionNow positionNow) {
        if (suggestionSearch == null) {
            suggestionSearch = SuggestionSearch.newInstance();
            OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
                @Override
                public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                    positionNow.nowPosition(suggestionResult);
                }
            };
            suggestionSearch.setOnGetSuggestionResultListener(listener);
        }
        suggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(address).city(city));
    }

    public void searchDestroy() {
        if (suggestionSearch != null) {
            suggestionSearch.destroy();
        }
    }

    public void positionSearch(LatLng latLng, final PositionAddress positionAddress) {
        if (geoCoder == null) {
            geoCoder = GeoCoder.newInstance();
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    positionAddress.nowPositionAddress(reverseGeoCodeResult);
                }
            });
        }
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    public void positionSearchDestroy() {
        if (geoCoder != null) {
            geoCoder.destroy();
        }
    }

//    public void addMarker(TakeRedPacketBean takeRedPacketBean) {
//        List<OverlayOptions> options = new ArrayList<>();
//        for (int i = 0; i < takeRedPacketBean.getReturnData().getRandomSpot().size(); i++) {
//            BitmapDescriptor bitmap;
//            Bundle bundle = new Bundle();
//            switch (takeRedPacketBean.getReturnData().getRandomSpot().get(i).getType()) {
//                case 1:
//                    bitmap = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_map_redbag_system);
//                    bundle.putString(ConfigUtil.RED_PACKET_TYPE,
//                            ConfigUtil.TYPE_ONE + "");
//                    break;
//                case 2:
//                    bitmap = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_map_redbag_person);
//                    bundle.putString(ConfigUtil.RED_PACKET_TYPE,
//                            ConfigUtil.TYPE_TWE + "");
//                    break;
//                case 3:
//                    bitmap = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_map_redbag_shop);
//                    bundle.putString(ConfigUtil.RED_PACKET_TYPE,
//                            ConfigUtil.TYPE_THREE + "");
//                    break;
//                case 6:
//                    bitmap = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_redbag_pwd);
//                    bundle.putString(ConfigUtil.RED_PACKET_TYPE,
//                            ConfigUtil.TYPE_SIX + "");
//                    break;
//                default:
//                    bitmap = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_map_redbag_system);
//                    break;
//            }
//            bundle.putString(ConfigUtil.RED_PACKET_ID,
//                    takeRedPacketBean.getReturnData().getRandomSpot().get(i).getRedPacketId() + "");
//            bundle.putString(ConfigUtil.RED_PACKET_PROVIDE_ICON,
//                    takeRedPacketBean.getReturnData().getRandomSpot().get(i).getIcon() + "");
//            bundle.putString(ConfigUtil.RED_PACKET_PROVIDE_NAME,
//                    takeRedPacketBean.getReturnData().getRandomSpot().get(i).getName());
//            bundle.putString(ConfigUtil.RED_PACKET_EXPLAIN,
//                    takeRedPacketBean.getReturnData().getRandomSpot().get(i).getExplain());
//            OverlayOptions option = new MarkerOptions()
//                    .position(new LatLng(Double.parseDouble(takeRedPacketBean.getReturnData().getRandomSpot().get(i).getLat()),
//                            Double.parseDouble(takeRedPacketBean.getReturnData().getRandomSpot().get(i).getLng())))
//                    .icon(bitmap).extraInfo(bundle);
////            .animateType(MarkerOptions.MarkerAnimateType.jump)
//            options.add(option);
//        }
//        //在地图上添加Marker，并显示
//        bm.addOverlays(options);
//    }
//
//    public void showInMap(TakeRedPacketBean takeRedPacketBean) {
//        //定义Maker坐标点
//        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_location);
//        bm.clear();
//        Bundle nowbundle = new Bundle();
//        nowbundle.putString(ConfigUtil.RED_PACKET_ID, "now");
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap).extraInfo(nowbundle);
//        //在地图上添加Marker，并显示
//        bm.addOverlay(option);
//
//        OverlayOptions ooCircle = new CircleOptions().fillColor(0x1ebababa)
//                .center(new LatLng(location.getLatitude(), location.getLongitude())).stroke(new Stroke(1, 0xff9f9f9f))
//                .radius(takeRedPacketBean.getReturnData().getSoRange());
//        bm.addOverlay(ooCircle);
//
//        Bundle sharebundle = new Bundle();
//        sharebundle.putString(ConfigUtil.RED_PACKET_ID, "share");
//        View view = LayoutInflater.from(KSHBApplication.appContext).
//                inflate(R.layout.view_get_packet_expand_scope, null);
//        LinearLayout allLl = view.findViewById(R.id.expand_scope_all_ll);
//        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        allLl.measure(width, height);
//        View bgView = view.findViewById(R.id.expand_scope_v);
//        int i = allLl.getMeasuredWidth();
//        ViewGroup.LayoutParams lp = bgView.getLayoutParams();
//        lp.width = (int) (allLl.getMeasuredWidth() / (float) takeRedPacketBean.getReturnData().getGroomTotal()) * takeRedPacketBean.getReturnData().getStopGroom();
//        if (lp.width != 0 && lp.width < 80) {
//            lp.width = 80;
//        }
////        bgView.setLayoutParams(new RelativeLayout.LayoutParams(
////                (int) (allLl.getMeasuredWidth() / 10.0) * 3, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        addMarker(takeRedPacketBean);
//
//        ((TextView) view.findViewById(R.id.expand_scope_tv)).setText(takeRedPacketBean.getReturnData().getSoRange() + "米");
//        OverlayOptions shareOption = new MarkerOptions().
//                position(new LatLng(location.getLatitude() - 0.010, location.getLongitude())).
//                icon(BitmapDescriptorFactory
//                        .fromView(view)).extraInfo(sharebundle);
//        bm.addOverlay(shareOption);
//    }

    /**
     * 边界检索
     */
    public void districtSerach(String locaName) {
        DistrictSearch mDistrictSearch = DistrictSearch.newInstance();//初始化行政区检索
        mDistrictSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {

            @Override
            public void onGetDistrictResult(DistrictResult districtResult) {
                districtResult.getCenterPt();//获取行政区中心坐标点
                districtResult.getCityName();//获取行政区域名称
                List<List<LatLng>> polyLines = districtResult.getPolylines();//获取行政区域边界坐标点
                //边界就是坐标点的集合，在地图上画出来就是多边形图层。有的行政区可能有多个区域，所以会有多个点集合。

                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(districtResult.getCenterPt()).zoom(10.0f);
                bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                OverlayOptions titleOo = new MarkerOptions().
                        title(districtResult.getCityName()).
                        position(districtResult.getCenterPt()).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                bm.addOverlay(titleOo);
                for (List<LatLng> latLngs : polyLines) {
                    OverlayOptions ooPolyline = new PolylineOptions().width(7).color(0xAAFF0000).points(latLngs);
                    //在地图上画出线条图层，mPolyline：线条图层
                    bm.addOverlay(ooPolyline);
                }
            }

        });//设置回调监听

        DistrictSearchOption districtSearchOption = new DistrictSearchOption();
        districtSearchOption.cityName(locaName);//检索城市名称
//        districtSearchOption.districtName(districtStr);//检索的区县名称
        mDistrictSearch.searchDistrict(districtSearchOption);//请求行政区数据
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeServerError) {
                Toast.makeText(App.getInstance(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                return;
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Toast.makeText(App.getInstance(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
                return;
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                Toast.makeText(App.getInstance(), "请给与定位权限", Toast.LENGTH_SHORT).show();
//                CDialog.oneBtnDialog(context, "请给与定位权限", "去开启", new CDialog.OnBtnDialogListener() {
//                    @Override
//                    public void confirm() {
//                        JumpUtil.getAppDetailSettingIntent();
//                    }
//                });
                return;
            }
            if (bm == null) {
                locaLatlon.nowPosition(location);
                return;
            }
            BDMapUtil.this.location = location;
            locaLatlon.nowPosition(location);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(16.0f);
                bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//                if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                    // GPS定位结果
//                    Toast.makeText(KSHBApplication.appContext, location.getAddrStr(), Toast.LENGTH_SHORT).show();
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                    // 网络定位结果
//                    Toast.makeText(KSHBApplication.appContext, location.getAddrStr(), Toast.LENGTH_SHORT).show();
//
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
//                    // 离线定位结果
//                    Toast.makeText(KSHBApplication.appContext, location.getAddrStr(), Toast.LENGTH_SHORT).show();
//
//                } else

            }
        }
    }

    public interface LocaLatlon {
        void nowPosition(BDLocation location);
    }

    public interface PositionNow {
        void nowPosition(SuggestionResult suggestionResult);
    }

    public interface PositionAddress {
        void nowPositionAddress(ReverseGeoCodeResult reverseGeoCodeResult);
    }

}
