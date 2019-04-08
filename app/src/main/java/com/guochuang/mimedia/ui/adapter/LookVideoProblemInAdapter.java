package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class LookVideoProblemInAdapter extends BaseQuickAdapter<LookVideoResult.QuestionListBean.OptionsListBean, BaseViewHolder> {

    public LookVideoProblemInAdapter( @Nullable List<LookVideoResult.QuestionListBean.OptionsListBean> data,int layoutResId) {
        super(layoutResId, data);
    }




    @Override
    protected void convert(BaseViewHolder holder, LookVideoResult.QuestionListBean.OptionsListBean optionsListBean) {

        holder.setText(R.id.tv_option, optionsListBean.getOptionName() + "." + optionsListBean.getOptionValue())
                .setGone(R.id.iv_isanswer, optionsListBean.getIsAnswer() == 1 ? true : false);
        TextView view = holder.getView(R.id.tv_option);
        if(optionsListBean.getIsAnswer() == 1) {
            view.setTextColor(mContext.getResources().getColor(R.color.color_ff7519));
        }else {
            view.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }
    }
}
