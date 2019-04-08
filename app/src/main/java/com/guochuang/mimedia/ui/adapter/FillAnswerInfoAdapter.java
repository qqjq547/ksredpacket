package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class FillAnswerInfoAdapter extends BaseQuickAdapter<LookSurevyResult.StatisticsListBean.OptionsListBean, BaseViewHolder> {

    public FillAnswerInfoAdapter(@Nullable List<LookSurevyResult.StatisticsListBean.OptionsListBean> data, int layoutResId) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, LookSurevyResult.StatisticsListBean.OptionsListBean item) {
        int position = holder.getAdapterPosition();
        if (!TextUtils.isEmpty(item.getOptionValue())) {
            holder.setText(R.id.tv_answer, position + "." + item.getOptionValue()).setGone(R.id.tv_answer, true);
        } else {
            holder.setGone(R.id.tv_answer, false);
        }
    }
}
