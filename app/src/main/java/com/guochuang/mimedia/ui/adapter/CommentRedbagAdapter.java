package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CommentRedbag;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class CommentRedbagAdapter extends BaseQuickAdapter<CommentRedbag,BaseViewHolder> {

    public CommentRedbagAdapter(@Nullable List<CommentRedbag> data) {
        super(R.layout.item_comment_redbag,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CommentRedbag item) {
          helper.setText(R.id.tv_name,item.getCommentContent());
         helper.addOnClickListener(R.id.tv_delete);
        if (item.getPictureList()!=null&&item.getPictureList().size()>0){
            helper.setGone(R.id.iv_content_image,true);
            GlideImgManager.loadImage(mContext,item.getPictureList().get(0).getPicture(),(ImageView) helper.getView(R.id.iv_content_image));
        }else if(!TextUtils.isEmpty(item.getCoverUrl())) {
            helper.setGone(R.id.iv_content_image,true);
            GlideImgManager.loadImage(mContext,item.getCoverUrl(),(ImageView) helper.getView(R.id.iv_content_image));
        }else{
            helper.setGone(R.id.iv_content_image,false);
        }
        if (TextUtils.isEmpty(item.getRedPacketContent())&&(item.getPictureList()==null||item.getPictureList().isEmpty())){
            helper.setText(R.id.tv_content_title,R.string.check_redbag_detail);
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_blue));
        }else {
            helper.setText(R.id.tv_content_title,item.getRedPacketContent());
            helper.setTextColor(R.id.tv_content_title,mContext.getResources().getColor(R.color.text_black));
        }
        helper.setText(R.id.tv_watch,String.valueOf(item.getBrowserNumber()));
        helper.setText(R.id.tv_comment,String.valueOf(item.getCommentNumber()));
        helper.setText(R.id.tv_zan,String.valueOf(item.getPraiseNumber()));
        helper.setText(R.id.tv_fav,String.valueOf(item.getFavoriteNumber()));
    }
}
