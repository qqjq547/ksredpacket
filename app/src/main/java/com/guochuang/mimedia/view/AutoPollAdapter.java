package com.guochuang.mimedia.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class AutoPollAdapter extends RecyclerView.Adapter<BaseViewHolder> {
 private final Context mContext;
 private final List<String> mData;
 private OnTextClickListener onTextClickListener;

 public interface OnTextClickListener{
    void onTextClick(int position);
 }

 public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
  this.onTextClickListener = onTextClickListener;
 }

 public AutoPollAdapter(Context context, List<String> list) {
  this.mContext = context;
  this.mData = list;
 }
 @Override
 public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);
  BaseViewHolder holder = new BaseViewHolder(view);
  return holder;
 }
 @Override
 public void onBindViewHolder(BaseViewHolder holder, int position) {
  final int realPos=position%mData.size();
  String data = mData.get(realPos);
  holder.setText(R.id.tv_content,data);
  if (onTextClickListener!=null){
   holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
     onTextClickListener.onTextClick(realPos);
    }
   });
  }

 }
 @Override
 public int getItemCount() {
  return Integer.MAX_VALUE;
 }
}
