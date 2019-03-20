package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class SendRedbagAdapter extends BaseQuickAdapter<RedbagRecord,BaseViewHolder> {

    public SendRedbagAdapter(@Nullable List<RedbagRecord> data) {
        super(R.layout.item_send_redbag,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, RedbagRecord item) {
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getNickName());
        if (item.getPictureList()!=null&&item.getPictureList().size()>0){
            helper.setGone(R.id.iv_content_image,true);
            GlideImgManager.loadImage(mContext,item.getPictureList().get(0).getPicture(),(ImageView) helper.getView(R.id.iv_content_image));
        }else {
            helper.setGone(R.id.iv_content_image,false);
        }
        if (TextUtils.isEmpty(item.getContent())&&(item.getPictureList()==null||item.getPictureList().isEmpty())){
            helper.setText(R.id.tv_content_title,R.string.check_redbag_detail);
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_blue));
        }else {
            helper.setText(R.id.tv_content_title,item.getContent());
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_black));
        }
        helper.setText(R.id.tv_watch,String.valueOf(item.getBrowserNumber()));
        helper.setText(R.id.tv_comment,String.valueOf(item.getCommentNumber()));
        helper.setText(R.id.tv_zan,String.valueOf(item.getPraiseNumber()));
        helper.setText(R.id.tv_fav,String.valueOf(item.getFavoriteNumber()));
    }
}
