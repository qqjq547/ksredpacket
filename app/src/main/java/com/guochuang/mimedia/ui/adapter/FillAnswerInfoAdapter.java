package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class FillAnswerInfoAdapter extends CommonRecyclerAdapter<LookSurevyResult.StatisticsListBean.OptionsListBean> {

    public FillAnswerInfoAdapter(Context context, List<LookSurevyResult.StatisticsListBean.OptionsListBean> optionsListBeans, int itemlayout) {
        super(context, optionsListBeans, itemlayout);
    }

    @Override
    protected void convert(ViewHolder holder, int position, LookSurevyResult.StatisticsListBean.OptionsListBean item) {
        if (!TextUtils.isEmpty(item.getOptionValue())) {
            holder.setText(R.id.tv_answer, position + "." + item.getOptionValue()).setViewVisibility(R.id.tv_answer, View.VISIBLE);
        } else {
            holder.setViewVisibility(R.id.tv_answer, View.GONE);
        }

    }
}
