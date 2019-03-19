package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class EditRedPackgeProblemInAdapter extends CommonRecyclerAdapter<ProblemBean.ItemBean> {

    private String mRedPacketType;

    public EditRedPackgeProblemInAdapter(Context context, List<ProblemBean.ItemBean> itemBeans, int itemlayout, String redPacketType) {
        super(context, itemBeans, itemlayout);
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
    protected void convert(ViewHolder holder, int position, ProblemBean.ItemBean itemBean) {
        holder.setViewVisibility(R.id.iv_icon, View.GONE);

        String text = "";
        switch (itemBean.getProblemType()) {
            case 0:
                holder.setViewVisibility(R.id.iv_icon, View.VISIBLE).setImageResource(R.id.iv_icon, itemBean.isIsanswer() ? R.drawable.select_y : R.drawable.select_n);

                text = itemBean.getItemname() + "." + itemBean.getItemcontent();
                break;
            case 1:
                holder.setViewVisibility(R.id.iv_icon, View.VISIBLE).setImageResource(R.id.iv_icon, itemBean.isIsanswer() ? R.drawable.many_y : R.drawable.many_n);
                text = itemBean.getItemname() + "." + itemBean.getItemcontent();

                break;
            case 2:
                text = Html.fromHtml("<font color = '#000000'>答:</font>") + itemBean.getItemcontent();
                break;
        }
        if(Constant.RED_PACKET_TYPE_SURVEY.equals(mRedPacketType)) {
            holder.setViewVisibility(R.id.iv_icon, View.GONE);
        }
        holder.setText(R.id.tv_container, text);


    }
}
