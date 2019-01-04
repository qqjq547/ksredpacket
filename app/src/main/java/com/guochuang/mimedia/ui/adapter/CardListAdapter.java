package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CardList;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class CardListAdapter extends BaseQuickAdapter<CardList, BaseViewHolder> {
    public CardListAdapter(@Nullable List<CardList> data) {
        super(R.layout.item_card, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardList item) {
        helper.setText(R.id.tv_bank_name, item.getBankName());
        helper.setText(R.id.tv_bank_number, item.getCardNumber().replaceAll("(.{4})", "$1\t\t"));
        GlideImgManager.loadImage(mContext, item.getUrl(), (ImageView) helper.getView(R.id.iv_bank_icon));
    }
}
