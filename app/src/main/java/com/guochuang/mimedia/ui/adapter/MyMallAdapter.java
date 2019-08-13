package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.MyMallRecord;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class MyMallAdapter extends BaseQuickAdapter<MyMallRecord,BaseViewHolder> {
    public MyMallAdapter(@Nullable List<MyMallRecord> data) {
        super(R.layout.item_my_mall,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMallRecord item) {
        switch (item.getRegionLevel()){
            case 1:
                helper.setText(R.id.tv_node_name,"省节点");
                break;
            case 2:
                helper.setText(R.id.tv_node_name,"市节点");
                break;
            case 3:
                helper.setText(R.id.tv_node_name,"区节点");
                break;
        }
        helper.setText(R.id.tv_node_address,item.getAddress());
        helper.setText(R.id.tv_coin,item.getTotalQc()+" "+mContext.getString(R.string.money_unit_qc));
        helper.setText(R.id.tv_time,item.getCreateDate());
        helper.setText(R.id.tv_index,String.valueOf(item.getNodeNumber()));
        if (item.getAuctionStatus()==0){
            helper.setGone(R.id.tv_add,true);
            helper.setGone(R.id.tv_again,true);
            helper.setGone(R.id.tv_add_result,true);
            helper.setGone(R.id.tv_result,false);
            helper.setImageResource(R.id.iv_state,R.drawable.bg_node_state_normal);
        }else {
            helper.setGone(R.id.tv_add,false);
            helper.setGone(R.id.tv_again,false);
            helper.setGone(R.id.tv_add_result,false);
            helper.setGone(R.id.tv_result,true);
            if (item.getAuctionStatus()==2){
                helper.setImageResource(R.id.iv_state,R.drawable.bg_node_state_uncheck);
            }else if(item.getAuctionStatus()==3){
                helper.setImageResource(R.id.iv_state,R.drawable.bg_node_state_check);
            }
        }
      helper.addOnClickListener(R.id.tv_add);
      helper.addOnClickListener(R.id.tv_add_result);
      helper.addOnClickListener(R.id.tv_result);
    }
}
