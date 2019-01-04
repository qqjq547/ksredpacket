package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagUser;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class RedbagJoinedAdapter extends BaseQuickAdapter<RedbagUser,BaseViewHolder> {
    public RedbagJoinedAdapter(@Nullable List<RedbagUser> data) {
        super(R.layout.item_redbag_joined,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedbagUser item) {
        GlideImgManager.loadCircleImage(mContext,item.getDrawAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getDrawNickName());
        helper.setText(R.id.tv_time,item.getCreateDate());
        helper.setText(R.id.tv_coin,"+"+item.getDrawCoin());
    }
}
