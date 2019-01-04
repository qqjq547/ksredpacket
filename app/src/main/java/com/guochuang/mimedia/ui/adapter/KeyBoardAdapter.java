package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.Map;

public class KeyBoardAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String, String>> valueList;

    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList) {
        this.mContext = mContext;
        this.valueList = valueList;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_virtual_keyboard_item, null);
            viewHolder = new ViewHolder();
            viewHolder.btnKey = convertView.findViewById(R.id.btn_keys);
            viewHolder.imgDelete = convertView.findViewById(R.id.imgDelete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 9) {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
        } else if (position == 11) {
            viewHolder.btnKey.setBackgroundResource(R.drawable.ic_keyboard_delete_img);
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setVisibility(View.INVISIBLE);

        } else {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);

            viewHolder.btnKey.setText(valueList.get(position).get("name"));
            viewHolder.btnKey.setBackgroundColor(mContext.getResources().getColor(R.color.bg_white));
        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView btnKey;
        public RelativeLayout imgDelete;
    }
}
