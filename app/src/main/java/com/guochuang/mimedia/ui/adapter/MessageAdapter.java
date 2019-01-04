package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.mvp.model.Message;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class MessageAdapter extends BaseMultiItemQuickAdapter<Message,BaseViewHolder> {
    public MessageAdapter(@Nullable List<Message> data) {
        super(data);
        addItemType(0, R.layout.item_message_notice);
        addItemType(1, R.layout.item_message_redbag);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_name,item.getTitle());
                helper.setText(R.id.tv_time,item.getCreateDate());
                break;
            case 1:
                GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
                helper.setText(R.id.tv_name,item.getNickName());
                helper.setText(R.id.tv_summary,item.getTitle());
                helper.setText(R.id.tv_reply_content,item.getContent());
                helper.addOnClickListener(R.id.lin_content);
                if (item.getIsReply() == 0){
                    helper.setGone(R.id.tv_reply,true);
                    helper.addOnClickListener(R.id.tv_reply);
                }else {
                    helper.setGone(R.id.tv_reply,false);
                }
                if (TextUtils.isEmpty(item.getSourcePicture())){
                    helper.setGone(R.id.iv_content_image,false);
                }else {
                    helper.setGone(R.id.iv_content_image,true);
                    GlideImgManager.loadImage(mContext,item.getSourcePicture(),(ImageView) helper.getView(R.id.iv_content_image));
                }
                if (TextUtils.isEmpty(item.getSourceContent())&&TextUtils.isEmpty(item.getSourcePicture())){
                    helper.setText(R.id.tv_content_title,R.string.check_redbag_detail);
                    helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_blue));
                }else {
                    helper.setText(R.id.tv_content_title,item.getSourceContent());
                    helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_black));
                }
                helper.setText(R.id.tv_time,item.getCreateDate());
                break;
        }
    }

}
