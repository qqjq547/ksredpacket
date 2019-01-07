package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.GuideHelper;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeeNestActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_fav)
    ImageView ivFav;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_navigation)
    TextView tvNavigation;
    @BindView(R.id.lin_location)
    LinearLayout linLocation;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_weibo)
    TextView tvWeibo;
    @BindView(R.id.lin_info)
    LinearLayout linInfo;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_beenest_detail;
    }

    @Override
    public void initViewAndData() {

    }


    @OnClick({R.id.iv_back, R.id.iv_image, R.id.iv_fav, R.id.tv_url, R.id.tv_navigation, R.id.tv_call, R.id.tv_wechat, R.id.tv_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image:
                break;
            case R.id.iv_fav:
                break;
            case R.id.tv_url:
                IntentUtils.startOutWebActivity(this, "");
                break;
            case R.id.tv_navigation:
                GuideHelper.guide(this,Double.parseDouble(getPref().getLatitude()),Double.parseDouble(getPref().getLongitude()));
                break;
            case R.id.tv_call:
                CommonUtil.callPhone(this,"120");
                break;
            case R.id.tv_wechat:
                CommonUtil.copyMsg(this, tvWechat.getText().toString().trim());
                new DialogBuilder(this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.copy_wechat)
                        .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtils.startWechatApp(BeeNestActivity.this);
                            }
                        }).create().show();
                break;
            case R.id.tv_weibo:
                CommonUtil.copyMsg(this, tvWeibo.getText().toString().trim());
                new DialogBuilder(this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.copy_weibo)
                        .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtils.startWeiboApp(BeeNestActivity.this);
                            }
                        }).create().show();
                break;
        }
    }
}
