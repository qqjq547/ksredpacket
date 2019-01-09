package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class PictureVerticalAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PictureVerticalAdapter(@Nullable List<String> data) {
        super(R.layout.item_picture_vertical,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideImgManager.loadCornerImage(mContext,item,(ImageView) helper.getView(R.id.iv_picture),8,true);
    }
}
