package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.view.View;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.view.CustomProgress;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class LookSurevyInAdapter extends CommonRecyclerAdapter<LookSurevyResult.StatisticsListBean.OptionsListBean> {
    int mDrawNumber;

    public LookSurevyInAdapter(Context context, List<LookSurevyResult.StatisticsListBean.OptionsListBean> optionsListBeans, int itemlayout, int drawNumber) {
        super(context, optionsListBeans, itemlayout);
        mDrawNumber = drawNumber;
    }

    @Override
    protected void convert(ViewHolder holder, int position, LookSurevyResult.StatisticsListBean.OptionsListBean item) {
        holder.setText(R.id.tv_option, item.getOptionName() + "." + item.getOptionValue()).setText(R.id.tv_percentage, item.getCount() * mDrawNumber / 100 + "%");
        CustomProgress view = holder.getView(R.id.custom_progress);
        view.setMaxProgress(mDrawNumber);
        view.setProgress(item.getCount());


    }
}
