package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.ShareBenefit;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class ShareBenefitAdapter extends BaseQuickAdapter<ShareBenefit,BaseViewHolder> {

    public ShareBenefitAdapter(@Nullable List<ShareBenefit> data) {
        super(R.layout.item_share_benefit,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ShareBenefit item) {
       helper.setText(R.id.tv_name,item.getTitle());
       helper.setText(R.id.tv_time,item.getCreateDate());
       helper.setText(R.id.tv_ksb,String.format(mContext.getString(R.string.format_ksb),item.getCoin()));
    }
}
