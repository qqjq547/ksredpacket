package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RegistUser;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class IncreaseDetailAdapter extends BaseQuickAdapter<RegistUser,BaseViewHolder> {

    public IncreaseDetailAdapter(@Nullable List<RegistUser> data) {
        super(R.layout.item_increase_detail,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, RegistUser item) {
       helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_time,item.getRegisterDate());
    }
}
