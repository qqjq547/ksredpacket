package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MenuGroupAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    int selectPos=0;
    public MenuGroupAdapter(@Nullable List<String> data) {
        super(R.layout.item_menu,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      if (selectPos==helper.getAdapterPosition()){
          helper.setTextColor(R.id.tv_text,mContext.getResources().getColor(R.color.text_red));
          helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_white));
      }else {
          helper.setTextColor(R.id.tv_text,mContext.getResources().getColor(R.color.text_black));
          helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
      }
      helper.setText(R.id.tv_text,item);
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }

    public int getSelectPos() {
        return selectPos;
    }
}
