package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.QcRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

//除了淘区块收益、淘区块分润、淘区块押金退回、QC转SEAL、SEAL转QC类型，的记录。

public class MyQCDetailsAdapter extends BaseMultiItemQuickAdapter<QcRecord, BaseViewHolder> {
    public MyQCDetailsAdapter(@Nullable List<QcRecord> data) {
        super(data);
        addItemType(SealRecord.SIMPLE, R.layout.item_qc_details);
        addItemType(SealRecord.TRANSFER, R.layout.item_qc_detail_extra);
    }

    @Override
    protected void convert(BaseViewHolder helper, QcRecord item) {
        String number_sign="";
        if (TextUtils.equals(item.getIsIncome(), "1")) {
            number_sign="+";
        } else if(TextUtils.equals(item.getIsIncome(), "0")){
            number_sign="-";
        }
        switch (item.getItemType()){
            case SealRecord.SIMPLE:
                helper.setText(R.id.tv_name,item.getTitle());
                helper.setText(R.id.tv_time,item.getCreateDate());
                if (TextUtils.isEmpty(item.getRemark())){
                    helper.setGone(R.id.tv_remark,false);
                }else {
                    helper.setGone(R.id.tv_remark,true);
                }
                if (item.getIsIncome().equals("1")){
                    helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_city_yellow));
                    helper.setText(R.id.tv_price,number_sign+String.format(mContext.getString(R.string.format_qc),item.getCoin()));
                }else {
                    helper.setTextColor(R.id.tv_price,mContext.getResources().getColor(R.color.text_black));
                    helper.setText(R.id.tv_price,number_sign+String.format(mContext.getString(R.string.format_qc),item.getCoin()));
                }
                break;
            case SealRecord.TRANSFER:
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setGone(R.id.iv_status, false);
                helper.setGone(R.id.tv_address, false);
                helper.setGone(R.id.tv_money, true);
                helper.setTextColor(R.id.tv_number, mContext.getResources().getColor(R.color.color_4498e0));
                helper.setTextColor(R.id.tv_equal_value, mContext.getResources().getColor(R.color.color_4498e0));
                helper.setText(R.id.tv_time, item.getCreateDate());
                helper.setText(R.id.tv_number, String.format(mContext.getString(R.string.format_qc_number), number_sign+item.getMoney()));
                helper.setText(R.id.tv_equal_value,mContext.getString(R.string.equalvalue_seal)+item.getCoin());
                helper.setText(R.id.tv_money, mContext.getResources().getString(R.string.servicefee_seal) + mContext.getString(R.string.symbol_) + item.getServiceFee());
                helper.setText(R.id.tv_address, String.format(mContext.getResources().getString(R.string.format_receive_address), item.getTargetAddress()));
                helper.setGone(R.id.iv_status, true);
                if (item.getStatus() == 0 ){
                    helper.setImageResource(R.id.iv_status,R.drawable.extract_doing);
                }else if(item.getStatus() == 1){
                    helper.setImageResource(R.id.iv_status,R.drawable.extract_success);
                }else {
                    helper.setImageResource(R.id.iv_status,R.drawable.extract_fial);
                }
                helper.setGone(R.id.tv_address, true);
                break;
        }
    }
}