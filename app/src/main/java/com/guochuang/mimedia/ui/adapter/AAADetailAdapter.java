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
        holder.setGone(R.id.iv_status, false).setGone(R.id.tv_adrress, false)
                .setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_000000));
        @DrawableRes int imageResId = 0;
        @StringRes int type = 0;
        String tvMoneyText = "";
        switch (String.valueOf(item.getBusinessType())) {
            case Constant.AAA2KSB:
                type = R.string.aaa2ksb;
                imageResId = R.drawable.icon_aaa2ksb;
                holder.setTextColor(R.id.tv_nuber, mContext.getResources().getColor(R.color.color_4498e0));
                tvMoneyText = mContext.getResources().getString(R.string.dengzhi_str) + item.getTargetCoin();
                break;

            case Constant.KSB2AAA:
                type = R.string.ksb2aaa;
                imageResId = R.drawable.icon_ksb2aaa;
                tvMoneyText = mContext.getResources().getString(R.string.servicefee_str) + item.getServiceFee();
                break;
            case Constant.EXTRACT_AAA:
                type = R.string.extract_aaa;
                imageResId = R.drawable.icon_extract_aaa;
                holder.setGone(R.id.iv_status, true).setImageResource(R.id.iv_status,
                        item.getStatus() == 0 ? R.drawable.extract_doing
                                : R.drawable.extract_success).setGone(R.id.tv_adrress, true);
                tvMoneyText = mContext.getResources().getString(R.string.servicefee_str) + item.getServiceFee();

                break;
            case Constant.Fill_AAA:
                type = R.string.fill_aaa;
                imageResId = R.drawable.icon_fill_aaa;
                tvMoneyText = String.format(mContext.getResources().getString(R.string.target_str), item.getTargetAddress());
                break;

        }

        try {
            holder.setImageResource(R.id.iv_type, imageResId).setText(R.id.tv_type,
                    mContext.getResources().getString(type)).setText(R.id.tv_time, item.getCreateDate())
                    .setText(R.id.tv_nuber, String.format(mContext.getResources()
                            .getString(R.string.aaa_number_fomat), item.getSourceCoin()+"")).setText(R.id.tv_money, tvMoneyText)
                    .setText(R.id.tv_adrress, String.format(mContext.getResources().getString(R.string.receiv_str), item.getTargetAddress()));
        }catch (Exception e){

        }



    }
}
