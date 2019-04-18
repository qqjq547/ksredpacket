package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.webkit.WebView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
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

        if (item.getNestStatisticsResDto()==null){
            helper.setText(R.id.tv_show_num,String.valueOf(0));
            helper.setText(R.id.tv_click_num,String.valueOf(0));
            helper.setText(R.id.tv_collect_num,String.valueOf(0));
        }else {
            helper.setText(R.id.tv_show_num,String.valueOf(item.getNestStatisticsResDto().getShowQuantity()));
            helper.setText(R.id.tv_click_num,String.valueOf(item.getNestStatisticsResDto().getClickQuantity()));
            helper.setText(R.id.tv_collect_num,String.valueOf(item.getNestStatisticsResDto().getFavoriteQuantity()));
        }

        if (item.getStatus()==0){ //待投放
            if (item.getNestInfoId()==0){
                helper.setText(R.id.tv_edit,R.string.add_ad);
                helper.setVisible(R.id.tv_check,false);
            }else {
                helper.setText(R.id.tv_edit,R.string.edit_ad);
                helper.setText(R.id.tv_check,R.string.preview_nest_ad);
                helper.setVisible(R.id.tv_check,true);
                helper.addOnClickListener(R.id.tv_check);
            }
            helper.addOnClickListener(R.id.tv_edit);
            helper.setText(R.id.tv_status,R.string.waiting_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.bg_sky_blue));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getStatus()==1){ //投放中
            if (item.getNestInfoId()==0){
                helper.setText(R.id.tv_edit,R.string.add_ad);
                helper.setVisible(R.id.tv_check,false);
            }else {
                helper.setText(R.id.tv_edit,R.string.edit_ad);
                helper.setVisible(R.id.tv_check,true);
                helper.addOnClickListener(R.id.tv_check);
            }
            helper.addOnClickListener(R.id.tv_edit);
            helper.setText(R.id.tv_status,R.string.vote_going);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_black));
        }else if(item.getStatus()==2){//投放结束
            if (item.getNestInfoId()==0){//未投放广告的显示
                helper.setVisible(R.id.tv_edit,true);
                helper.setText(R.id.tv_edit,R.string.ad_has_not_vote);
                helper.setTextColor(R.id.tv_edit,mContext.getResources().getColor(R.color.text_gray));
                helper.setVisible(R.id.tv_check,false);
            }else {
                helper.setVisible(R.id.tv_edit,false);
                helper.setText(R.id.tv_edit,R.string.check_ad);
                helper.setVisible(R.id.tv_check,true);
                helper.addOnClickListener(R.id.tv_check);
            }
            helper.setText(R.id.tv_status,R.string.has_vote);
            helper.setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.text_gray));
            helper.setBackgroundColor(R.id.v_line,mContext.getResources().getColor(R.color.text_gray));
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.text_gray));
        }
        WebView wvMap=helper.getView(R.id.wv_map);
        CommonUtil.initH5WebView(mContext,wvMap);
        wvMap.loadUrl(Constant.URL_BMAP_URL+"?lng="+item.getLng()+"&lat="+item.getLat());
    }
}
