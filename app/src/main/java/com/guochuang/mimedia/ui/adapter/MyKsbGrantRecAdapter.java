package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.MyKsbGrantRec;

import java.util.List;

public class MyKsbGrantRecAdapter extends BaseQuickAdapter<MyKsbGrantRec, BaseViewHolder> {
    public MyKsbGrantRecAdapter(@Nullable List<MyKsbGrantRec> data) {
        super(R.layout.item_my_ksb_grant_rec, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyKsbGrantRec item) {
        helper.setText(R.id.tv_num, String.format(mContext.getResources().getString(R.string.format_ksb), item.getCoin()));
        helper.setText(R.id.tv_time, item.getCreateDate());
        helper.setText(R.id.tv_your, item.getDoneeAddress());
    }
}
