package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RainRecord;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class WelfareAdapter extends BaseQuickAdapter<RainRecord,BaseViewHolder> {
    public WelfareAdapter(@Nullable List<RainRecord> data) {
        super(R.layout.item_welfare,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RainRecord item) {
        GlideImgManager.loadImage(mContext,R.drawable.ic_rain_list,(ImageView)helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_time,String.format(mContext.getString(R.string.format_expire_time),item.getExpireTime()));
        switch (item.getState()){
            case Constant.STATE_HAS_GET:
                helper.setText(R.id.tv_state,R.string.state_has_get);
                helper.setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.text_gray));
                helper.setGone(R.id.tv_ksb,true);
                SpannableString span=new SpannableString(String.format(mContext.getString(R.string.format_rob_ksb),item.getDrawCoin()));
                span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_city_yellow)), 2, item.getDrawCoin().length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                helper.setText(R.id.tv_ksb,span);
                break;
            case Constant.STATE_UN_GET:
                helper.setText(R.id.tv_state,R.string.state_un_get);
                helper.setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.text_city_yellow));
                helper.setGone(R.id.tv_ksb,false);
                break;
            case Constant.STATE_HAS_EXPIRE:
                helper.setText(R.id.tv_state,R.string.state_has_expire);
                helper.setTextColor(R.id.tv_state,mContext.getResources().getColor(R.color.text_gray));
                helper.setGone(R.id.tv_ksb,false);
                break;
        }

    }
}
