package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class EditRedPackgeProblemAdapter extends BaseQuickAdapter<ProblemBean, BaseViewHolder> {
    private String mRedPacketType;

    public EditRedPackgeProblemAdapter(@Nullable List<ProblemBean> data, int layoutResId, String redPacketType) {
        super(layoutResId, data);
        mRedPacketType = redPacketType;
    }


    @Override
    protected void convert(BaseViewHolder holder, ProblemBean problem) {
        final int position = holder.getAdapterPosition();
        holder.setText(R.id.tv_serial_number, position + 1 + ".")
                .setText(R.id.tv_problem_name, (problem.getType() == 0 ?
                        mContext.getString(R.string.single_choice) : problem.getType() == 1
                        ? mContext.getString(R.string.muti_choice)
                        : mContext.getString(R.string.input_blank)) + problem.getProblem());


        holder.addOnClickListener(R.id.iv_edit_problem);
        holder.addOnClickListener(R.id.iv_delete_problem);

        RecyclerView innerRecycle = holder.getView(R.id.recycle_anser);
        innerRecycle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        EditRedPackgeProblemInAdapter editRedPackgeProblemInAdapter = new EditRedPackgeProblemInAdapter( problem.getItem(), R.layout.item_anser_layout, mRedPacketType);
        innerRecycle.setAdapter(editRedPackgeProblemInAdapter);
    }


}