package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.NestHomeAd;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class HoneyAdapter extends BaseQuickAdapter<NestHomeAd,BaseViewHolder> {
    public HoneyAdapter(@Nullable List<NestHomeAd> data) {
        super(R.layout.item_honey,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestHomeAd item) {
       helper.setText(R.id.tv_name,item.getShortMsg());
        GlideImgManager.loadCornerImage(mContext,item.getCoverPicture(),(ImageView) helper.getView(R.id.iv_icon),3,true);
    }
}
