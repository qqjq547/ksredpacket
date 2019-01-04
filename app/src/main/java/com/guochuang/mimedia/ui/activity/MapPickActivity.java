package com.guochuang.mimedia.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.GeoCode;
import com.guochuang.mimedia.mvp.presenter.MapPickPresenter;
import com.guochuang.mimedia.mvp.view.MapPickView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.LogUtil;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.BDMapUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.PoiInfoAdapter;
import com.guochuang.mimedia.ui.adapter.SuggestionInfoAdapter;
import com.guochuang.mimedia.view.ClearEditText;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class MapPickActivity extends MvpActivity<MapPickPresenter> implements MapPickView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.mv_pick)
    MapView mvPick;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.lin_detail)
    LinearLayout linDetail;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    BDLocation location = new BDLocation();
    BaiduMap bm;
    PoiInfoAdapter poiAdapter;
    List<GeoCode.PoisBean> poiArr = new ArrayList<>();
    SuggestionInfoAdapter suggAdapter;
    List<GeoCode.PoisBean> suggArr = new ArrayList<>();
    boolean isPoiSearch = true;
    double lat,lng;
    @Override
    protected MapPickPresenter createPresenter() {
        return new MapPickPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_map_pick;
    }

    @Override
    public void initViewAndData() {
        bm = mvPick.getMap();
        mvPick.showZoomControls(false);
        mvPick.showScaleControl(false);
        mvPick.removeViewAt(1);
        bm.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                isPoiSearch = false;
                rvSearch.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
                double lat=latLng.latitude;
                double lng=latLng.longitude;
                location.setLatitude(lat);
                location.setLongitude(lng);
                tvName.setText(null);
                toPosition(new LatLng(lat, lng));
                mvpPresenter.getGeocode(String.valueOf(lat),String.valueOf(lng),null);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                isPoiSearch = false;
                rvSearch.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
                double lat=mapPoi.getPosition().latitude;
                double lng=mapPoi.getPosition().longitude;
                location.setLatitude(lat);
                location.setLongitude(lng);
                tvName.setText(mapPoi.getName());
                toPosition(new LatLng(lat, lng));
                mvpPresenter.getGeocode(String.valueOf(lat),String.valueOf(lng),null);
                return true;
            }
        });
        lat=Double.parseDouble(getPref().getLatitude());
        lng=Double.parseDouble(getPref().getLongitude());
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword=etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)){
                        showLoadingDialog(null);
                        mvpPresenter.getGeocode(null,null,keyword);
                        CommonUtil.hideInput(MapPickActivity.this,etSearch);
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tvConfirm.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.VISIBLE);
                } else {
                    tvConfirm.setVisibility(View.VISIBLE);
                    rvSearch.setVisibility(View.GONE);
                }
            }
        });
        rvSearch.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvSearch.addItemDecoration(new VerticalDecoration(this));
        suggAdapter = new SuggestionInfoAdapter(suggArr);
        suggAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        suggAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isPoiSearch = true;
                rvSearch.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
                closeSearch();
                double lat=suggAdapter.getData().get(position).getPoint().getY();
                double lng=suggAdapter.getData().get(position).getPoint().getX();
                location.setLatitude(lat);
                location.setLongitude(lng);
                tvName.setText(poiAdapter.getData().get(position).getName());
                toPosition(new LatLng(lat, lng));
                mvpPresenter.getGeocode(String.valueOf(lat),String.valueOf(lng),null);
            }
        });
        rvSearch.setAdapter(suggAdapter);
        rvAddress.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvAddress.addItemDecoration(new VerticalDecoration(this));
        poiAdapter = new PoiInfoAdapter(poiArr);
        poiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        poiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isPoiSearch = false;
                rvSearch.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
                double lat=poiAdapter.getData().get(position).getPoint().getY();
                double lng=poiAdapter.getData().get(position).getPoint().getX();
                location.setLatitude(lat);
                location.setLongitude(lng);
                tvName.setText(poiAdapter.getData().get(position).getName());
                toPosition(new LatLng(lat, lng));
            }
        });
        rvAddress.setAdapter(poiAdapter);
        if (!TextUtils.isEmpty(getPref().getLatitude())){
            toPosition(new LatLng(Double.parseDouble(getPref().getLatitude()),Double.parseDouble(getPref().getLongitude())));
            showLoadingDialog(null);
            mvpPresenter.getGeocode(getPref().getLatitude(),getPref().getLongitude(),null);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_confirm, R.id.iv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
                if (tvName.getText().equals("")) {
                    showShortToast(R.string.pls_select_location);
                    return;
                }
                if (location.getLatitude() == 0) {
                    showShortToast(R.string.canot_get_location);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(Constant.NAME, tvName.getText().toString());
                intent.putExtra(Constant.LATITUDE, location.getLatitude());
                intent.putExtra(Constant.LONGITUDE, location.getLongitude());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_location:
//                showLocationInMap(lat,lng);
                mvpPresenter.getGeocode(String.valueOf(lat),String.valueOf(lng),null);
                break;
        }
    }

    private void toPosition(LatLng pt) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(pt);
        bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void closeSearch() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
        etSearch.clearFocus();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(MapPickActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {
        if (rvSearch.getVisibility() == View.VISIBLE) {
            rvSearch.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.VISIBLE);
            closeSearch();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvPick.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mvPick.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvPick.onDestroy();
    }


    @Override
    public void setData(GeoCode data) {
        closeLoadingDialog();
        if (data!=null){
            if (data.getLocation()!=null) {
                toPosition(new LatLng(data.getLocation().getLat(), data.getLocation().getLng()));
                tvName.setText(data.getFormattedAddress());
                poiArr.clear();
                poiArr.addAll(data.getPois());
                poiAdapter.notifyDataSetChanged();
                location.setLatitude(data.getLocation().getLat());
                location.setLongitude(data.getLocation().getLng());
            }
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

}
