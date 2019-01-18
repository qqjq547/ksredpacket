package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class BuyPriceAdapter extends BaseQuickAdapter<BidPrice,BaseViewHolder> {

    public BuyPriceAdapter(@Nullable List<BidPrice> data) {
        super(R.layout.item_buy_price,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, BidPrice item) {
         helper.setText(R.id.tv_name,item.getWhereRegion());
         helper.setText(R.id.tv_time,item.getCreateDate());
         helper.setText(R.id.tv_money,mContext.getString(R.string.out_price)+" "+item.getCoin());
         helper.setText(R.id.tv_principal,mContext.getString(R.string.principal)+" "+item.getPrincipalCoin());
    }
}
