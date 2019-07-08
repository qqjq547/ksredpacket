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

public class HistoryPutAdapter extends BaseQuickAdapter<NestHistory,BaseViewHolder> {
    public HistoryPutAdapter(@Nullable List<NestHistory> data) {
        super(R.layout.item_history_put,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestHistory item) {
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_nickname,item.getNickName());
        String priceStr=String.format(mContext.getString(R.string.format_price_and_total),String.valueOf(item.getUnitPrice()),String.valueOf(item.getTotalPrice()));
        SpannableStringBuilder builder=new SpannableStringBuilder(priceStr);
        String dayprice=String.valueOf(item.getUnitPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String totalprice=String.valueOf(item.getTotalPrice());
        int totalIndex = priceStr.lastIndexOf(totalprice);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), totalIndex, totalIndex + totalprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_price,builder);

        helper.setText(R.id.tv_time,String.format(mContext.getString(R.string.format_time_to_time),item.getStartDate(),item.getEndDate()));
        if (item.getNestStatisticsResDto()==null){
            helper.setText(R.id.tv_show_num,String.valueOf(0));
            helper.setText(R.id.tv_click_num,String.valueOf(0));
            helper.setText(R.id.tv_collect_num,String.valueOf(0));
        }else {
            helper.setText(R.id.tv_show_num,String.valueOf(item.getNestStatisticsResDto().getShowQuantity()));
            helper.setText(R.id.tv_click_num,String.valueOf(item.getNestStatisticsResDto().getClickQuantity()));
            helper.setText(R.id.tv_collect_num,String.valueOf(item.getNestStatisticsResDto().getFavoriteQuantity()));

        }

    }
}
