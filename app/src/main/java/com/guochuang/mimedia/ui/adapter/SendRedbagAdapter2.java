package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class SendRedbagAdapter2 extends CommonRecyclerAdapter<RedbagRecord> {
    enum RedPacketType {
        random("随机红包: "),
        survey("问卷红包: "),
        video("视频红包: "),
        lucky("幸运红包: "),
        password("口令红包: "),;

        String mRedPacketType;

        RedPacketType(String type) {
            mRedPacketType = type;
        }

        public String getmRedPacketType() {
            return mRedPacketType;
        }
    }


    public SendRedbagAdapter2(Context context, List<RedbagRecord> redbagRecords, int itemlayout) {
        super(context, redbagRecords, itemlayout);
    }

    @Override
    protected void convert(ViewHolder holder, final int position, RedbagRecord item) {

        holder.setViewVisibility(R.id.tv_kou_lin, View.GONE)
                .setViewVisibility(R.id.tv_goto_look_problem, View.GONE)
                .setViewVisibility(R.id.tv_goto_look_info, View.GONE)
                .setViewVisibility(R.id.iv_prictu, View.GONE);

        String suffix = item.getMoney() + "元  " + item.getQuantity() + "个";

        holder.setText(R.id.tv_red_packet,
                "random".equals(item.getRedPacketType()) ? RedPacketType.random.getmRedPacketType() + suffix
                        : "survey".equals(item.getRedPacketType()) ? RedPacketType.survey.getmRedPacketType() + suffix
                        : "video".equals(item.getRedPacketType()) ? RedPacketType.video.getmRedPacketType() + suffix
                        : "password".equals(item.getRedPacketType()) ? RedPacketType.password.getmRedPacketType() + suffix
                        : RedPacketType.lucky.getmRedPacketType() + suffix);


        holder.setText(R.id.tv_kou_lin, "口令: " + item.getPassword())
                .setViewVisibility(R.id.tv_kou_lin, TextUtils.isEmpty(item.getPassword()) ? View.GONE : View.VISIBLE);


        holder.setText(R.id.tv_goto_look_problem, "survey".equals(item.getRedPacketType()) ? "查看问卷统计》" : "video".equals(item.getRedPacketType()) ? "查看所提问题" : "")
                .setViewVisibility(R.id.tv_goto_look_problem, "survey".equals(item.getRedPacketType()) && !("0".equals(item.getSurveyId())) ? View.VISIBLE : "video".equals(item.getRedPacketType()) && !("0".equals(item.getSurveyId())) ? View.VISIBLE : View.GONE);

        if (item.getPictureList() != null) {
            if (item.getPictureList().size() <= 0 && TextUtils.isEmpty(item.getContent())) {
                holder.setViewVisibility(R.id.tv_goto_look_info, View.VISIBLE);
            }
        } else {
            if (TextUtils.isEmpty(item.getContent())) {
                holder.setViewVisibility(R.id.tv_goto_look_info, View.VISIBLE);
            }
        }


        if (!TextUtils.isEmpty(item.getContent())) {
            holder.setViewVisibility(R.id.tv_content_title, View.VISIBLE).setText(R.id.tv_content_title, item.getContent());
        } else {
            holder.setViewVisibility(R.id.tv_content_title, View.GONE);
        }


        if (item.getPictureList() != null && item.getPictureList().size() > 0) {
            holder.setViewVisibility(R.id.iv_prictu, View.VISIBLE);
            GlideImgManager.loadCornerImage(mContext, item.getPictureList().get(0).getPicture(), (ImageView) holder.getView(R.id.iv_prictu), 8, true);
        }


        holder.setText(R.id.tv_look_number, String.valueOf(item.getBrowserNumber()))
                .setText(R.id.tv_msg, String.valueOf(item.getCommentNumber()))
                .setText(R.id.tv_zan, String.valueOf(item.getPraiseNumber()))
                .setText(R.id.tv_collection, String.valueOf(item.getFavoriteNumber()));


        //查看所提问题
        holder.getView(R.id.tv_goto_look_problem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItmeChildrenClick != null) {
                    mOnItmeChildrenClick.itmeChildrenClick(R.id.tv_goto_look_problem, position);
                }

            }
        });

    }

    /**
     * 设置监听
     */

    public interface OnItmeChildrenClick {
        void itmeChildrenClick(int viewId, int position);

    }

    OnItmeChildrenClick mOnItmeChildrenClick;

    public void setOnItmeChildren(OnItmeChildrenClick listener) {
        mOnItmeChildrenClick = listener;

    }


}
