package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class TextAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    int selectPos=-1;
    public TextAdapter(@Nullable List<String> data) {
        super(R.layout.item_text,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      helper.setText(R.id.tv_text,item);
      if (selectPos==helper.getAdapterPosition()){
          helper.setTextColor(R.id.tv_text,mContext.getResources().getColor(R.color.text_red));
      }else {
          helper.setTextColor(R.id.tv_text,mContext.getResources().getColor(R.color.text_black));
      }
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }
}
