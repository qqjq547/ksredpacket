package com.guochuang.mimedia.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.HomeRegion;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.model.NestHomeAd;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.presenter.RedbagPresenter;
import com.guochuang.mimedia.mvp.view.RedbagView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.beenest.AdBidActivity;
import com.guochuang.mimedia.ui.activity.city.CityActivity;
import com.guochuang.mimedia.ui.activity.MainActivity;
import com.guochuang.mimedia.ui.activity.common.MyCaptureActivity;
import com.guochuang.mimedia.ui.activity.common.ShareActivity;
import com.guochuang.mimedia.ui.activity.redbag.SquareActivity;
import com.guochuang.mimedia.ui.activity.user.IdentifyActivity;
import com.guochuang.mimedia.ui.activity.user.UpgradeAgentActivity;
import com.guochuang.mimedia.ui.dialog.OpenRedbagDialog;
import com.guochuang.mimedia.ui.dialog.RedbagTypeDialog;
import com.guochuang.mimedia.view.HoneyCombView;
import com.guochuang.mimedia.view.ScrollTextView;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.internal.operators.CompletableOnSubscribeConcat;

public class RedbagFragment extends MvpFragment<RedbagPresenter> implements RedbagView {

    @BindView(R.id.tv_start)
    ImageView tvStart;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.mv_redbag)
    MapView mvRedbag;
    @BindView(R.id.lin_notice)
    LinearLayout linNotice;
    @BindView(R.id.stv_notice)
    ScrollTextView stvNotice;
    @BindView(R.id.lin_fl_head)
    LinearLayout linFlHead;
    @BindView(R.id.tv_city_owner)
    TextView tvCityOwner;
    @BindView(R.id.lin_city_owner)
    LinearLayout linCityOwner;
    @BindView(R.id.iv_city_owner_avatar)
    ImageView ivCityOwnerAvatar;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.lin_upgrade_agent)
    LinearLayout linUpgradeAgent;
    @BindView(R.id.hcv_ad)
    HoneyCombView hcvAd;

    TextView tvScope;
    BaiduMap bm;
    LocationClient mLocationClient;
    LocationClientOption option;
    OpenRedbagDialog openRedbagDialog;
    Redbag redbag;
    List<OverlayOptions> poolOptions = new ArrayList<>();
    List<OverlayOptions> locOptions = new ArrayList<>();
    Marker optMarker;
    HomeRegion homeRegion;
    int kilometre = 1000;
    boolean isDelay = false;//标记延时的这个时间段
    boolean isFirstLocation = true;
    View vScope;
    Animation rotateAnim;
    BaiduMap.OnMarkerClickListener onMarkerClickListener;
    boolean showNest = false;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isDelay = false;
            if (isVisible()) {
                mLocationClient.start();
            }
        }
    };

    @Override
    protected RedbagPresenter createPresenter() {
        return new RedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_redbag;
    }

    @Override
    public void initViewAndData() {
        tvText.setText(R.string.square);
        if (App.getInstance().getUserInfo() != null) {
            tvTitle.setText(getPref().getString(PrefUtil.COIN, ""));
        }
        bm = mvRedbag.getMap();
        mvRedbag.showZoomControls(false);
        mvRedbag.showScaleControl(false);
        mvRedbag.removeViewAt(1);
        vScope = LayoutInflater.from(getContext()).inflate(R.layout.layout_redpacket_scope, null);
        tvScope = vScope.findViewById(R.id.tv_scope);
        new RxPermissions(getActivity()).request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {//全部授权
                    startLocation();
                }
            }
        });
        setUserRole(getPref().getInt(PrefUtil.USER_ROLE, Constant.USER_ROLE_FANS));
        kilometre = getPref().getInt(PrefUtil.KILOMETRE, 1000);
        mvpPresenter.getWalletCoinAndMoney();
        mvpPresenter.getScrollBar();
        mvpPresenter.getUserRole();
        onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                optMarker = marker;
                Bundle mb = marker.getExtraInfo();
                switch (mb.getString(Constant.RED_PACKET_TYPE)) {
                    case Constant.TYPE_NOW:
                        break;
                    case Constant.TYPE_REDBAG:
                        redbag = (Redbag) mb.getSerializable(Constant.RED_PACKET_DATA);
                        if (redbag == null) {
                            showShortToast(R.string.not_find_redbag_data);
                            return false;
                        }
                        openRedbagDialog = new OpenRedbagDialog(getContext());
                        //是否是拖动开红包
                        int isDragStr = getPref().getInt(PrefUtil.ISDRAG, 0);
                        if (Constant.ISDRAG==isDragStr) {
                            openRedbagDialog.setDrag(true);
                        }
                            openRedbagDialog.setOnOpenResultListener(new OpenRedbagDialog.OnOpenResultListener() {
                                @Override
                                public void onOpenResult(String password) {
                                    optMarker.remove();
                                    showLoadingDialog(null);
                                    if (redbag.getRoleType().equals(Constant.ROLETYPE_SYSTEM)) {
                                        mvpPresenter.redPacketOpen(getPref().getLatitude(), getPref().getLongitude(), redbag.getUuid());
                                    } else {
                                        if ((redbag.getType().equals(Constant.RED_PACKET_TYPE_VIDEO) && redbag.getSurveyId() > 0) || redbag.getType().equals(Constant.RED_PACKET_TYPE_SURVEY)) {
                                            mvpPresenter.redPacketPoolOpenSurvey(getPref().getLatitude(), getPref().getLongitude(), redbag.getUuid());
                                        } else {
                                            mvpPresenter.redPacketPoolOpen(getPref().getLatitude(), getPref().getLongitude(), redbag.getUuid(), password);
                                        }
                                    }

                                }
                            });
                        LogUtil.d(redbag.toString());
                        openRedbagDialog.setRedbag(redbag);
                        openRedbagDialog.show();
                        break;
                }
                return false;
            }
        };
        bm.setOnMarkerClickListener(onMarkerClickListener);
        setUserRole(getPref().getInt(PrefUtil.USER_ROLE, 0));
        setHomeAd(new ArrayList<NestHomeAd>());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mvRedbag.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mvRedbag.onResume();
        bm.setOnMarkerClickListener(onMarkerClickListener);
        if (mLocationClient != null) {
            if (!TextUtils.isEmpty(getPref().getLatitude())) {
                mvpPresenter.getHomeRegion(getPref().getLatitude(), getPref().getLongitude());
            }
            onHiddenChanged(false);
        }
        mvpPresenter.getKilometre();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mvpPresenter.getWalletCoinAndMoney();
            if (mLocationClient != null) {
                if (!isDelay) {
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mvRedbag.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mvRedbag.onDestroy();
    }

    @OnClick({R.id.iv_scan, R.id.tv_text, R.id.lin_city_owner, R.id.iv_city_owner_avatar, R.id.tv_start, R.id.iv_share, R.id.iv_refresh, R.id.lin_upgrade_agent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                new RxPermissions(getActivity()).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            if (getPref().getInt(PrefUtil.IDENTITY,0)==0){
                                startActivity(new Intent(getActivity(), IdentifyActivity.class));
                            }else{
                                startActivity(new Intent(getActivity(), MyCaptureActivity.class));
                            }
                        } else {
                            showShortToast(R.string.get_camera_permission);
                        }
                    }
                });
                break;
            case R.id.tv_text:
                startActivity(new Intent(getActivity(), SquareActivity.class));
                break;
            case R.id.lin_city_owner:
            case R.id.iv_city_owner_avatar:
                startActivity(new Intent(getActivity(), CityActivity.class));
                break;
            case R.id.tv_start:
                new RedbagTypeDialog(getActivity(), new RedbagTypeDialog.OnItemClickListener() {
                    @Override
                    public void onRandom() {
                        IntentUtils.startEditRedbagActivity(getActivity(), Constant.RED_PACKET_TYPE_RANDOM);
                    }

                    @Override
                    public void onPassword() {
                        IntentUtils.startEditRedbagActivity(getActivity(), Constant.RED_PACKET_TYPE_PASSWORD);
                    }

                    @Override
                    public void onLucky() {
                        IntentUtils.startEditRedbagActivity(getActivity(), Constant.RED_PACKET_TYPE_LUCKY);
                    }

                    @Override
                    public void onVideo() {
                        IntentUtils.startEditRedbagActivity(getActivity(), Constant.RED_PACKET_TYPE_VIDEO);
                    }

                    @Override
                    public void onQuestion() {
                        IntentUtils.startEditRedbagActivity(getActivity(), Constant.RED_PACKET_TYPE_SURVEY);
                    }
                }).show();
                break;
            case R.id.iv_refresh:
                if (AntiShake.check(view.getId()))
                    return;
                if (mLocationClient != null) {
                    startAnim();
                    handler.removeMessages(0);
                    handler.sendEmptyMessage(0);
                    mvpPresenter.getHomeRegion(getPref().getLatitude(), getPref().getLongitude());
                    mvpPresenter.getKilometre();
                    if (showNest) {
                        mvpPresenter.getHomeAd(getPref().getLatitude(), getPref().getLongitude());
                    }
                }
                break;
            case R.id.iv_share:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            case R.id.lin_upgrade_agent:
                startActivity(new Intent(getActivity(), UpgradeAgentActivity.class));
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startLocation();
                    }
                }, 1000);
                return;
            }
            getPref().setString(PrefUtil.LATITUDE, String.valueOf(bdLocation.getLatitude()));
            getPref().setString(PrefUtil.LONGITUDE, String.valueOf(bdLocation.getLongitude()));
            if (isFirstLocation) {
                isFirstLocation = false;
                mvpPresenter.getHomeRegion(getPref().getLatitude(), getPref().getLongitude());
                mvpPresenter.userStatistics(getPref().getLatitude(), getPref().getLongitude());
                mvpPresenter.getIsQualified(getPref().getLatitude(), getPref().getLongitude());
            }
            if (!isHidden() && isResumed()) {
                isDelay = true;
                handler.sendEmptyMessageDelayed(0, 30000);
            }
            mvpPresenter.redPacketGet(String.valueOf(bdLocation.getLatitude()), String.valueOf(bdLocation.getLongitude()));
            mvpPresenter.getLocationRedabg(String.valueOf(bdLocation.getLatitude()), String.valueOf(bdLocation.getLongitude()));
            if (showNest) {
                mvpPresenter.getHomeAd(String.valueOf(bdLocation.getLatitude()), String.valueOf(bdLocation.getLongitude()));
            }
            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(CommonUtil.getLevel(kilometre));
            bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


            LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_location);
            bm.clear();
            Bundle nowbundle = new Bundle();
            nowbundle.putString(Constant.RED_PACKET_TYPE, Constant.TYPE_NOW);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap).extraInfo(nowbundle);
            //在地图上添加Marker，并显示
            bm.addOverlay(option);

            OverlayOptions ooCircle = new CircleOptions().fillColor(0x1ebababa)
                    .center(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())).stroke(new Stroke(1, 0xff9f9f9f))
                    .radius(kilometre);
            bm.addOverlay(ooCircle);
            if (isAdded()) {
                tvScope.setText(String.format(getString(R.string.format_mill_unit), kilometre));
            }
        }
    };

    @Override
    public void setSystemRedbag(List<Redbag> data) {
        closeAnim();
        if (data != null) {
            if (isVisible()) {
                addMarker(data, poolOptions);
            }
        }
    }

    @Override
    public void setLocationRedbag(List<Redbag> data) {
        closeAnim();
        if (data != null) {
            if (isVisible()) {
                addMarker(data, locOptions);
            }
        }
    }

    @Override
    public void setRedbagDetail(RedbagDetail redbagDetail) {
        closeLoadingDialog();
        //保存数据
        getPref().setInt(PrefUtil.ISDRAG, redbagDetail.getNextValidate());
        if (redbagDetail != null) {
            IntentUtils.startRedbagDetailActivity(getActivity(), redbagDetail, redbag.getUuid(), redbag.getRoleType(),redbag.getType());
        }
    }

    @Override
    public void setCoinAndMoney(MyKsb data) {
        tvTitle.setText(String.valueOf(data.getCoin()));
        getPref().setString(PrefUtil.COIN, String.valueOf(data.getCoin()));
        getPref().setString(PrefUtil.MONEY, String.valueOf(data.getMoney()));
    }

    @Override
    public void setScrollbar(List<String> data) {
        if (data == null || data.size() == 0) {
            linNotice.setVisibility(View.GONE);
            return;
        }
        stvNotice.setScroll(true);
        stvNotice.setTextSpeed(5);
        stvNotice.setTextSize(CommonUtil.sp2px(getContext(), 13));
        stvNotice.setTextColor(getResources().getColor(R.color.text_notice));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            builder.append(data.get(i));
            for (int j = 0; j < 30; j++) {
                builder.append("  ");
            }
        }
        stvNotice.setText(builder.toString());
    }

    @Override
    public void setUserRole(Integer data) {
        if (data != null) {
            getPref().setInt(PrefUtil.USER_ROLE, 0);
            if (data.intValue() >= Constant.USER_ROLE_AGENT) {
                linUpgradeAgent.setVisibility(View.GONE);
            } else {
                linUpgradeAgent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setHomeRegion(HomeRegion data) {
        if (data != null && data != homeRegion) {
            homeRegion = data;
            GlideImgManager.loadCircleImage(getActivity(), data.getAvatar(), ivCityOwnerAvatar, R.drawable.ic_city_owner_default);
            tvCityOwner.setText(data.getNickName());
        }
    }

    @Override
    public void setKilometre(Integer data) {
        if (data != null & data.intValue() != kilometre) {
            kilometre = data;
            getPref().setInt(PrefUtil.KILOMETRE, kilometre);
            if (tvScope != null) {
                tvScope.setText(String.format(getString(R.string.format_mill_unit), kilometre));
            }
        }
    }

    @Override
    public void setHomeAd(List<NestHomeAd> data) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linFlHead.getLayoutParams();
//        for (int i=0;i<30;i++){
//            NestHomeAd ad=new NestHomeAd();
//            ad.setCoverPicture("https://upload.jianshu.io/users/upload_avatars/4174308/540285e2-5be5-483a-9259-db485564a4b0.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
//            ad.setShortMsg("name="+(i+1));
//            data.add(ad);
//        }
        if (data != null && data.size() > 0) {
            lp.topMargin = CommonUtil.dip2px(getContext(), 140);
            sethoneyData(data);
        } else {
            lp.topMargin = CommonUtil.dip2px(getContext(), 30);
        }
        linFlHead.setLayoutParams(lp);
    }

    @Override
    public void setIsQualified(Boolean data) {
        if (data != null && data.booleanValue()) {
            ((MainActivity) getActivity()).startNestAd();
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        closeAnim();
        ((MainActivity) getActivity()).setError(msg);
    }

    @Override
    public void setRedbagInvalid() {
        ivRefresh.callOnClick();
    }

    public void addMarker(List<Redbag> redbagList, List<OverlayOptions> options) {
        options.clear();
        for (int i = 0; i < redbagList.size(); i++) {
            BitmapDescriptor bitmap;
            Bundle bundle = new Bundle();
            switch (redbagList.get(i).getRoleType()) {
                case Constant.ROLETYPE_SYSTEM:
                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_system);
                    break;
                case Constant.ROLETYPE_MERCHANT:
                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_shop);
                    break;
                case Constant.ROLETYPE_PERSON:
                    if (redbagList.get(i).getType().equals(Constant.RED_PACKET_TYPE_RANDOM)) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_person);
                    } else if (redbagList.get(i).getType().equals(Constant.RED_PACKET_TYPE_PASSWORD)) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_pwd);
                    }else if(redbagList.get(i).getType().equals(Constant.RED_PACKET_TYPE_VIDEO)) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_video);
                    } else if(redbagList.get(i).getType().equals(Constant.RED_PACKET_TYPE_SURVEY)){
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_survey);
                    }else {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_person);
                    }
                    break;
                case Constant.ROLETYPE_ADMIN:
                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_shop);
                    break;
                default:
                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_redbag_system);
                    break;
            }
            bundle.putSerializable(Constant.RED_PACKET_TYPE, Constant.TYPE_REDBAG);
            bundle.putSerializable(Constant.RED_PACKET_DATA, redbagList.get(i));
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(redbagList.get(i).getLatitude()),
                            Double.parseDouble(redbagList.get(i).getLongitude())))
                    .icon(bitmap).extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            options.add(option);
        }
        bm.addOverlays(options);
    }

    public void refreshWallet() {
        mvpPresenter.getWalletCoinAndMoney();
    }

    public void refreshUserRole() {
        mvpPresenter.getUserRole();
    }

    public void startAnim() {
        if (rotateAnim == null) {
            rotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.refresh_rotate);
            LinearInterpolator lin = new LinearInterpolator();
            rotateAnim.setInterpolator(lin);
            ivRefresh.startAnimation(rotateAnim);
        }
    }

    public void closeAnim() {
        if (rotateAnim != null) {
            rotateAnim.cancel();
            rotateAnim = null;
        }
    }

    public void sethoneyData(final List<NestHomeAd> honeyArr) {
        hcvAd.setVisibility(View.VISIBLE);
        hcvAd.setData(honeyArr, new HoneyCombView.OnMenuClickListener() {
            @Override
            public void onClick(NestHomeAd data) {
                IntentUtils.startBeeNestActivity(getActivity(), data.getNestInfoId(), data.getNestLocationId());
            }

            @Override
            public void onSendAd() {
                clearMarker();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), AdBidActivity.class));
                    }
                }, 500);
//                startActivity(new Intent(getActivity(),AdBidActivity.class));
            }
        });
    }

    public void clearMarker() {
        bm.removeMarkerClickListener(onMarkerClickListener);
        bm.clear();
    }

    public void openNestAd() {
        showNest = true;
        if (!TextUtils.isEmpty(getPref().getString(PrefUtil.LATITUDE, ""))) {
            mvpPresenter.getHomeAd(PrefUtil.getInstance().getLatitude(), getPref().getLongitude());
        }
    }
}
