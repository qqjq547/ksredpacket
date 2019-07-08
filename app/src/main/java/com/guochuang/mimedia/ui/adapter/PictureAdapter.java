package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.view.SquareImageView;

import java.util.List;

public class PictureAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PictureAdapter(@Nullable List<String> data) {
        super(R.layout.item_picture,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideImgManager.loadCornerImage(mContext,item,(SquareImageView) helper.getView(R.id.siv_image),5,true);
    }
}
