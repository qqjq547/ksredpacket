package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.PictureBean;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.view.SquareImageView;

import java.util.List;

public class RedbagPictureAdapter extends BaseQuickAdapter<PictureBean,BaseViewHolder> {

    public RedbagPictureAdapter(@Nullable List<PictureBean> data) {
        super(R.layout.item_picture,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, PictureBean item) {
        GlideImgManager.loadCornerImage(mContext,item.getPicture(),(SquareImageView) helper.getView(R.id.siv_image),5,true);
    }
}
