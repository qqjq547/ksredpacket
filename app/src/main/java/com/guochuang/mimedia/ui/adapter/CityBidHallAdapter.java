package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class CityBidHallAdapter extends BaseMultiItemQuickAdapter<CityBidHall, BaseViewHolder> {

    public CityBidHallAdapter(List<CityBidHall> data) {
        super(data);
        addItemType(CityBidHall.EXIST, R.layout.item_city_bid_hall);
        addItemType(CityBidHall.NONE, R.layout.item_city_bid_hall_none);
//        addItemType();
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBidHall item) {
        switch (item.getItemType()) {
            case CityBidHall.EXIST:
                helper.setText(R.id.tv_city_name, item.getWhereRegion());
                helper.setText(R.id.tv_buy_time, item.getCreateDate());
                GlideImgManager.loadCircleImage(mContext, item.getCurrentAvatar(), (ImageView) helper.getView(R.id.iv_left_header));
                helper.setText(R.id.tv_left_user_name, item.getCurrentNickName());
                helper.setText(R.id.tv_left_price, String.format(mContext.getResources().getString(R.string.insert_yuan), item.getCurrentMoney()));
                GlideImgManager.loadCircleImage(mContext, item.getFormerAvatar(), (ImageView) helper.getView(R.id.iv_right_header));
                helper.setText(R.id.tv_right_user_name, item.getFormerNickName());
                helper.setText(R.id.tv_right_price, String.format(mContext.getResources().getString(R.string.insert_yuan), item.getFormerMoney()));
                helper.setText(R.id.tv_premium, String.format(mContext.getResources().getString(R.string.format_premium), item.getPremium()));
                helper.setText(R.id.tv_time_go, item.getAppointmentDate());
                break;
            case CityBidHall.NONE:
                helper.setText(R.id.tv_city_name, item.getWhereRegion());
                helper.setText(R.id.tv_bid_price, String.format(mContext.getResources().getString(R.string.insert_yuan), item.getCurrentMoney()));
                break;
        }
    }
}
