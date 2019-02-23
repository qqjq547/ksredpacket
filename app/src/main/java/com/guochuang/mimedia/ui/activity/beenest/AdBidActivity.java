package com.guochuang.mimedia.ui.activity.beenest;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
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
    @BindView(R.id.tv_my_ad)
    TextView tvMyAd;
    @BindView(R.id.iv_location)
    ImageView ivLocation;

    BaiduMap bm;
    LocationClient mLocationClient;
    LocationClientOption option;
    List<OverlayOptions> adOptions = new ArrayList<>();
    List<LatLng> markerArr=new ArrayList<>();
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
        bm.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle mb = marker.getExtraInfo();
               String value=mb.getString(Constant.RED_PACKET_TYPE);
               if (TextUtils.equals(value,Constant.MAP_MARKER_SPOT)){
                   long nestLocationId=mb.getLong(Constant.NESTLOCATIONID,0);
                   if(nestLocationId>0){
                       startActivity(new Intent(AdBidActivity.this,BidBrandActivity.class).putExtra(Constant.NESTLOCATIONID,nestLocationId));
                   }
               }
                return false;
            }
        });
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
    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_my_ad, R.id.iv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                IntentUtils.startWebActivity(this,null,Constant.URL_FENGCHAO_JINGPAI);
                break;
            case R.id.tv_my_ad:
                startActivity(new Intent(this,MyAdActivity.class));
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
            mvpPresenter.getNestSpot(String.valueOf(bdLocation.getLatitude()),String.valueOf(getPref().getLongitude()));
            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(16f);
            bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_bid_location);
            bm.clear();
            Bundle nowbundle = new Bundle();
            nowbundle.putString(Constant.RED_PACKET_TYPE, Constant.TYPE_NOW);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap).extraInfo(nowbundle);
            //在地图上添加Marker，并显示
            bm.addOverlay(option);

        }
    };
    public void addMarker(List<NestLocation> data) {
        adOptions.clear();
        for (int i = 0; i < data.size(); i++) {
            BitmapDescriptor bitmap;
            Bundle bundle = new Bundle();
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_ad_marker);
            bundle.putSerializable(Constant.RED_PACKET_TYPE, Constant.MAP_MARKER_SPOT);
            bundle.putLong(Constant.NESTLOCATIONID,data.get(i).getNestLocationId());
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng(data.get(i).getLatitude(),data.get(i).getLongitude()))
                    .icon(bitmap).extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            adOptions.add(option);
        }
        bm.addOverlays(adOptions);
    }

    @Override
    public void setData(List<NestLocation> data) {
        if (data!=null&&data.size()>0){
            addMarker(data);
        }
    }

    @Override
    public void setError(String msg) {

    }
}