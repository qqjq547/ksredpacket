package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.ShareBg;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.guochuang.mimedia.tools.glide.RadiusTransformation;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class ShareAdapter extends BaseQuickAdapter<ShareBg, BaseViewHolder> {

    public ShareAdapter(int layoutResId, @Nullable List<ShareBg> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ShareBg item) {
//        holder.setText(R.id.tv_des, item.getDes());
        GlideApp.with(mContext).load(item.getZoomImage()).transform(new RadiusTransformation(mContext, 5)).into((ImageView) (holder.getView(R.id.iv_bg)));
    }
}
