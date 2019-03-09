package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dmcbig.mediapicker.utils.ScreenUtils;
import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.ToastUtil;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class ProblemDialogInAdapter extends CommonRecyclerAdapter<ProblemBean.ItemBean> {
    private String mRedPacketType;

    public ProblemDialogInAdapter(Context context, List<ProblemBean.ItemBean> problemBeans, MultiTypeSupport<ProblemBean.ItemBean> multiTypeSupport, String redPacketType) {
        super(context, problemBeans, multiTypeSupport);
        mRedPacketType = redPacketType;
    }

    @Override
    public int getItemCount() {
        if (!mData.isEmpty()) {

            ProblemBean.ItemBean itemBean = mData.get(0);
            if (itemBean.getProblemType() == 2) {
                if (Constant.RED_PACKET_TYPE_QUESTION.equals(mRedPacketType)) {
                    return 0;
                }


                return 1;
            }
        }


        return super.getItemCount();
    }

    @Override
    protected void convert(ViewHolder holder, final int position, final ProblemBean.ItemBean item) {


        final int problemType = item.getProblemType();

        switch (problemType) {
            case 0:
            case 1:
                if (Constant.RED_PACKET_TYPE_QUESTION.equals(mRedPacketType)) {

                    holder.setViewVisibility(R.id.rl_select_root, View.GONE);
                }


                //单项
                holder.setViewVisibility(R.id.tv_anser, View.GONE)
                        .setViewVisibility(R.id.iv_select_ok, View.GONE);


                holder.setText(R.id.tv_name, item.getItemname())

                        .setViewVisibility(item.isIsanswer() ? R.id.iv_select_ok : R.id.tv_anser, View.VISIBLE);

                holder.getView(R.id.iv_select_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击的是勾
                        item.setIsanswer(false);
                        notifyDataSetChanged();
                    }
                });


                final EditText etContainer = holder.getView(R.id.et_contant);

                etContainer.setTag(position);
                if (position == (int) (etContainer.getTag())) {
                    holder.setText(R.id.et_contant, item.getItemcontent());
                }
                etContainer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if ((int) (etContainer.getTag()) == position) {
                            String containe = s.toString().trim();
                            item.setItemcontent(containe);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                holder.getView(R.id.tv_anser).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击的是答案
                        String containe = etContainer.getText().toString().trim();
                        item.setItemcontent(containe);
                        if (!TextUtils.isEmpty(containe)) {
                            if (problemType == 0) {
                                singeSelect(item);
                            } else {
                                item.setIsanswer(true);
                                notifyDataSetChanged();
                            }

                        }

                    }
                });


                if (mData.size() - 1 == position) {
                    LinearLayout view = holder.getView(R.id.item_root);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.bottomMargin = ScreenUtils.dp2px(mContext, 30);
                    view.setLayoutParams(layoutParams);
                } else {
                    LinearLayout view = holder.getView(R.id.item_root);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.bottomMargin = ScreenUtils.dp2px(mContext, 0);
                    view.setLayoutParams(layoutParams);
                }
                break;
            case 2:


                holder.setViewVisibility(R.id.ll_fill_container, View.GONE);
                if (position == 0) {
                    holder.setViewVisibility(R.id.ll_fill_container, View.VISIBLE);
                }

                if (Constant.RED_PACKET_TYPE_QUESTION.equals(mRedPacketType)) {

                    holder.setViewVisibility(R.id.ll_fill_container, View.GONE);
                }


                holder.setText(R.id.et_content, item.getItemcontent());

                final EditText et_inputanswer = holder.getView(R.id.et_content);
                et_inputanswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String containe = s.toString().trim();
                        item.setItemcontent(containe);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }


    }

    /**
     * 单选
     *
     * @param item
     */
    private void singeSelect(ProblemBean.ItemBean item) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getItemname().equals(item.getItemname())) {
                mData.get(i).setIsanswer(true);
            } else {
                mData.get(i).setIsanswer(false);
            }

        }

        notifyDataSetChanged();
    }


}
