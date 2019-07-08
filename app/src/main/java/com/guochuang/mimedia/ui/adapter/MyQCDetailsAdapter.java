package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.SealRecord;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

//除了淘区块收益、淘区块分润、淘区块押金退回、QC转SEAL、SEAL转QC类型，的记录。

public class MyQCDetailsAdapter extends BaseQuickAdapter<SealRecord, BaseViewHolder> {
    public MyQCDetailsAdapter(@Nullable List<SealRecord> data) {
        super(R.layout.item_my_ksb_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SealRecord item) {
        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_time,item.getCreateDate());
        if (TextUtils.isEmpty(item.getRemark())){
            helper.setGone(R.id.tv_remark,false);
        }else {
            helper.setGone(R.id.tv_remark,true);
        }
        if (item.getIsIncome().equals("1")){
            helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_city_yellow));
            helper.setText(R.id.tv_price,"+"+String.format(mContext.getString(R.string.format_qc),item.getCoin()));
        }else {
            helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_black));
            helper.setText(R.id.tv_price,"-"+String.format(mContext.getString(R.string.format_qc),item.getCoin()));
        }
    }
}