package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.io.File;
import java.util.List;

public class PickImageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PickImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_pick_image,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPicture=helper.getView(R.id.iv_picture);
        if(TextUtils.isEmpty(item)){
            ivPicture.setImageResource(R.drawable.ic_add_pic);
            helper.setGone(R.id.iv_delete,false);
        }else {
            if (item.startsWith("http")){
                GlideImgManager.loadImage(mContext,item,ivPicture);
            }else {
                GlideImgManager.loadImage(mContext,new File(item),ivPicture);
            }
            helper.setGone(R.id.iv_delete,true);
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
