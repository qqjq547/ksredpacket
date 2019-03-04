package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.PhoneEntity;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class SelectPhoneAdapter extends BaseAdapter {
    private Context mContext;
    List<PhoneEntity> mData;
    private View inflate;

    public SelectPhoneAdapter(Context context, List<PhoneEntity> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size() > 10 ? 10 : mData.size();
    }

    @Override
    public PhoneEntity getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflate = LayoutInflater.from(mContext).inflate(R.layout.item_seach_phone, null, false);
        TextView itmeTV = inflate.findViewById(R.id.tv_phone);
        itmeTV.setText(mData.get(position).getPhone());
        return inflate;
    }

    public float getPopoHight() {
        return inflate.getHeight();
    }


}