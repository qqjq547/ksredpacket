package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RecommedUser;

import java.util.List;

public class FansDetailAdapter extends BaseQuickAdapter<RecommedUser,BaseViewHolder> {
    public FansDetailAdapter(@Nullable List<RecommedUser> data) {
        super(R.layout.item_fans_detail,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommedUser item) {
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_time,item.getRegisterDate());
        helper.setText(R.id.tv_ksb,item.getBonus());
    }
}
