package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.MyCity;
import com.guochuang.mimedia.mvp.model.MyRegion;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class MyCityAdapter extends BaseQuickAdapter<MyRegion.MyRegionListDtoBean, BaseViewHolder> {
    public MyCityAdapter(@Nullable List<MyRegion.MyRegionListDtoBean> data) {
        super(R.layout.item_my_city, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyRegion.MyRegionListDtoBean item) {
        helper.setText(R.id.tv_city_name,item.getWhereRegion());
        if (item.isShow()){
            helper.setGone(R.id.lin_auction,true);
            if (item.isAuction()){//锁定
                helper.setImageResource(R.id.iv_finger_pwd,R.drawable.ic_my_city_check_green_nor);
            }else {
                helper.setImageResource(R.id.iv_finger_pwd,R.drawable.ic_my_city_check_green_checked);
            }
        }else {
            helper.setGone(R.id.lin_auction,false);
        }

        helper.addOnClickListener(R.id.iv_finger_pwd);
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.iv_left_header));
        helper.setText(R.id.tv_bid_price,String.format(mContext.getString(R.string.format_qc),item.getBiddingPrice()));
        helper.setText(R.id.tv_income_share_price,String.format(mContext.getString(R.string.format_seal),item.getProfit()));
        helper.addOnClickListener(R.id.tv_notice_alter);
        helper.setText(R.id.tv_notice,item.getContent());
        List<String> pictureArr=item.getPicture();
        ImageView ivImage1=helper.getView(R.id.iv_image_1);
        ImageView ivImage2=helper.getView(R.id.iv_image_2);
        ImageView ivImage3=helper.getView(R.id.iv_image_3);
        if (pictureArr!=null&&pictureArr.size()>0){
            if (pictureArr.size()>0){
                ivImage1.setVisibility(View.VISIBLE);
                GlideImgManager.loadImage(mContext,pictureArr.get(0),ivImage1);
            }else {
                ivImage1.setVisibility(View.GONE);
            }
            if (pictureArr.size()>1){
                ivImage2.setVisibility(View.VISIBLE);
                GlideImgManager.loadImage(mContext,pictureArr.get(1),ivImage2);
            }else {
                ivImage2.setVisibility(View.GONE);
            }
            if (pictureArr.size()>2){
                ivImage3.setVisibility(View.VISIBLE);
                GlideImgManager.loadImage(mContext,pictureArr.get(2),ivImage3);
            }else {
                ivImage3.setVisibility(View.GONE);
            }
        }else {
            ivImage1.setVisibility(View.GONE);
            ivImage2.setVisibility(View.GONE);
            ivImage3.setVisibility(View.GONE);
        }
    }
}
