package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.BenefitType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.view.flowtag.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class CityAreaAdapter<BenefitType> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<com.guochuang.mimedia.mvp.model.BenefitType> mDataList;

    public CityAreaAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public com.guochuang.mimedia.mvp.model.BenefitType getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_city_area, null);
        TextView textView = view.findViewById(R.id.tv_tag);
        com.guochuang.mimedia.mvp.model.BenefitType t = mDataList.get(position);
        textView.setText(t.getDistrict().get(position).getDistrictName());
        return view;
    }

    public void onlyAddAll(List<com.guochuang.mimedia.mvp.model.BenefitType> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<com.guochuang.mimedia.mvp.model.BenefitType> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return position % 2 == 0;
    }
}
