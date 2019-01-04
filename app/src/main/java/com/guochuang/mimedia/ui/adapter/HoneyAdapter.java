package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class HoneyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public HoneyAdapter(@Nullable List<String> data) {
        super(R.layout.item_honey,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_name,item);
    }
}
