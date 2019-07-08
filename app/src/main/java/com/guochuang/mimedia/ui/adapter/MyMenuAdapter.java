package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.MyMenuItem;

import java.util.List;

public class MyMenuAdapter extends BaseQuickAdapter<MyMenuItem,BaseViewHolder> {

    public MyMenuAdapter(@Nullable List<MyMenuItem> data) {
        super(R.layout.item_my_menu,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MyMenuItem item) {
        helper.setImageResource(R.id.iv_icon,item.getIconResId());
        helper.setText(R.id.tv_name,item.getNameResId());
    }
}
