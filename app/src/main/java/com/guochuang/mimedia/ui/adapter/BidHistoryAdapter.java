package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.NestHistory;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class BidHistoryAdapter extends BaseQuickAdapter<NestHistory,BaseViewHolder> {
    public BidHistoryAdapter(@Nullable List<NestHistory> data) {
        super(R.layout.item_bid_history,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestHistory item) {
        GlideImgManager.loadImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_time,item.getStartDate());
        String priceStr=item.getUnitPrice()+mContext.getString(R.string.unit_yuan_day);
        SpannableStringBuilder builder=new SpannableStringBuilder(priceStr);
        String dayprice=String.valueOf(item.getUnitPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_price,builder);
    }
}
