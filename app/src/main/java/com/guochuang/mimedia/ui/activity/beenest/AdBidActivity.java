package com.guochuang.mimedia.ui.activity.beenest;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.presenter.AdBidPresenter;
import com.guochuang.mimedia.mvp.view.AdBidView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class AdBidActivity extends MvpActivity<AdBidPresenter> implements AdBidView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.mv_content)
    MapView mvContent;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_my_ad)
    TextView tvMyAd;
    @BindView(R.id.iv_location)
    ImageView ivLocation;

    BaiduMap bm;
    LocationClient mLocationClient;
    LocationClientOption option;
    List<Marker> adOptions = new ArrayList<>();
    BDLocation currentLoc;

    BaiduMap.OnMarkerClickListener lisener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.e("onMarkerClick: ", marker.getId());
//            Bundle mb = marker.getExtraInfo();
//            String value = mb.getString(Constant.RED_PACKET_TYPE);
//            if (TextUtils.equals(value, Constant.MAP_MARKER_SPOT)) {
//                long nestLocationId = mb.getLong(Constant.NESTLOCATIONID, 0);
//                String latitude = mb.getString(Constant.LATITUDE);
//                String longitude = mb.getString(Constant.LONGITUDE);
//                if (nestLocationId > 0) {
//                    IntentUtils.startBidBrandActivity(AdBidActivity.this, nestLocationId, latitude, longitude);
//                }
//            }
            return true;
        }
    };





    BaiduMap.OnMapClickListener mMapClickListener = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {

            toPosition(latLng);
            showLoadingDialog(null);
            mvpPresenter.getNestSpot(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));

        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {

            toPosition(mapPoi.getPosition());
            showLoadingDialog(null);
            mvpPresenter.getNestSpot(String.valueOf(mapPoi.getPosition().latitude), String.valueOf(mapPoi.getPosition().longitude));
            return false;
        }
    };

    @Override
    protected AdBidPresenter createPresenter() {
        return new AdBidPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ad_bid;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.ad_bid);
        tvText.setText(R.string.bid_explain);
        bm = mvContent.getMap();
        mvContent.showZoomControls(false);
        mvContent.showScaleControl(false);
        mvContent.removeViewAt(1);
        new RxPermissions(this).request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {//全部授权
                    startLocation();
                }
            }
        });
        bm.setOnMapClickListener(mMapClickListener);

        setTextMarquee(tvTip);
    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mvContent.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mvContent.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mvContent.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvContent.onDestroy();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (bm != null) {
            bm.removeMarkerClickListener(lisener);
            bm.setOnMapClickListener(null);
            bm.clear();
            bm = null;
        }

        if (mvContent != null) {
            mvContent = null;
        }
        if(lisener != null) {
            lisener = null;
        }
        if(mMapClickListener != null) {
            mMapClickListener = null;
        }

    }


    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_my_ad, R.id.iv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                IntentUtils.startWebActivity(this, null, Constant.URL_FENGCHAO_JINGPAI);
                break;
            case R.id.tv_my_ad:
                startActivity(new Intent(this, MyAdActivity.class));
                break;
            case R.id.iv_location:
                if (AntiShake.check(view.getId()))
                    return;
                if (mLocationClient != null) {
                    startLocation();
                }
                break;
        }
    }

    public void startLocation() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(App.getInstance());
            option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setCoorType(String.valueOf(CoordType.BD09LL));
//            option.setScanSpan(3000);
            option.setOpenGps(true);
            option.setLocationNotify(false);
            option.setIgnoreKillProcess(false);
            option.setEnableSimulateGps(false);
            mLocationClient.setLocOption(option);
            mLocationClient.registerLocationListener(locationListener);
            //开启定位
        }
        mLocationClient.start();
    }

    BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LogUtil.d(bdLocation.getLongitude() + "=" + bdLocation.getLatitude());
            mLocationClient.stop();
            if ("4.9E-324".equals(String.valueOf(bdLocation.getLatitude()))) {
                return;
            }
            mvpPresenter.getNestSpot(String.valueOf(bdLocation.getLatitude()), String.valueOf(getPref().getLongitude()));
            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(16f);
            bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            currentLoc = bdLocation;
        }
    };

    public void addMarker(List<NestLocation> data) {

        for (int i = 0; i <data.size(); i++){
            bm.removeMarkerClickListener(lisener);
        }


        bm.clear();
        if (currentLoc != null) {
            LatLng point = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_bid_location);
            Bundle nowbundle = new Bundle();
            nowbundle.putString(Constant.RED_PACKET_TYPE, Constant.TYPE_NOW);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap).extraInfo(nowbundle);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) bm.addOverlay(option);
        }
        adOptions.clear();
        for (int i = 0; i < data.size(); i++) {
            BitmapDescriptor bitmap;
            Bundle bundle = new Bundle();
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_ad_marker);
            bundle.putSerializable(Constant.RED_PACKET_TYPE, Constant.MAP_MARKER_SPOT);
            bundle.putLong(Constant.NESTLOCATIONID, data.get(i).getNestLocationId());
            bundle.putString(Constant.LATITUDE, String.valueOf(data.get(i).getLatitude()));
            bundle.putString(Constant.LONGITUDE, String.valueOf(data.get(i).getLongitude()));
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude()))
                    .icon(bitmap).extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);

            Marker marker = (Marker) bm.addOverlay(option);
            adOptions.add(marker);
//            adOptions.add(option);

            bm.setOnMarkerClickListener(lisener);
        }

        Log.e( "addMarker: ", "设置覆盖物的监听事件");
//        bm.removeMarkerClickListener(lisener);

//        bm.addOverlays(adOptions);


        //移动后点发生变化

    }

    @Override
    public void setData(List<NestLocation> data) {
        closeLoadingDialog();
        if (data != null && data.size() > 0) {
            addMarker(data);
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    private void toPosition(LatLng pt) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(pt);
        bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
}
