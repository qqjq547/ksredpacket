package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RecommedUser;

import java.util.List;

public class RecommendDetailAdapter extends BaseQuickAdapter<RecommedUser,BaseViewHolder> {
    public RecommendDetailAdapter(@Nullable List<RecommedUser> data) {
        super(R.layout.item_recommend_detail,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommedUser item) {
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_time,item.getRegisterDate());
        helper.setText(R.id.tv_ksb,item.getBonus());
        if (TextUtils.isEmpty(item.getAvatar())){
            GlideImgManager.loadImage(mContext,R.drawable.default_head,(ImageView)helper.getView(R.id.iv_avatar));
        }else {
            GlideImgManager.loadCornerImage(mContext,item.getAvatar(),(ImageView)helper.getView(R.id.iv_avatar),3);
        }
        helper.setText(R.id.tv_mobile,item.getMobile());
    }
}
