package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagMenu;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class RedbagTypeAdapter extends BaseQuickAdapter<RedbagMenu,BaseViewHolder> {
    public RedbagTypeAdapter(@Nullable List<RedbagMenu> data) {
        super(R.layout.item_redbag_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedbagMenu item) {
        helper.setImageResource(R.id.iv_icon,item.getIconResId());
        helper.setText(R.id.tv_name,item.getNameResId());
    }
}
