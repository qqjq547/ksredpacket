package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.guochuang.mimedia.view.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.view.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class LookVideoProblemInAdapter extends CommonRecyclerAdapter<LookVideoResult.QuestionListBean.OptionsListBean> {

    public LookVideoProblemInAdapter(Context context, List<LookVideoResult.QuestionListBean.OptionsListBean> optionsListBeans, int itemlayout) {
        super(context, optionsListBeans, itemlayout);
    }

    @Override
    protected void convert(ViewHolder holder, int position, LookVideoResult.QuestionListBean.OptionsListBean optionsListBean) {
        holder.setText(R.id.tv_option, optionsListBean.getOptionName() + "." + optionsListBean.getOptionValue())
                .setViewVisibility(R.id.iv_isanswer, optionsListBean.getIsAnswer() == 1 ? View.VISIBLE : View.GONE);
        TextView view = holder.getView(R.id.tv_option);
        if(optionsListBean.getIsAnswer() == 1) {
            view.setTextColor(mContext.getResources().getColor(R.color.color_ff7519));
        }else {
            view.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }


    }
}
