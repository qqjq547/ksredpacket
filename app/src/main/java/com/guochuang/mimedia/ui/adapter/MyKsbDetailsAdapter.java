package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.KsbRecord;


import java.util.List;

public class MyKsbDetailsAdapter extends BaseQuickAdapter<KsbRecord, BaseViewHolder> {
    public MyKsbDetailsAdapter(@Nullable List<KsbRecord> data) {
        super(R.layout.item_my_ksb_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KsbRecord item) {
       helper.setText(R.id.tv_name,item.getTitle());
       helper.setText(R.id.tv_time,item.getCreateDate());
       helper.addOnClickListener(R.id.lin_title);
        if (item.getIsIncome().equals("1")){
           helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_city_yellow));
           helper.setText(R.id.tv_price,"+"+String.format(mContext.getString(R.string.format_ksb),item.getCoin()));
       }else {
            helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_black));
            helper.setText(R.id.tv_price,"-"+String.format(mContext.getString(R.string.format_ksb),item.getCoin()));
       }
    }
}
