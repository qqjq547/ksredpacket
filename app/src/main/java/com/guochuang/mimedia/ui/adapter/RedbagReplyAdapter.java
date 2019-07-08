package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class RedbagReplyAdapter extends BaseMultiItemQuickAdapter<RedPacketReply,BaseViewHolder> {
    public RedbagReplyAdapter(@Nullable List<RedPacketReply> data) {
        super(data);
        addItemType(RedPacketReply.REPLY, R.layout.item_redbag_comment);
        addItemType(RedPacketReply.OTHER, R.layout.item_redbag_other);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacketReply item) {
        switch (helper.getItemViewType()) {
            case RedPacketReply.REPLY:
                GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView)helper.getView(R.id.iv_header));
                helper.setText(R.id.tv_name,item.getNickName());
                helper.setText(R.id.tv_content,item.getContent());
                if (item.getIsCanReply()==1) {
                    helper.setGone(R.id.tv_reply,true);
                    helper.addOnClickListener(R.id.tv_reply);
                }else {
                    helper.setGone(R.id.tv_reply,false);
                }
                RecyclerView reChild=helper.getView(R.id.rv_child_comment);
                reChild.setLayoutManager(new LinearLayoutManager(mContext,OrientationHelper.VERTICAL,false));
                RedbagReplyChildAdapter adapter=new RedbagReplyChildAdapter(item.getCommentList());
                reChild.setAdapter(adapter);
                break;
            case RedPacketReply.OTHER:

                break;
        }
    }
}
