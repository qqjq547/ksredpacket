package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class EditRedPackgeProblemInAdapter extends BaseQuickAdapter<ProblemBean.ItemBean,BaseViewHolder> {

    private String mRedPacketType;

    public EditRedPackgeProblemInAdapter( @Nullable List<ProblemBean.ItemBean> data,int layoutResId,String redPacketType) {
        super(layoutResId, data);
        mRedPacketType = redPacketType;
    }


    @Override
    public int getItemCount() {
        if (mData != null && !mData.isEmpty()) {
            ProblemBean.ItemBean itemBean = mData.get(0);
            if (itemBean.getProblemType() == Constant.FILL_IN_PROBLEM) {
                if(Constant.RED_PACKET_TYPE_SURVEY.equals(mRedPacketType)) {
                    return 0;
                }

                return 1;
            }
        }

        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder holder, ProblemBean.ItemBean itemBean) {
        holder.setGone(R.id.iv_icon, false);

        String text = "";
        switch (itemBean.getProblemType()) {
            case 0:
                holder.setGone(R.id.iv_icon,true).setImageResource(R.id.iv_icon, itemBean.isIsanswer() ? R.drawable.select_y : R.drawable.select_n);

                text = itemBean.getItemname() + "." + itemBean.getItemcontent();
                break;
            case 1:
                holder.setGone(R.id.iv_icon,true).setImageResource(R.id.iv_icon, itemBean.isIsanswer() ? R.drawable.many_y : R.drawable.many_n);
                text = itemBean.getItemname() + "." + itemBean.getItemcontent();

                break;
            case 2:
                text = Html.fromHtml(mContext.getString(R.string.answer_str)) + itemBean.getItemcontent();
                break;
        }
        if(Constant.RED_PACKET_TYPE_SURVEY.equals(mRedPacketType)) {
            holder.setGone(R.id.iv_icon, false);
        }
        holder.setText(R.id.tv_container, text);
    }

}
