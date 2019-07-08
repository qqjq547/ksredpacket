package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class RedbagReplyChildAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public RedbagReplyChildAdapter(@Nullable List<String> data) {
        super(R.layout.item_redbag_comment_child,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_reply_content,item);
    }
}
