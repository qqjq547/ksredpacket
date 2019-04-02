package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;


public class LookSurevyAdapter extends CommonRecyclerAdapter<LookSurevyResult.StatisticsListBean> {
    private int mDrawNumber;

    public LookSurevyAdapter(Context context, List<LookSurevyResult.StatisticsListBean> statisticsListBeans, int itemlayout, int drawNumber) {
        super(context, statisticsListBeans, itemlayout);
        mDrawNumber = drawNumber;
    }


    @Override
    protected void convert(ViewHolder holder, final int position, LookSurevyResult.StatisticsListBean item) {
        holder.setViewVisibility(R.id.tv_look_info, View.GONE).setViewVisibility(R.id.recycle_anser_count, View.GONE);
        String type = item.getType() == 0 ? mContext.getString(R.string.str_singe_select) : item.getType() == 1 ? mContext.getString(R.string.str_many_select) : mContext.getString(R.string.str_fill_problem);

        holder.setText(R.id.tv_squece, position + ".").setText(R.id.tv_problem, "【" + type + "】" + item.getTitle());
        //填空题
        if (item.getType() == 2) {
            holder.setViewVisibility(R.id.tv_look_info, View.VISIBLE);
        } else {
            //单选或者是多选
            if (item.getOptionsList() != null && !item.getOptionsList().isEmpty()) {

                holder.setViewVisibility(R.id.recycle_anser_count, View.VISIBLE);
                RecyclerView recyclerView = holder.getView(R.id.recycle_anser_count);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                LookSurevyInAdapter lookSurevyInAdapter = new LookSurevyInAdapter(mContext, item.getOptionsList(), R.layout.item_look_surevy_in_layout, mDrawNumber);
                recyclerView.setAdapter(lookSurevyInAdapter);

            }

        }

        holder.getView(R.id.tv_look_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mOnItmeChildrenClick !=null) {
                    mOnItmeChildrenClick.itmeChildrenClick(R.id.tv_look_info,position);
                }

            }
        });

    }



    /**
     * 设置监听
     */

    public interface OnItmeChildrenClick {
        void itmeChildrenClick(int viewId, int position);

    }

    LookSurevyAdapter.OnItmeChildrenClick mOnItmeChildrenClick;

    public void setOnItmeChildren(LookSurevyAdapter.OnItmeChildrenClick listener) {
        mOnItmeChildrenClick = listener;

    }

}
