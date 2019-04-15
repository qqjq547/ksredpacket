package com.guochuang.mimedia.ui.adapter;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.tools.glide.RadiusTransformation;
import com.sz.gcyh.KSHongBao.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PickVideoAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PickVideoAdapter(@Nullable List<String> data) {
        super(R.layout.item_pick_image,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPicture=helper.getView(R.id.iv_picture);
        if(TextUtils.isEmpty(item)){
            ivPicture.setImageResource(R.drawable.ic_add_video);
            helper.setGone(R.id.iv_delete,false);
        }else {
            if(item.startsWith("http")) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                //设置数据源为该文件对象指定的绝对路径
                mmr.setDataSource(item, new HashMap<String, String>());
                //获得视频第一帧的Bitmap对象
                Bitmap bitmap = mmr.getFrameAtTime();
                GlideApp.with(mContext)
                        .load(bitmap)
                        .transform(new RadiusTransformation(mContext,CommonUtil.dip2px(mContext,8)))
                        .into(ivPicture);
            }else {
                GlideApp.with(mContext)
                        .load(Uri.parse("file://" +item))
                        .transform(new RadiusTransformation(mContext,CommonUtil.dip2px(mContext,8)))
                        .into(ivPicture);
            }

            helper.setGone(R.id.iv_delete,true);
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
