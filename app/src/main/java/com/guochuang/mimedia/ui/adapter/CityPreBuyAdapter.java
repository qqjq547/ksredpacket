package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.MallRegionInfo;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class CityPreBuyAdapter extends BaseQuickAdapter<MallRegionInfo.RegionNodeListBean,BaseViewHolder> {
    public CityPreBuyAdapter(@Nullable List<MallRegionInfo.RegionNodeListBean> data) {
        super(R.layout.item_city_prebuy,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallRegionInfo.RegionNodeListBean item) {
      helper.setText(R.id.tv_name,String.valueOf(item.getNodeNumber()));
      if (item.getIsBuy()==0){
          helper.setImageResource(R.id.iv_image,R.drawable.bg_city_enable);
      }else {
          helper.setImageResource(R.id.iv_image,R.drawable.bg_city_disable);
      }
    }
}
