package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

/**
 * * 类型 0：单选 1：多选 2：填空题
 */
public class LookVideoProblemAdapter extends CommonRecyclerAdapter<LookVideoResult.QuestionListBean> {

    public LookVideoProblemAdapter(Context context, List<LookVideoResult.QuestionListBean> questionListBeans, int itemlayout) {
        super(context, questionListBeans, itemlayout);
    }

    @Override
    protected void convert(ViewHolder holder, int position, LookVideoResult.QuestionListBean item) {
        holder.setViewVisibility(R.id.tv_fill_answer_info, View.GONE).setViewVisibility(R.id.recycle_anser_count, View.GONE);
        String type = item.getType() == 0 ? mContext.getString(R.string.single_choice) : item.getType() == 1 ? mContext.getString(R.string.muti_choice) : mContext.getString(R.string.input_blank);

        holder.setText(R.id.tv_squece, position + ".").setText(R.id.tv_problem, type + item.getTitle());
        //填空题
        if (item.getType() == 2) {
            holder.setViewVisibility(R.id.tv_fill_answer_info, View.VISIBLE);
            if (item.getOptionsList() != null && !item.getOptionsList().isEmpty()) {
                holder.setText(R.id.tv_fill_answer_info, Html.fromHtml(mContext.getString(R.string.answer_str) + item.getOptionsList().get(0).getOptionValue()));
            }

        } else {
            //单选或者是多选
            if (item.getOptionsList() != null && !item.getOptionsList().isEmpty()) {
                holder.setViewVisibility(R.id.recycle_anser_count, View.VISIBLE);
                RecyclerView recyclerView = holder.getView(R.id.recycle_anser_count);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                LookVideoProblemInAdapter lookVideoProblemInAdapter = new LookVideoProblemInAdapter(mContext, item.getOptionsList(), R.layout.item_lookvideoproblem_in_layout);
                recyclerView.setAdapter(lookVideoProblemInAdapter);

            }

        }


    }
}
