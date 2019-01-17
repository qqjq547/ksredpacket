package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyTreasureAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MyTreasureAdapter(@Nullable List<String> data) {
        super(R.layout.item_treasure,data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       switch (item){
           case "0"://已售罄状态，显示已售罄标识和参与人次、参与时间
               helper.setGone(R.id.lin_address,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,false);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,true);
               helper.setGone(R.id.tv_waiting_send,false);
               break;
           case "1"://已开奖状态，未完善收货地址，显示幸运号码和参与人次、参与时间、获奖标识、显示‘完善收货地址’。
               helper.setGone(R.id.lin_address,true);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,true);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               helper.addOnClickListener(R.id.tv_address);
               helper.setImageResource(R.id.iv_award,R.drawable.ic_get_award);
               break;
           case "2"://可购买状态，显示进度和参与人次。
               helper.setGone(R.id.lin_address,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,false);
               helper.setGone(R.id.lin_progress,true);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               break;
           case "3"://已开奖状态并且已经完善收货信息、显示幸运号码、参与人次、参与时间、获奖标识、晒单入口、快递信息
               helper.setGone(R.id.lin_address,false);
               helper.setGone(R.id.lin_comment,true);
               helper.setGone(R.id.fl_award,true);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,false);
               helper.addOnClickListener(R.id.tv_comment);
               helper.addOnClickListener(R.id.tv_express);
               helper.setImageResource(R.id.iv_award,R.drawable.ic_not_award);
               break;
           case "4"://虚拟商品获奖显示幸运号、参与人次、参与时间、发放状态
               helper.setGone(R.id.lin_address,false);
               helper.setGone(R.id.lin_comment,false);
               helper.setGone(R.id.fl_award,true);
               helper.setGone(R.id.lin_progress,false);
               helper.setGone(R.id.tv_has_selle_all,false);
               helper.setGone(R.id.tv_waiting_send,true);
               helper.setGone(R.id.iv_award,false);
               break;
       }
    }
}
