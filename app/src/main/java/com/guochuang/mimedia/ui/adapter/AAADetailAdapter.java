package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.AAADetail;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

/**
 * status (integer, optional):
 * <p>
 * 状态，0-处理中，1-成功 ,
 */
public class AAADetailAdapter extends BaseQuickAdapter<AAADetail, BaseViewHolder> {

    public AAADetailAdapter(@Nullable List<AAADetail> data) {
        super(R.layout.item_aaa_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, AAADetail item) {
        holder.setGone(R.id.iv_status, false);
        holder.setGone(R.id.tv_adrress, false);
        holder.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_000000));
        holder.setText(R.id.tv_time, item.getFinishDate());
        holder.setText(R.id.tv_nuber, String.format(mContext.getString(R.string.aaa_number_fomat), item.getSourceCoin()));
        holder.setText(R.id.tv_adrress, String.format(mContext.getResources().getString(R.string.receiv_str), item.getTargetAddress()));
        switch (item.getBusinessType()) {
            case Constant.AAA2KSB:
                holder.setText(R.id.tv_type,R.string.aaa2ksb);
                holder.setImageResource(R.id.iv_type, R.drawable.icon_aaa2ksb);
                holder.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_4498e0));
                holder.setText(R.id.tv_money, mContext.getString(R.string.dengzhi_str) + item.getTargetCoin());
                break;
            case Constant.KSB2AAA:
                holder.setText(R.id.tv_type,R.string.ksb2aaa);
                holder.setImageResource(R.id.iv_type, R.drawable.icon_ksb2aaa);
                holder.setText(R.id.tv_money,  mContext.getResources().getString(R.string.servicefee_str) + item.getServiceFee()+mContext.getString(R.string.KSB_str));
                holder.setText(R.id.tv_nuber,String.format(mContext.getResources()
                        .getString(R.string.aaa_number_fomat), item.getTargetCoin()));
                break;
            case Constant.EXTRACT_AAA:
                holder.setText(R.id.tv_type,R.string.extract_aaa);
                holder.setImageResource(R.id.iv_type, R.drawable.icon_extract_aaa);
                holder.setGone(R.id.iv_status, true).setImageResource(R.id.iv_status,
                        item.getStatus() == 0 ? R.drawable.extract_doing : item.getStatus() == 1 ?
                                 R.drawable.extract_success:R.drawable.extract_fial).setGone(R.id.tv_adrress, true);
                holder.setText(R.id.tv_money, mContext.getResources().getString(R.string.servicefee_str) + item.getServiceFee()+mContext.getString(R.string.KSB_str));

                break;
            case Constant.Fill_AAA:
                holder.setText(R.id.tv_type,R.string.fill_aaa);
                holder.setImageResource(R.id.iv_type, R.drawable.icon_fill_aaa);
                holder.setText(R.id.tv_nuber, String.format(mContext.getString(R.string.aaa_number_fomat), item.getTargetCoin()));
                break;

        }

    }

}
