package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    boolean selectABle=false;
    private int selectPos=0;
    public AddressAdapter(@Nullable List<String> data) {
        super(R.layout.item_address,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (helper.getAdapterPosition()==0){
            helper.setGone(R.id.tv_default,true);
        }else {
            helper.setGone(R.id.tv_default,false);
        }
        if (selectABle){
            helper.setGone(R.id.iv_check,true);
            if (selectPos==helper.getAdapterPosition()){
                helper.setGone(R.id.iv_check,true);
            }else {
                helper.setGone(R.id.iv_check,false);
            }
        }else {
            helper.setGone(R.id.iv_check,false);
        }

    }

    public void setSelectAble(boolean enable) {
        selectABle=enable;
        notifyDataSetChanged();
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }

    public int getSelectPos() {
        return selectPos;
    }
}
