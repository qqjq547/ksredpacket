package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagBenefit;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class RedbagBenefitAdapter extends BaseQuickAdapter<RedbagBenefit,BaseViewHolder> {

    public RedbagBenefitAdapter(@Nullable List<RedbagBenefit> data) {
        super(R.layout.item_redbag_benefit,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, RedbagBenefit item) {
       helper.setText(R.id.tv_name,item.getWhereRegion()+item.getType());
       helper.setText(R.id.tv_time,item.getCreateDate());
       helper.setText(R.id.tv_ksb,item.getCoin());
    }
}