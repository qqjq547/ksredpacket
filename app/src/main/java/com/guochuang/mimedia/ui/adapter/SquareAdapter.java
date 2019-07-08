package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.Square;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class SquareAdapter extends BaseQuickAdapter<Square,BaseViewHolder> {

    public SquareAdapter(@Nullable List<Square> data) {
        super(R.layout.item_square,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Square item) {
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getNickName());
        if (item.getPicture()==null||item.getPicture().size()==0){
            helper.setGone(R.id.iv_single_image,false);
            helper.setGone(R.id.lin_content_image,false);
        }else {
            if (item.getPicture().size()>=3){
                helper.setGone(R.id.iv_single_image,false);
                helper.setGone(R.id.lin_content_image,true);
                GlideImgManager.loadCornerImage(mContext,item.getPicture().get(0),(ImageView) helper.getView(R.id.iv_image_1),5,true);
                GlideImgManager.loadCornerImage(mContext,item.getPicture().get(1),(ImageView) helper.getView(R.id.iv_image_2),5,true);
                GlideImgManager.loadCornerImage(mContext,item.getPicture().get(2),(ImageView) helper.getView(R.id.iv_image_3),5,true);
            }else {
                helper.setGone(R.id.iv_single_image,true);
                helper.setGone(R.id.lin_content_image,false);
                GlideImgManager.loadCornerImage(mContext,item.getPicture().get(0),(ImageView) helper.getView(R.id.iv_single_image),5,true);
            }
        }
        if (TextUtils.isEmpty(item.getContent())&&(item.getPicture()==null||item.getPicture().isEmpty())){
            helper.setText(R.id.tv_message,R.string.check_redbag_detail);
            helper.setTextColor(R.id.tv_message,mContext.getResources().getColor(R.color.text_blue));
        }else {
            helper.setText(R.id.tv_message,item.getContent());
            helper.setTextColor(R.id.tv_message,mContext.getResources().getColor(R.color.text_black));
        }
    }
}
