package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.IncomeDetail;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class IncomeDetailAdapter extends BaseQuickAdapter<IncomeDetail,BaseViewHolder> {

    public IncomeDetailAdapter(@Nullable List<IncomeDetail> data) {
        super(R.layout.item_income_detail,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, IncomeDetail item) {
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_type,item.getType());
        helper.setText(R.id.tv_price,item.getCoin());
        helper.setText(R.id.tv_time,item.getTime());
    }
}
