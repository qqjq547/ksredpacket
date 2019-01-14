package com.guochuang.mimedia.ui.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.guochuang.mimedia.mvp.model.RegionCoreHome;
import com.guochuang.mimedia.mvp.presenter.OperationCenterPresenter;
import com.guochuang.mimedia.mvp.view.OperationCenterView;
import com.guochuang.mimedia.tools.GeoHasher;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class OperationCenterActivity extends MvpActivity<OperationCenterPresenter> implements OperationCenterView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_where_region)
    TextView tvWhereRegion;
    @BindView(R.id.mv_center)
    MapView mvCenter;
    @BindView(R.id.tv_income_statistics)
    TextView tvIncomeStatistics;
    @BindView(R.id.pcv_income)
    PieChartView pcvIncome;
    @BindView(R.id.tv_today_income)
    TextView tvTodayIncome;
    @BindView(R.id.tv_yesterday_income)
    TextView tvYesterdayIncome;
    @BindView(R.id.tv_operation_total_income)
    TextView tvOperationTotalIncome;
    @BindView(R.id.tv_redbag_value)
    TextView tvRedbagValue;
    @BindView(R.id.tv_redbag_percent)
    TextView tvRedbagPercent;
    @BindView(R.id.tv_game_value)
    TextView tvGameValue;
    @BindView(R.id.tv_game_percent)
    TextView tvGamePercent;
    @BindView(R.id.tv_ad_value)
    TextView tvAdValue;
    @BindView(R.id.tv_ad_percent)
    TextView tvAdPercent;
    @BindView(R.id.tv_mall_value)
    TextView tvMallValue;
    @BindView(R.id.tv_mall_percent)
    TextView tvMallPercent;
    @BindView(R.id.tv_user_statistics)
    TextView tvUserStatistics;
    @BindView(R.id.tv_total_user)
    TextView tvTotalUser;
    @BindView(R.id.tv_today_increase)
    TextView tvTodayIncrease;
    @BindView(R.id.tv_today_active)
    TextView tvTodayActive;
    @BindView(R.id.tv_yesterday_increase)
    TextView tvYesterdayIncrease;
    @BindView(R.id.tv_yesterday_active)
    TextView tvYesterdayActive;
    private PieChartData data;
    BaiduMap bm;
    RegionCoreHome homeData;

    private double maxLatitude;
    private double minLatitude;
    private double maxLongitude;
    private double minLongitude;
    private double distance;
    private float level;

    @Override
    protected OperationCenterPresenter createPresenter() {
        return new OperationCenterPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_operation_center;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.operation_center);
       tvText.setText(R.string.unit_ksb);
        bm = mvCenter.getMap();
        mvCenter.showZoomControls(false);
        mvCenter.showScaleControl(false);
        mvCenter.removeViewAt(1);
        mvpPresenter.getRegionCoreHome();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mvCenter.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mvCenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mvCenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvCenter.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_income_statistics, R.id.tv_user_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                break;
            case R.id.tv_income_statistics:
                if (homeData!=null) {
                    startActivity(new Intent(this, IncomeStatisticsActivity.class).putExtra(Constant.STATISTICSID,homeData.getStatisticsId()));
                }
                break;
            case R.id.tv_user_statistics:
                if (homeData!=null) {
                   startActivity(new Intent(this,UserStatisticsActivity.class));
                }
                break;
        }
    }
    private void generateData(float redPacket,float game,float ad,float mall) {
        int[] colorResArr=new int[]{
                getResources().getColor(R.color.bg_red),
                getResources().getColor(R.color.bg_center_purple),
                getResources().getColor(R.color.bg_yellow),
                getResources().getColor(R.color.bg_center_green),
        };
        List<SliceValue> values = new ArrayList<>();
        SliceValue redPacketValue = new SliceValue(redPacket, colorResArr[0]);
        values.add(redPacketValue);
        SliceValue gameValue = new SliceValue(game, colorResArr[1]);
        values.add(gameValue);
        SliceValue adValue = new SliceValue(ad, colorResArr[2]);
        values.add(adValue);
        SliceValue mallValue = new SliceValue(mall, colorResArr[3]);
        values.add(mallValue);

        data = new PieChartData(values);
        data.setHasLabels(false);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(false);
        data.setHasCenterCircle(true);
        data.setSlicesSpacing(0);
        pcvIncome.setPieChartData(data);
    }

    /**
     * 边界检索
     */
    public void districtSerach(String provinceName,String cityName,String districtName) {
        DistrictSearch mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {

            @Override
            public void onGetDistrictResult(DistrictResult districtResult) {
                if (districtResult==null||districtResult.getCenterPt()==null){
                    showShortToast(R.string.get_city_error);
                    return;
                }
                List<List<LatLng>> polyLines = districtResult.getPolylines();
                OverlayOptions titleOo = new MarkerOptions().
                        title(districtResult.getCityName()).
                        position(districtResult.getCenterPt()).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                bm.addOverlay(titleOo);
                double minLeft=0;
                double maxLeft=0;
                for (List<LatLng> latLngs : polyLines) {
                    PolygonOptions polygonOptions = new PolygonOptions();
                    polygonOptions.points(latLngs);//多边形顶点坐标集
                    polygonOptions.stroke(new Stroke(1, 0xAAFF0000));//边框样式
                    polygonOptions.fillColor(0x66FFFF00);//填充颜色
                    bm.addOverlay(polygonOptions);
                    for (LatLng latLng:latLngs){
                        if (latLng.longitude<minLeft)
                            minLeft=latLng.longitude;
                        if (latLng.longitude>maxLeft)
                            maxLeft=latLng.longitude;
                    }
                }
                getMax(polyLines);
                calculateDistance();
                float zoom=getLevel();
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(districtResult.getCenterPt()).zoom(zoom);
                bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        });

        DistrictSearchOption districtSearchOption = new DistrictSearchOption();
        if (TextUtils.isEmpty(cityName)){//是省级
            districtSearchOption.cityName(provinceName);
        }else if(TextUtils.isEmpty(districtName)){//市级
            districtSearchOption.cityName(cityName);
        }else {
            districtSearchOption.cityName(cityName);
            districtSearchOption.districtName(districtName);
        }
        mDistrictSearch.searchDistrict(districtSearchOption);
    }

    @Override
    public void setData(RegionCoreHome data) {
        closeLoadingDialog();
         if (data!=null){
             this.homeData=data;
             tvTodayIncome.setText(data.getTodayIncome());
             tvYesterdayIncome.setText(data.getYesterDayIncome());
             tvOperationTotalIncome.setText(data.getTotalIncome());
             tvRedbagValue.setText(data.getRedPacketIncome());
             tvRedbagPercent.setText(data.getRedPacketThan()+getString(R.string.percent));
             tvGameValue.setText(data.getGameIncome());
             tvGamePercent.setText(data.getGameThan()+getString(R.string.percent));
             tvAdValue.setText(data.getAdIncome());
             tvAdPercent.setText(data.getAdThan()+getString(R.string.percent));
             tvMallValue.setText(data.getMallIncome());
             tvMallPercent.setText(data.getMallThan()+getString(R.string.percent));
             generateData(data.getRedPacketThan(),data.getGameThan(),data.getAdThan(),data.getMallThan());
             tvTotalUser.setText(String.valueOf(data.getTotalUser()));
             tvTodayIncrease.setText(String.valueOf(data.getTodayAddUser()));
             tvYesterdayIncrease.setText(String.valueOf(data.getYesterAddUser()));
             tvTodayActive.setText(String.valueOf(data.getTodayActive()));
             tvYesterdayActive.setText(String.valueOf(data.getYesterActive()));
             if (!TextUtils.isEmpty(data.getDistrictName())){
                 tvWhereRegion.setText(data.getProvinceName()+data.getCityName()+data.getDistrictName());
             }else if(!TextUtils.isEmpty(data.getCityName())){
                 tvWhereRegion.setText(data.getProvinceName()+data.getCityName());
             }else if(!TextUtils.isEmpty(data.getProvinceName())){
                 tvWhereRegion.setText(data.getProvinceName());
             }
             districtSerach(data.getProvinceName(),data.getCityName(),data.getDistrictName());
         }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }


    /**
     * 比较选出集合中最大经纬度
     */
    private void getMax(List<List<LatLng>> polyLines) {
         List<Double> latitudeList = new ArrayList<Double>();
         List<Double> longitudeList = new ArrayList<Double>();
        for (int i = 0; i < polyLines.size(); i++) {
            for (int j=0;j<polyLines.get(i).size();j++){
                double latitude = polyLines.get(i).get(j).latitude;
                double longitude = polyLines.get(i).get(j).longitude;
                latitudeList.add(latitude);
                longitudeList.add(longitude);
            }
        }
        maxLatitude = Collections.max(latitudeList);
        minLatitude = Collections.min(latitudeList);
        maxLongitude = Collections.max(longitudeList);
        minLongitude = Collections.min(longitudeList);
    }

    /**
     * 计算两个Marker之间的距离
     */
    private void calculateDistance() {
        distance = GeoHasher.GetDistance(maxLatitude, maxLongitude, minLatitude, minLongitude);
    }

    /**
     *根据距离判断地图级别
     */
    private float getLevel() {
        int zoom[] = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        for (int i = 0; i < zoom.length; i++) {
            int zoomNow = zoom[i];
            if (zoomNow - distance * 1000 > 0) {
                return 18 - i + 6;
            }
        }
        return 10f;
    }

}
