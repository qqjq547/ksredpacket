package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class VideoProblemAdapter extends CommonRecyclerAdapter<ProblemBean> {
    private String mRedPacketType;

    public VideoProblemAdapter(Context context, List<ProblemBean> problemBeans, int itemlayout, String redPacketType) {
        super(context, problemBeans, itemlayout);
        mRedPacketType = redPacketType;
    }


    @Override
    protected void convert(ViewHolder holder, final int position, final ProblemBean problem) {

        holder.setText(R.id.tv_serial_number, position + 1 + ".")
                .setText(R.id.tv_problem_name, "【" + (problem.getType() == 0 ? "单选" : problem.getType() == 1 ? "多选" : "填空") + "】" + problem.getProblem());

        holder.getView(R.id.iv_edit_problem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改

                if (mOnEditeClick != null) {

                    mOnEditeClick.onClick(position);
                }

            }
        });
        holder.getView(R.id.iv_delete_problem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (mOnDeleteClick != null) {
                    mOnDeleteClick.onClick(position);
                }

            }
        });

        RecyclerView innerRecycle = holder.getView(R.id.recycle_anser);
        innerRecycle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        VideoProblemInAdapter videoProblemInAdapter = new VideoProblemInAdapter(mContext, problem.getItem(), R.layout.item_anser_layout,mRedPacketType);
        innerRecycle.setAdapter(videoProblemInAdapter);


    }

    public interface OnEditeClick {
        void onClick(int position);
    }

    public interface OnDeleteClick {
        void onClick(int position);
    }

    private OnEditeClick mOnEditeClick;
    private OnDeleteClick mOnDeleteClick;

    public void setOnEditeClickLisenter(OnEditeClick onEditeClick) {
        mOnEditeClick = onEditeClick;
    }

    public void setOnDeleteClickLisenter(OnDeleteClick onDeleteClick) {
        mOnDeleteClick = onDeleteClick;
    }

}