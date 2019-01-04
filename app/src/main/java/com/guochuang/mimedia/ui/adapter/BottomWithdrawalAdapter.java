package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class BottomWithdrawalAdapter extends BaseQuickAdapter<KsbTransfer.PayTypeListBean, BaseViewHolder> {

    private int checkItem;

    public int getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(int checkItem) {
        this.checkItem = checkItem;
    }

    public BottomWithdrawalAdapter(@Nullable List<KsbTransfer.PayTypeListBean> data) {
        super(R.layout.item_pay_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KsbTransfer.PayTypeListBean item) {
        switch (item.getPayType()) {
            case 2:
                helper.setImageResource(R.id.iv_header, R.drawable.ic_my_ksb_transfer_wx);
                helper.setText(R.id.tv_type, R.string.wxpay);
                helper.setText(R.id.tv_name, item.getName());
                break;
            case 3:
                helper.setImageResource(R.id.iv_header, R.drawable.ic_my_ksb_transfer_ali);
                helper.setText(R.id.tv_type, R.string.alipay);
                helper.setText(R.id.tv_name, item.getName());
                break;
            default:
                GlideImgManager.loadImage(mContext, item.getIcon(), (ImageView) helper.getView(R.id.iv_header));
                helper.setText(R.id.tv_type, item.getName());
                helper.setText(R.id.tv_name, item.getAccount());
                break;
        }

        if (getCheckItem() == helper.getAdapterPosition()) {
            helper.setImageResource(R.id.iv_check, R.drawable.ic_my_ksb_transfer_check);
            helper.getView(R.id.iv_check).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_check).setVisibility(View.GONE);
        }
    }
}
