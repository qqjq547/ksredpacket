package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
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

public class BidRecordAdapter extends BaseQuickAdapter<NestAuctionRecord,BaseViewHolder> {
    public BidRecordAdapter(@Nullable List<NestAuctionRecord> data) {
        super(R.layout.item_bid_record,data);
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
        helper.setText(R.id.tv_name,item.getUserName());
        helper.setText(R.id.tv_time,item.getStartDate());
        String priceStr=item.getDayPrice()+mContext.getString(R.string.unit_yuan_day);
        SpannableStringBuilder builder=new SpannableStringBuilder(priceStr);
        String dayprice=String.valueOf(item.getDayPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_price,builder);
    }
}
