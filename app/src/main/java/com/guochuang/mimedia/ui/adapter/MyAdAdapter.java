package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyAdAdapter extends BaseQuickAdapter<MyAd,BaseViewHolder> {
    public MyAdAdapter(@Nullable List<MyAd> data) {
        super(R.layout.item_my_ad,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAd item) {
        if (item.getState()==0){
            helper.setGone(R.id.lin_info,false);
            helper.setGone(R.id.lin_data,false);
            helper.setText(R.id.tv_edit,R.string.edit_ad);
            helper.setText(R.id.tv_status,R.string.waiting_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getState()==1){
            helper.setGone(R.id.lin_info,true);
            helper.setGone(R.id.lin_data,false);
            helper.setText(R.id.tv_edit,R.string.edit_ad);
            helper.setText(R.id.tv_status,R.string.vote_going);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getState()==2){
            helper.setGone(R.id.lin_info,true);
            helper.setGone(R.id.lin_data,true);
            helper.setText(R.id.tv_edit,R.string.check_ad);
            helper.setText(R.id.tv_status,R.string.has_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_gray));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_gray));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_gray));
        }
        helper.addOnClickListener(R.id.tv_edit);
//        113.822728=22.630487
        TextureMapView mapView=helper.getView(R.id.mv_location);
        BaiduMap bm = mapView.getMap();
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.removeViewAt(1);
        OverlayOptions titleOo = new MarkerOptions().
                position(new LatLng(113.822728,22.630487)).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        bm.addOverlay(titleOo);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(113.822728,22.630487)).zoom(15f);
        bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}
