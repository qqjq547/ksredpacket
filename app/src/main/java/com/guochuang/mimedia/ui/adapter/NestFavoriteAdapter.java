package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.model.NestFavorite;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class NestFavoriteAdapter extends BaseQuickAdapter<NestFavorite,BaseViewHolder> {

    public NestFavoriteAdapter(@Nullable List<NestFavorite> data) {
        super(R.layout.item_nest_favorite,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestFavorite item) {
        GlideImgManager.loadCircleImage(mContext,item.getPublisherAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_nickname,item.getPublisherNickName());
        helper.setText(R.id.tv_nickname,item.getShortMsg());
        GlideImgManager.loadCircleImage(mContext,item.getCoverPicture(),(ImageView) helper.getView(R.id.iv_cover));
    }

}
