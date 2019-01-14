package com.guochuang.mimedia.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.ui.dialog.OpenRedbagDialog;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class AdBidActivity extends MvpActivity {
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
    protected BasePresenter createPresenter() {
        return null;
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

            for (int i=0;i<30;i++){
                markerArr.add(new LatLng(bdLocation.getLatitude()-0.01d+(Math.random()*0.02d),bdLocation.getLongitude()-0.01d+(Math.random()*0.02d)));
            }
            addMarker(markerArr);
        }
    };
    public void addMarker(List<LatLng> redbagList) {
        adOptions.clear();
        for (int i = 0; i < redbagList.size(); i++) {
            BitmapDescriptor bitmap;
            Bundle bundle = new Bundle();
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_ad_marker);
            bundle.putSerializable(Constant.RED_PACKET_TYPE, Constant.TYPE_REDBAG);
            OverlayOptions option = new MarkerOptions()
                    .position(redbagList.get(i))
                    .icon(bitmap).extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            adOptions.add(option);
        }
        bm.addOverlays(adOptions);
    }
}
