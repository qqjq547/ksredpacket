package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.redbag.LookSurevyActivity;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TempFragment extends MvpFragment {
    @BindView(R.id.tv_survery)
    TextView tvSurvery;
    @BindView(R.id.tv_amout)
    TextView tvAmout;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_scope)
    TextView tvScope;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.lin_word)
    LinearLayout linWord;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.lin_picture)
    LinearLayout linPicture;
    @BindView(R.id.iv_picture1)
    ImageView ivPicture1;
    @BindView(R.id.iv_picture2)
    ImageView ivPicture2;
    @BindView(R.id.iv_picture3)
    ImageView ivPicture3;

    RedbagTemp temp;
    Unbinder unbinder;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_temp;
    }

    @Override
    public void initViewAndData() {
        if (temp.getRedPacketType().equals(Constant.RED_PACKET_TYPE_VIDEO)
                ||temp.getRedPacketType().equals(Constant.RED_PACKET_TYPE_SURVEY)){
            if (temp.getSurveyId()>0){
                tvSurvery.setVisibility(View.VISIBLE);
            }
        }
        tvAmout.setText(String.format(getString(R.string.format_yuan), String.valueOf(temp.getMoney())));
        tvCount.setText(String.format(getString(R.string.format_num), temp.getQuantity()));
        tvScope.setText(getResources().getStringArray(R.array.redbag_scope)[temp.getAreaType()]);
        if (TextUtils.isEmpty(temp.getDistrictName())) {
            tvLocation.setText(R.string.location_item_hint);
        } else {
            tvLocation.setText(temp.getDistrictName());
        }
        if (temp.getRedPacketType().equals(Constant.RED_PACKET_TYPE_PASSWORD)) {
            linWord.setVisibility(View.VISIBLE);
        } else {
            linWord.setVisibility(View.GONE);
        }
        if (temp.getRedPacketType().equals(Constant.RED_PACKET_TYPE_VIDEO)){
            ivCover.setVisibility(View.VISIBLE);
            linPicture.setVisibility(View.GONE);
            GlideImgManager.loadImage(getContext(),temp.getCoverUrl(),ivCover);
        }
        if (TextUtils.isEmpty(temp.getPicture())) {
            linPicture.setVisibility(View.GONE);
        } else {
            linPicture.setVisibility(View.VISIBLE);
            String[] picArr = temp.getPicture().split(",");
            if (picArr.length > 0) {
                GlideImgManager.loadImage(getActivity(), picArr[0], ivPicture1);
            } else {
                ivPicture1.setVisibility(View.INVISIBLE);
            }
            if (picArr.length > 1) {
                GlideImgManager.loadImage(getActivity(), picArr[1], ivPicture2);
            } else {
                ivPicture2.setVisibility(View.INVISIBLE);
            }
            if (picArr.length > 2) {
                GlideImgManager.loadImage(getActivity(), picArr[2], ivPicture3);
            } else {
                ivPicture3.setVisibility(View.INVISIBLE);
            }

        }

    }

    public void setTemp(RedbagTemp temp) {
        this.temp = temp;
    }


    @OnClick({R.id.tv_survery,R.id.iv_cover})
    public void onViewClicked(View view) {
       switch (view.getId()){
           case R.id.tv_survery:
               startActivity(new Intent(getActivity(),LookSurevyActivity.class)
                       .putExtra(Constant.LOOK_PROBLEM_RED_PACKET_ID,String.valueOf(temp.getSurveyId()))
                       .putExtra(Constant.RED_PACKET_ID,temp.getRedPacketUuid()));
               break;
           case R.id.iv_cover:
               IntentUtils.startVideoPreviewActivity(getActivity(),temp.getVideoUrl());
               break;
       }
    }
}
