package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyBidAdapter extends BaseQuickAdapter<NestAuctionRecord,BaseViewHolder> {
    public MyBidAdapter(@Nullable List<NestAuctionRecord> data) {
        super(R.layout.item_my_bid,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestAuctionRecord item) {
        switch (item.getAuctionStatus()){//1领先2出局3成交
            case 1:
                GlideImgManager.loadImage(mContext,R.drawable.ic_bid_lead,(ImageView) helper.getView(R.id.iv_avatar));
                break;
            case 2:
                GlideImgManager.loadImage(mContext,R.drawable.ic_bid_out,(ImageView) helper.getView(R.id.iv_avatar));
                break;
            case 3:
                GlideImgManager.loadImage(mContext,R.drawable.ic_bid_done,(ImageView) helper.getView(R.id.iv_avatar));
                break;
        }
        helper.setText(R.id.tv_name,String.valueOf(item.getNestLocationId()));
        helper.setText(R.id.tv_duration,String.format(mContext.getString(R.string.format_time_to_time),item.getStartDate(),item.getEndDate()));
        String priceStr=String.format(mContext.getString(R.string.format_price_and_total),String.valueOf(item.getDayPrice()),String.valueOf(item.getTotalPrice()));
        SpannableStringBuilder builder=new SpannableStringBuilder(priceStr);
        String dayprice=String.valueOf(item.getDayPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String totalprice=String.valueOf(item.getTotalPrice());
        int totalIndex = priceStr.lastIndexOf(totalprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), totalIndex, totalIndex + totalprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_price,builder);
        helper.setText(R.id.tv_time,item.getAuctionDate());
    }
}
