package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SheetAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SheetAdapter(@Nullable List<String> data) {
        super(R.layout.item_sheet_menu,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
