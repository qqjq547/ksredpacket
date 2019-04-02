package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class SendRedbagAdapter extends BaseQuickAdapter<RedbagRecord,BaseViewHolder> {

    String[] nameArr;
    public SendRedbagAdapter(Context context,List<RedbagRecord> redbagRecords) {
        super(R.layout.item_sendredbag_layout,redbagRecords);
        nameArr= context.getResources().getStringArray(R.array.type_redpacket);
    }
    public String getTypeName(String type){
        switch (type){
            case Constant.RED_PACKET_TYPE_RANDOM:
                return nameArr[0];
            case Constant.RED_PACKET_TYPE_PASSWORD:
                return nameArr[1];
            case Constant.RED_PACKET_TYPE_LUCKY:
                return nameArr[2];
            case Constant.RED_PACKET_TYPE_VIDEO:
                return nameArr[3];
            case Constant.RED_PACKET_TYPE_SURVEY:
                return nameArr[4];
        }
        return null;
    }
    @Override
    protected void convert(BaseViewHolder helper, RedbagRecord item) {
        helper.setGone(R.id.tv_kou_lin,false);
        helper.setGone(R.id.tv_goto_look_problem,false);
        helper.setGone(R.id.tv_goto_look_info,false);
        helper.setGone(R.id.iv_prictu,false);

        String suffix = item.getMoney()+ String.format(mContext.getString(R.string.money_ge_fomat),item.getQuantity());
        helper.setText(R.id.tv_red_packet,getTypeName(item.getRedPacketType()) + suffix);

        helper.setText(R.id.tv_kou_lin, String.format(mContext.getString(R.string.password_fomat),item.getPassword()));
        helper.setGone(R.id.tv_kou_lin, !TextUtils.isEmpty(item.getPassword()) );

        switch (item.getRedPacketType()){
            case Constant.RED_PACKET_TYPE_SURVEY:
                helper.setText(R.id.tv_goto_look_problem,R.string.look_survey_statistics);
                helper.setGone(R.id.tv_goto_look_problem,item.getSurveyId()>0);
                break;
            case Constant.RED_PACKET_TYPE_VIDEO:
                helper.setText(R.id.tv_goto_look_problem,R.string.look_problme_str);
                helper.setGone(R.id.tv_goto_look_problem,item.getSurveyId()>0);
                helper.setGone(R.id.iv_prictu,true);
                GlideImgManager.loadCornerImage(mContext, item.getCoverUrl(), (ImageView) helper.getView(R.id.iv_prictu), 8, true);
                break;
            default:
                helper.setGone(R.id.tv_goto_look_problem,false);
                break;
        }
        if (item.getPictureList() != null&&item.getPictureList().size()>0) {
            helper.setGone(R.id.tv_goto_look_info, true);
            helper.setGone(R.id.iv_prictu,true);
            GlideImgManager.loadCornerImage(mContext, item.getPictureList().get(0).getPicture(), (ImageView) helper.getView(R.id.iv_prictu), 8, true);
        } else {
            helper.setGone(R.id.tv_goto_look_info, false);
        }

        if (!TextUtils.isEmpty(item.getContent())) {
            helper.setGone(R.id.tv_content_title,true);
            helper.setText(R.id.tv_content_title,item.getContent());
        } else {
            helper.setGone(R.id.tv_content_title,false);
        }

        helper.setText(R.id.tv_look_number, String.valueOf(item.getBrowserNumber()));
        helper.setText(R.id.tv_msg, String.valueOf(item.getCommentNumber()));
        helper.setText(R.id.tv_zan, String.valueOf(item.getPraiseNumber()));
        helper.setText(R.id.tv_collection, String.valueOf(item.getFavoriteNumber()));

        helper.addOnClickListener(R.id.tv_goto_look_problem);

    }

}
