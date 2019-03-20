package com.guochuang.mimedia.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PickImageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private String mType;
    public PickImageAdapter(@Nullable List<String> data,String type) {
        super(R.layout.item_pick_image,data);
        mType = type;
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPicture=helper.getView(R.id.iv_picture);
        if(TextUtils.isEmpty(item)){
            if(Constant.RED_PACKET_TYPE_VIDEO.equals(mType)) {
                //视频添加图标
                ivPicture.setImageResource(R.drawable.ic_add_video);
            }else {
                ivPicture.setImageResource(R.drawable.ic_add_pic);
            }

            helper.setGone(R.id.iv_delete,false);
        }else {

            if(Constant.RED_PACKET_TYPE_VIDEO.equals(mType)) {
                //加载视频 到列表中

                if(item.startsWith("http")) {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    //设置数据源为该文件对象指定的绝对路径
                    mmr.setDataSource(item, new HashMap<String, String>());
                    //获得视频第一帧的Bitmap对象
                    Bitmap bitmap = mmr.getFrameAtTime();
                    ivPicture.setImageBitmap(bitmap);

                }else {
                    Glide.with(mContext)
                            .load(Uri.parse("file://" +item))
                            .into(ivPicture);
                }


            }else {
                if (item.startsWith("http")){
                    GlideImgManager.loadImage(mContext,item,ivPicture);
                }else {
                    GlideImgManager.loadImage(mContext,new File(item),ivPicture);
                }
            }

            helper.setGone(R.id.iv_delete,true);
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
