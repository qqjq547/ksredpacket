package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RedbagReceived;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class ReceiveRedbagAdapter extends BaseQuickAdapter<RedbagReceived,BaseViewHolder> {

    public ReceiveRedbagAdapter(@Nullable List<RedbagReceived> data) {
        super(R.layout.item_receive_redbag,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, RedbagReceived item) {
        GlideImgManager.loadCornerImage(mContext,item.getSenderAvatar(),(ImageView) helper.getView(R.id.iv_avatar),360,true);
        helper.setText(R.id.tv_name,item.getSenderNickName());
        if (TextUtils.isEmpty(item.getRedPacketContent())&&TextUtils.isEmpty(item.getRedPacketPicture())){
            helper.setGone(R.id.lin_content,false);
            helper.setText(R.id.tv_content_title,R.string.check_redbag_detail);
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_blue));
        }else {
            helper.setGone(R.id.lin_content,true);
            helper.setText(R.id.tv_content_title,item.getRedPacketContent());
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_black));
            if (!TextUtils.isEmpty(item.getRedPacketPicture())) {
                helper.setGone(R.id.iv_content_image,true);
                GlideImgManager.loadCornerImage(mContext,item.getRedPacketPicture(),(ImageView) helper.getView(R.id.iv_content_image),8,true);
            }else {
                helper.setGone(R.id.iv_content_image,false);
            }
        }
        helper.setText(R.id.tv_reward,String.format(mContext.getString(R.string.format_get_ksb),item.getDrawCoin()));
    }
}
