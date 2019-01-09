package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.presenter.BeeNestPresenter;
import com.guochuang.mimedia.mvp.view.BeeNestView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.GuideHelper;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.ToastUtil;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.ui.adapter.PictureVerticalAdapter;
import com.guochuang.mimedia.ui.dialog.BeeNestDialog;
import com.guochuang.mimedia.ui.dialog.ReportDialog;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeeNestActivity extends MvpActivity<BeeNestPresenter> implements BeeNestView {
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
    PictureVerticalAdapter adapter;
    List<String> pictureArr=new ArrayList<>();

    @Override
    protected BeeNestPresenter createPresenter() {
        return new BeeNestPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_beenest_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText("xxxxx");
        ivImage.setImageResource(R.drawable.ic_more);
        pictureArr.add("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2774071914,778442818&fm=173&app=25&f=JPEG?w=640&h=400&s=FDB83BD10E76448043A0F550030040F3");
        pictureArr.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3253348826,163743007&fm=173&app=25&f=JPEG?w=405&h=300&s=3DA5CB1540027F41048008CD0300E0A1");
        pictureArr.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2669277608,2915862647&fm=173&app=25&f=JPEG?w=400&h=427&s=6E230FC306CA50F49994BCF803001011");
        adapter=new PictureVerticalAdapter(pictureArr);
        rvPicture.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvPicture.addItemDecoration(new VerticalDecoration(this,R.drawable.bg_city_divide));
        rvPicture.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back, R.id.iv_image, R.id.iv_fav, R.id.tv_url, R.id.tv_navigation, R.id.tv_call, R.id.tv_wechat, R.id.tv_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image:
                new BeeNestDialog(this).setOnItemClikListener(new BeeNestDialog.OnItemClikListener() {
                    @Override
                    public void onBidThis() {
                       startActivity(new Intent(BeeNestActivity.this,BidBrandActivity.class));
                    }

                    @Override
                    public void onBidOther() {

                    }

                    @Override
                    public void onReport() {
                        showLoadingDialog(null);
                        mvpPresenter.dictionaryGet(Constant.TYPE_REPORT_INFORMATION);
                    }
                }).show();
                break;
            case R.id.iv_fav:
//                if (detail == null)
//                    return;
//                if (detail.getIsFavorited() == 0) {
//                    mvpPresenter.favoriteAdd(articleUuid);
//                } else {
//                    mvpPresenter.favoriteDetele(articleUuid);
//                }
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

    @Override
    public void setData(InfoDetail data) {

    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void addFavorite(Integer data) {
        ivFav.setSelected(true);
//        detail.setIsFavorited(1);
        ToastUtil.showSuccess(this, getString(R.string.collect_success), R.drawable.ic_done);
    }

    @Override
    public void deleteFavorite(Boolean data) {
        ivFav.setSelected(false);
//        detail.setIsFavorited(0);
        ToastUtil.showSuccess(this, getString(R.string.cancel_collect), R.drawable.ic_done);
    }

    @Override
    public void addReport(Integer data) {
        closeLoadingDialog();
        showShortToast(R.string.report_success);
    }

    @Override
    public void getReportItem(List<DictionaryType> data) {
        closeLoadingDialog();
        if (data.size() > 0) {
            ReportDialog reportDialog = new ReportDialog(this, data);
            reportDialog.setOnResultListener(new ReportDialog.OnResultListener() {
                @Override
                public void onReport(List<DictionaryType> items, String content) {
                    showLoadingDialog(null);
                    List<String> codeArr = new ArrayList<>();
                    for (DictionaryType report : items) {
                        codeArr.add(report.getName());
                    }
                    String type = TextUtils.join(",", codeArr);
//                    mvpPresenter.reportAdd(articleUuid, content, type);
                }
            });
            reportDialog.show();
        }
    }

    @Override
    public void shareAdd(Integer data) {

    }

}
