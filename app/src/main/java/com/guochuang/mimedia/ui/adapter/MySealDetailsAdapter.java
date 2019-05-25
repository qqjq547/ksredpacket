package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

/**
 * 黄家强:
 * VALUE_24(124, "淘区块"),
 *
 * 黄家强:
 * VALUE_26(126, "淘区块分润"),
 *
 * 黄家强:
 * ALUE_37(137, "淘区块收益"),
 */
public class MySealDetailsAdapter extends BaseMultiItemQuickAdapter<SealRecord, BaseViewHolder> {
    public MySealDetailsAdapter(@Nullable List<SealRecord> data) {
        super(data);
        addItemType(SealRecord.SIMPLE, R.layout.item_my_seal_details);
        addItemType(SealRecord.TRANSFER, R.layout.item_seal_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, SealRecord item) {
        switch (item.getItemType()) {
            case SealRecord.SIMPLE:
                helper.setGone(R.id.tv_equivalence,false);
                String ksbType = item.getKsbType();

                if(SealRecord.TYPE_TQK.equals(ksbType) || SealRecord.TYPE_TQK_FY.equals(ksbType)
                        || SealRecord.TYPE_TQK_SY.equals(ksbType)|| SealRecord.TYPE_TQK_.equals(ksbType))
                    helper.setGone(R.id.tv_equivalence,true).setText(R.id.tv_equivalence,String.format(mContext.getString(R.string.equivalence_fomat_str),item.getMoney()));


                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_time, item.getCreateDate());
                if (TextUtils.isEmpty(item.getRemark())) {
                    helper.setGone(R.id.tv_remark, false);
                } else {
                    helper.setGone(R.id.tv_remark, true);
                }
                if (TextUtils.equals(item.getIsIncome(), "1")) {
                    helper.setTextColor(R.id.tv_price, mContext.getResources().getColor(R.color.text_city_yellow));
                    helper.setText(R.id.tv_price, "+" + String.format(mContext.getString(R.string.format_seal), item.getCoin()));
                } else {
                    helper.setTextColor(R.id.tv_price, mContext.getResources().getColor(R.color.text_black));
                    helper.setText(R.id.tv_price, "-" + String.format(mContext.getString(R.string.format_seal), item.getCoin()));
                }






                break;
            case SealRecord.TRANSFER:
                helper.setText(R.id.tv_type, item.getTitle());
                helper.setGone(R.id.iv_status, false);
                helper.setGone(R.id.tv_adrress, false);
                helper.setGone(R.id.tv_money, true);
                helper.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_000000));
                helper.setText(R.id.tv_time, item.getCreateDate());
                helper.setText(R.id.tv_nuber, String.format(mContext.getString(R.string.format_number), item.getCoin()));
                helper.setText(R.id.tv_adrress, String.format(mContext.getResources().getString(R.string.format_receive_address), item.getTargetAddress()));
                switch (item.getKsbType()) {
                    case SealRecord.TYPE_QC2SEAL://qc转seal
                        helper.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_4498e0));
                        helper.setText(R.id.tv_money, mContext.getString(R.string.equalvalue_) + item.getEquivalence() + mContext.getString(R.string.money_unit_seal));
                        break;
                    case SealRecord.TYPE_SEAL2QC://seal转qc
                        helper.setText(R.id.tv_nuber, String.format(mContext.getString(R.string.format_number), item.getCoin()));
                        helper.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_4498e0));
                        helper.setText(R.id.tv_money, mContext.getString(R.string.equalvalue_)+item.getEquivalence() + mContext.getString(R.string.money_unit_qc));
                        break;
                    case SealRecord.TYPE_EXTRA://提币
                        helper.setText(R.id.tv_nuber,  String.format(mContext.getString(R.string.format_number), mContext.getString(R.string.symbol_)+item.getCoin()));
                        helper.setGone(R.id.iv_status, true);
                        if (item.getStatus() == 0 ){
                            helper.setImageResource(R.id.iv_status,R.drawable.extract_doing);
                        }else if(item.getStatus() == 1){
                            helper.setImageResource(R.id.iv_status,R.drawable.extract_success);
                        }else {
                            helper.setImageResource(R.id.iv_status,R.drawable.extract_fial);
                        }
                        helper.setGone(R.id.tv_adrress, true);
                        helper.setText(R.id.tv_money, mContext.getResources().getString(R.string.servicefee_) + mContext.getString(R.string.symbol_) + item.getServiceFee());

                        break;
                    case SealRecord.TYPE_FILL://充币
                        helper.setText(R.id.tv_nuber, String.format(mContext.getString(R.string.format_number), item.getCoin()));
                        helper.setGone(R.id.tv_adrress, true);
                        helper.setText(R.id.tv_adrress, String.format(mContext.getResources().getString(R.string.target_address), item.getTargetAddress()));
                        helper.setGone(R.id.tv_money, false);
                        break;

                }
                break;
        }
    }
}
