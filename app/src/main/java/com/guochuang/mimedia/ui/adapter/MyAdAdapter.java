package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
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
        helper.setText(R.id.tv_time,String.format(mContext.getString(R.string.format_time_to_time),item.getStartDate(),item.getEndDate()));
        String priceStr=String.format(mContext.getString(R.string.format_price_and_total),item.getUnitPrice(),item.getTotalPrice());
        SpannableStringBuilder builder=new SpannableStringBuilder(priceStr);
        String dayprice=String.valueOf(item.getUnitPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String totalprice=String.valueOf(item.getTotalPrice());
        int totalIndex = priceStr.lastIndexOf(totalprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red)), totalIndex, totalIndex + totalprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_price,builder);

        helper.addOnClickListener(R.id.tv_edit);
        helper.setText(R.id.tv_show_num,String.valueOf(item.getNestStatisticsResDto().getShowQuantity()));
        helper.setText(R.id.tv_click_num,String.valueOf(item.getNestStatisticsResDto().getClickQuantity()));
        helper.setText(R.id.tv_collect_num,String.valueOf(item.getNestStatisticsResDto().getFavoriteQuantity()));

        if (item.getStatus().equals("0")){
            helper.setGone(R.id.lin_info,false);
            helper.setGone(R.id.lin_data,false);
            helper.setText(R.id.tv_edit,R.string.edit_ad);
            helper.setText(R.id.tv_status,R.string.waiting_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getStatus().equals("1")){
            helper.setGone(R.id.lin_info,true);
            helper.setGone(R.id.lin_data,false);
            helper.setText(R.id.tv_edit,R.string.edit_ad);
            helper.setText(R.id.tv_status,R.string.vote_going);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getStatus().equals("2")){
            helper.setGone(R.id.lin_info,true);
            helper.setGone(R.id.lin_data,true);
            helper.setText(R.id.tv_edit,R.string.check_ad);
            helper.setText(R.id.tv_status,R.string.has_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_gray));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_gray));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_gray));
        }
//        helper.addOnClickListener(R.id.tv_edit);
//        113.822728=22.630487
        MapView mapView=helper.getView(R.id.mv_location);
        BaiduMap bm = mapView.getMap();
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.removeViewAt(1);
//        OverlayOptions titleOo = new MarkerOptions().
//                position(new LatLng(113.822728,22.630487)).
//                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
//        bm.addOverlay(titleOo);
//        MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(new LatLng(113.822728,22.630487)).zoom(15f);
//        bm.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }
}
