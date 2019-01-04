package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.Reply;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class ReplyAdapter extends BaseQuickAdapter<Reply,BaseViewHolder> {
    public ReplyAdapter(@Nullable List<Reply> data) {
        super(R.layout.item_reply,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Reply item) {
        helper.setText(R.id.tv_nickname,item.getNickName());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_time,CommonUtil.getFriendTime(item.getCommentTimeStamp()));
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
    }
}
