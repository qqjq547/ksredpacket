package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class InfoAdapter extends BaseQuickAdapter<InfoItem,BaseViewHolder> {

    String keyWord;
    public InfoAdapter(@Nullable List<InfoItem> data) {
        super(R.layout.item_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoItem item) {
        if (TextUtils.isEmpty(keyWord)){
            helper.setText(R.id.tv_title,item.getTitle());
        }else {
            int startIndex=item.getTitle().indexOf(keyWord);
            if (startIndex>=0){
                SpannableString spanstr=new SpannableString(item.getTitle());
                spanstr.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_info_tab_select)),startIndex,startIndex+keyWord.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else {
                helper.setText(R.id.tv_title,item.getTitle());
            }
        }
        helper.setText(R.id.tv_from,item.getAuthor());
        helper.setText(R.id.tv_comment_num,String.valueOf(item.getCommentNumber()));
        if (TextUtils.isEmpty(item.getPicture())){
            helper.setGone(R.id.lin_content_image,false);
            helper.setGone(R.id.iv_right_image,false);
            return;
        }
        String[] imageUrls=item.getPicture().split(",");
        if (imageUrls==null||imageUrls.length==0){
            helper.setGone(R.id.lin_content_image,false);
            helper.setGone(R.id.iv_right_image,false);
        }else {
            if (imageUrls.length>=3){
                helper.setGone(R.id.lin_content_image,true);
                helper.setGone(R.id.iv_right_image,false);
                GlideImgManager.loadCornerImage(mContext,imageUrls[0],(ImageView) helper.getView(R.id.iv_image_1),5,true);
                GlideImgManager.loadCornerImage(mContext,imageUrls[1],(ImageView) helper.getView(R.id.iv_image_2),5,true);
                GlideImgManager.loadCornerImage(mContext,imageUrls[2],(ImageView) helper.getView(R.id.iv_image_3),5,true);
            }else {
                helper.setGone(R.id.lin_content_image,false);
                helper.setGone(R.id.iv_right_image,true);
                GlideImgManager.loadCornerImage(mContext,imageUrls[0],(ImageView) helper.getView(R.id.iv_right_image),5,true);
            }
        }

    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        notifyDataSetChanged();
    }
}
