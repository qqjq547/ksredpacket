package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.GeoCode;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class PoiInfoAdapter extends BaseQuickAdapter<GeoCode.PoisBean,BaseViewHolder> {

    public PoiInfoAdapter(@Nullable List<GeoCode.PoisBean> data) {
        super(R.layout.item_poi,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GeoCode.PoisBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_content,item.getAddr());
    }
}
