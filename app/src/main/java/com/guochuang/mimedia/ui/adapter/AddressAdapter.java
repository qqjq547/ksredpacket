package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.Address;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<Address,BaseViewHolder> {
    boolean selectABle=false;
    private int selectPos=0;
    public AddressAdapter(@Nullable List<Address> data) {
        super(R.layout.item_address,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setText(R.id.tv_name,item.getTrackName());
        helper.setText(R.id.tv_mobile,item.getTrackMobile());
        helper.setText(R.id.tv_address,item.getProvince()+item.getCity()+item.getDistrict()+item.getAddress());
        if (selectABle){
            helper.setGone(R.id.iv_check,true);
            if (selectPos==helper.getAdapterPosition()){
                helper.setImageResource(R.id.iv_check,R.drawable.bg_address_check_select);
            }else {
                helper.setImageResource(R.id.iv_check,R.drawable.bg_address_check_nor);
            }
            helper.setGone(R.id.iv_edit,false);
        }else {
            helper.setGone(R.id.iv_check,false);
            helper.setGone(R.id.iv_edit,true);
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
