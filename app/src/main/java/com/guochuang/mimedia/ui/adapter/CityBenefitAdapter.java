package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.view.flowtag.FlowTagLayout;
import com.guochuang.mimedia.view.flowtag.OnTagClickListener;

import java.util.List;

public class CityBenefitAdapter extends BaseQuickAdapter<BenefitType,BaseViewHolder> {
    public interface OnSelectTagListener{
        void onSelectTag(String cityName,String districtName);
    }
    OnSelectTagListener onSelectTagListener;
    public CityBenefitAdapter(@Nullable List<BenefitType> data) {
        super(R.layout.item_city_group,data);
    }

    public void setOnSelectTagListener(OnSelectTagListener onSelectTagListener) {
        this.onSelectTagListener = onSelectTagListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BenefitType item) {
        helper.setText(R.id.tv_city,item.getCityName());
        FlowTagLayout flowTagLayout=helper.getView(R.id.ftl_area);
        CityAreaAdapter tagAdapter = new CityAreaAdapter<>(mContext);
        flowTagLayout.setAdapter(tagAdapter);
        flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                if (onSelectTagListener!=null)
                    onSelectTagListener.onSelectTag(item.getCityName(),item.getDistrict().get(position).getDistrictName());
            }
        });
        tagAdapter.clearAndAddAll(item.getDistrict());
    }
}
