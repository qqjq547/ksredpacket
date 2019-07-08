package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;


public class LookSurevyAdapter extends BaseQuickAdapter<LookSurevyResult.StatisticsListBean, BaseViewHolder> {
    private int mDrawNumber;

    public LookSurevyAdapter(@Nullable List<LookSurevyResult.StatisticsListBean> data, int layoutResId, int drawNumber) {
        super(layoutResId, data);
        mDrawNumber = drawNumber;
    }


    @Override
    protected void convert(BaseViewHolder holder, LookSurevyResult.StatisticsListBean item) {
        int position = holder.getAdapterPosition();
        holder.setGone(R.id.tv_look_info, false).setGone(R.id.recycle_anser_count, false);


        String type = item.getType() == 0 ? mContext.getString(R.string.single_choice) : item.getType() == 1 ? mContext.getString(R.string.muti_choice) : mContext.getString(R.string.input_blank);

        holder.setText(R.id.tv_squece, position+1 + ".").setText(R.id.tv_problem, type + item.getTitle());
        //填空题
        if (item.getType() == 2) {
            holder.setGone(R.id.tv_look_info, true);
        } else {
            //单选或者是多选
            if (item.getOptionsList() != null && !item.getOptionsList().isEmpty()) {

                holder.setGone(R.id.recycle_anser_count, true);
                RecyclerView recyclerView = holder.getView(R.id.recycle_anser_count);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                LookSurevyInAdapter lookSurevyInAdapter = new LookSurevyInAdapter(item.getOptionsList(), R.layout.item_look_surevy_in_layout, mDrawNumber);
                recyclerView.setAdapter(lookSurevyInAdapter);

            }

        }

        holder.addOnClickListener(R.id.tv_look_info);
    }


}
