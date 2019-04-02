package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class SendRedbagAdapter2 extends CommonRecyclerAdapter<RedbagRecord> {
    enum RedPacketType {
        random(Constant.RANDOM_STR),
        survey(Constant.SURVEY_STR),
        video(Constant.VIDEO_STR),
        lucky(Constant.LUCKY_STR),
        password(Constant.PASSWORD_STR);

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

       // String suffix = item.getMoney() + "元  " + item.getQuantity() + "个";
        String suffix = String.format(mContext.getString(R.string.money_ge_fomat),item.getMoney(),item.getQuantity());
        holder.setText(R.id.tv_red_packet,RedPacketType.valueOf(item.getRedPacketType()).getmRedPacketType() + suffix);

        holder.setText(R.id.tv_kou_lin, String.format(mContext.getString(R.string.password_fomat),item.getPassword()))
                .setViewVisibility(R.id.tv_kou_lin, TextUtils.isEmpty(item.getPassword()) ? View.GONE : View.VISIBLE);


        holder.setText(R.id.tv_goto_look_problem, Constant.RED_PACKET_TYPE_SURVEY.equals(item.getRedPacketType()) ? mContext.getString(R.string.look_survey_statistics) :Constant.RED_PACKET_TYPE_VIDEO.equals(item.getRedPacketType()) ? mContext.getString(R.string.look_problme_str) : "")
                .setViewVisibility(R.id.tv_goto_look_problem, Constant.RED_PACKET_TYPE_SURVEY.equals(item.getRedPacketType()) && !("0".equals(item.getSurveyId())) ? View.VISIBLE : Constant.RED_PACKET_TYPE_VIDEO.equals(item.getRedPacketType()) && !("0".equals(item.getSurveyId())) ? View.VISIBLE : View.GONE);

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
