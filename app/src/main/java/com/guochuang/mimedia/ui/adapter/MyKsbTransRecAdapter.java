package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.MyKsbTransRec;

import java.util.List;

public class MyKsbTransRecAdapter extends BaseQuickAdapter<MyKsbTransRec, BaseViewHolder> {
    public MyKsbTransRecAdapter(@Nullable List<MyKsbTransRec> data) {
        super(R.layout.item_my_ksb_trans_rec, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyKsbTransRec item) {
        switch (item.getPayType()){
            case Constant.TRANSFER_WXPAY:
                helper.setImageResource(R.id.iv_icon,R.drawable.ic_wechat_small);
                break;
            case Constant.TRANSFER_ALIPAY:
                helper.setImageResource(R.id.iv_icon,R.drawable.ic_alipay_small);
                break;
            case Constant.TRANSFER_BANKCARD:
                helper.setImageResource(R.id.iv_icon,R.drawable.ic_bankcard_small);
                break;
        }
        helper.setText(R.id.tv_price, item.getMoney());
        helper.setText(R.id.tv_poundage, item.getPoundage());
        helper.setText(R.id.tv_time, item.getCreateDate());
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.tv_state, mContext.getResources().getString(R.string.apply_for));
                break;
            case 1:
                helper.setText(R.id.tv_state, mContext.getResources().getString(R.string.paymented));
                break;
            case 2:
                helper.setText(R.id.tv_state, mContext.getResources().getString(R.string.no_pass));
                break;
            case 3:
                helper.setText(R.id.tv_state, mContext.getResources().getString(R.string.has_refund));
                break;
        }
    }
}
