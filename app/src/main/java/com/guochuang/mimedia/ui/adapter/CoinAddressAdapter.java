package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.view.CoinAddress;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class CoinAddressAdapter extends BaseQuickAdapter<CoinAddress,BaseViewHolder> {
    public CoinAddressAdapter(@Nullable List<CoinAddress> data) {
        super(R.layout.item_coin_address,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinAddress item) {
        helper.setText(R.id.tv_name,item.getAlias());
        helper.setText(R.id.tv_address,item.getChainAddress());
        helper.addOnClickListener(R.id.iv_edit);

    }
}
