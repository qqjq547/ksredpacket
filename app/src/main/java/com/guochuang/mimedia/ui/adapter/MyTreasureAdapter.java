package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyTreasureAdapter extends BaseQuickAdapter<Snatch,BaseViewHolder> {
    public MyTreasureAdapter(@Nullable List<Snatch> data) {
        super(R.layout.item_treasure,data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Snatch item) {
        GlideImgManager.loadImage(mContext,item.getSnatchImg(),(ImageView) helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_peroid,String.format(mContext.getString(R.string.format_peroid),item.getSnatchPeriods()));
        helper.setText(R.id.tv_name,item.getSnatchName());
        helper.setText(R.id.tv_award_number,item.getLuckNum());
        helper.setText(R.id.tv_people_time,String.valueOf(item.getPayNum()));
        helper.setText(R.id.tv_joined_time,item.getPayDate());
       switch (item.getStatus()){
           case 1://1售卖中
               if (item.getIsPay()==0){//未支付
                   helper.setGone(R.id.tv_pay,true);
                   helper.addOnClickListener(R.id.tv_pay);
                   helper.setGone(R.id.iv_arrow,false);
               }else {
                   helper.setGone(R.id.tv_pay,false);
                   helper.setGone(R.id.iv_arrow,true);
                   helper.addOnClickListener(R.id.lin_join_people_time);
               }
               helper.setGone(R.id.tv_address,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,false);
               helper.setGone(R.id.lin_progress,true);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               helper.setText(R.id.tv_progress,String.valueOf((int)(item.getRate()*100))+mContext.getString(R.string.percent));
               ProgressBar pb=helper.getView(R.id.pb_progress);
               pb.setProgress((int)(item.getRate()*100));
               break;
           case 2://2售罄等待开奖
               helper.setGone(R.id.iv_arrow,true);
               helper.addOnClickListener(R.id.lin_join_people_time);
               helper.setGone(R.id.tv_address,false);
               helper.setGone(R.id.tv_pay,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,false);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,true);
               helper.setGone(R.id.tv_waiting_send,false);
               break;
           case 3://3已开奖
               helper.setGone(R.id.iv_arrow,true);
               helper.addOnClickListener(R.id.lin_join_people_time);
               helper.setGone(R.id.tv_address,false);
               helper.setGone(R.id.tv_pay,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,true);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               if (item.getIsWin()==1){//是否中奖
                   helper.setImageResource(R.id.iv_award,R.drawable.ic_get_award);
                   if (item.getType()==1) {//实物
                       if (item.getShowAddress()==1){//未填写地址,显示填写地址
                           helper.setGone(R.id.tv_address, true);
                           helper.addOnClickListener(R.id.tv_address);
                       }else {//已填写地址，显示快递信息
                           helper.setGone(R.id.lin_comment,true);
                           helper.setGone(R.id.tv_comment,false);
                           helper.setGone(R.id.v_vertical_line,false);
                           helper.setGone(R.id.tv_express,true);
                           helper.addOnClickListener(R.id.tv_express);
                       }
                   }else {
                       helper.setGone(R.id.tv_address,false);
                       helper.setGone(R.id.tv_waiting_send,true);
                   }
               }else {
                   helper.setImageResource(R.id.iv_award,R.drawable.ic_not_award);
                   helper.setGone(R.id.tv_address,false);
               }
               break;
           case 4://4已发货
           case 5://5结束
               helper.setGone(R.id.iv_arrow,true);
               helper.addOnClickListener(R.id.lin_join_people_time);
               helper.setGone(R.id.tv_address,false);
               helper.setGone(R.id.tv_pay,false);
               helper.setGone(R.id.lin_comment,true);
               helper.setGone(R.id.fl_award,true);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               if (item.getIsWin()==1){//是否中奖
                   helper.setImageResource(R.id.iv_award,R.drawable.ic_get_award);
                   if (item.getType()==1) {//实物,有快递信息
                       helper.setGone(R.id.tv_comment,true);
                       helper.setGone(R.id.v_vertical_line,true);
                       helper.setGone(R.id.tv_express,true);
                       helper.addOnClickListener(R.id.tv_comment);
                       helper.addOnClickListener(R.id.tv_express);
                       }else {//无快递信息
                       helper.setGone(R.id.tv_comment,true);
                       helper.setGone(R.id.v_vertical_line,false);
                       helper.setGone(R.id.tv_express,false);
                       helper.addOnClickListener(R.id.tv_comment);
                   }
               }else {
                   helper.setImageResource(R.id.iv_award,R.drawable.ic_not_award);
                   helper.setGone(R.id.lin_comment,false);
               }
               break;
           case 6://6到期未满
                   helper.setGone(R.id.tv_pay,false);
                   helper.setGone(R.id.iv_arrow,true);
                   helper.addOnClickListener(R.id.lin_join_people_time);
               helper.setImageResource(R.id.iv_award,R.drawable.ic_dateline_unsale);
               helper.setGone(R.id.tv_address,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,false);
               helper.setGone(R.id.lin_progress,true);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               helper.setText(R.id.tv_progress,String.valueOf((int)(item.getRate()*100))+mContext.getString(R.string.percent));
               ProgressBar pb1=helper.getView(R.id.pb_progress);
               pb1.setProgress((int)(item.getRate()*100));
               break;
       }
    }
}
