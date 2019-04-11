package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.view.CustomProgress;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class LookSurevyInAdapter extends BaseQuickAdapter<LookSurevyResult.StatisticsListBean.OptionsListBean, BaseViewHolder> {
    int mDrawNumber;

    public LookSurevyInAdapter(@Nullable List<LookSurevyResult.StatisticsListBean.OptionsListBean> data,int layoutResId,  int drawNumber) {
        super(layoutResId, data);
        mDrawNumber = drawNumber;
    }


    @Override
    protected void convert(BaseViewHolder holder, LookSurevyResult.StatisticsListBean.OptionsListBean item) {
        holder.setText(R.id.tv_option, item.getOptionName() + "." + item.getOptionValue())
                .setText(R.id.tv_percentage, mDrawNumber == 0 ?"0%":(item.getCount() *  ( 100/ mDrawNumber))+ "%");
        CustomProgress view = holder.getView(R.id.custom_progress);
        view.setMaxProgress(mDrawNumber);
        view.setProgress(item.getCount());
    }
}
