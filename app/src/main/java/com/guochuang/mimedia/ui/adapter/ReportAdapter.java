package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends BaseQuickAdapter<DictionaryType,BaseViewHolder> {
    List<DictionaryType> selectArr=new ArrayList<>();
    public ReportAdapter(@Nullable List<DictionaryType> data) {
        super(R.layout.item_report,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictionaryType item) {
        helper.getView(R.id.iv_state).setSelected(selectArr.contains(item));
        helper.setText(R.id.tv_content,item.getName());
    }
    public void onClick(int position){
        if (selectArr.contains(getItem(position))){
            selectArr.remove(getItem(position));
        }else {
            selectArr.add(getItem(position));
        }
        notifyDataSetChanged();
    }
    public List<DictionaryType> getSelectItem(){
        return selectArr;
    }
}
