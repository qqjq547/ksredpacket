package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.CurrentRegion;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import java.util.List;

public class BuyHallAdapter extends BaseQuickAdapter<CurrentRegion.BiddingHallBean,BaseViewHolder> {

    public BuyHallAdapter(@Nullable List<CurrentRegion.BiddingHallBean> data) {
        super(R.layout.item_buy_hall,data);
}

    @Override
    protected void convert(BaseViewHolder helper, CurrentRegion.BiddingHallBean item) {
        GlideImgManager.loadCircleImage(mContext,item.getAvatar(),(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getProsit()+item.getNickName());
        helper.setText(R.id.tv_money,String.format(mContext.getString(R.string.format_qc),item.getPrice()));
        helper.setText(R.id.tv_buy_result,item.getGrats());
    }
}
