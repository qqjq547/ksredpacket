package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyBidAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MyBidAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_bid,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
