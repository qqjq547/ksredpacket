package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class EditRedPackgeProblemAdapter extends CommonRecyclerAdapter<ProblemBean> {
    private String mRedPacketType;

    public EditRedPackgeProblemAdapter(Context context, List<ProblemBean> problemBeans, int itemlayout, String redPacketType) {
        super(context, problemBeans, itemlayout);
        mRedPacketType = redPacketType;
    }


    @Override
    protected void convert(ViewHolder holder, final int position, final ProblemBean problem) {


        holder.setText(R.id.tv_serial_number, position + 1 + ".")
                .setText(R.id.tv_problem_name, (problem.getType() == 0 ?
                        mContext.getString(R.string.single_choice) : problem.getType() == 1
                        ? mContext.getString(R.string.muti_choice)
                        : mContext.getString(R.string.input_blank)) + problem.getProblem());

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
        EditRedPackgeProblemInAdapter editRedPackgeProblemInAdapter = new EditRedPackgeProblemInAdapter(mContext, problem.getItem(), R.layout.item_anser_layout,mRedPacketType);
        innerRecycle.setAdapter(editRedPackgeProblemInAdapter);


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