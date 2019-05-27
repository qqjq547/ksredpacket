package com.guochuang.mimedia.ui.activity.city;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.tools.UrlConfig;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.RegionDetail;
import com.guochuang.mimedia.mvp.presenter.CityDetailPresenter;
import com.guochuang.mimedia.mvp.view.CityDetailView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.ui.dialog.CityBenefitExplainDialog;
import com.guochuang.mimedia.view.GridItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CityDetailActivity extends MvpActivity<CityDetailPresenter> implements CityDetailView {
    @BindView(R.id.lin_root)
    LinearLayout linRoot;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_next_buy_time)
    TextView tvNextBuyTime;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_buy_city_owner)
    TextView tvBuyCityOwner;
    @BindView(R.id.tv_buy_record)
    TextView tvBuyRecord;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_special_price)
    TextView tvSpecialPrice;
    @BindView(R.id.tv_benefit_explain)
    TextView tvBenefitExplain;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @BindView(R.id.lin_city_none)
    LinearLayout linCityNone;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_bid_price)
    TextView tvBidPrice;
    @BindView(R.id.tv_bid_share_hint)
    TextView tvBidShareHint;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.lin_next_buy_time)
    LinearLayout linNextBuyTime;

    PictureAdapter adapter;
    ArrayList<String> pictureArr=new ArrayList<>();
    long regionId;
    RegionDetail detail;
    @Override
    protected CityDetailPresenter createPresenter() {
        return new CityDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_city_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.city);
        setStatusbar(R.color.bg_red,false);
        regionId=getIntent().getLongExtra(Constant.REGIONID,0);
        rvImage.setLayoutManager(new GridLayoutManager(this,3));
        rvImage.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,5),true));
        adapter=new PictureAdapter(pictureArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startImagePreviewActivity(CityDetailActivity.this, position, pictureArr);
            }
        });
        rvImage.setAdapter(adapter);
        mvpPresenter.getRegionDetail(regionId);
    }

    @OnClick({R.id.iv_back,R.id.tv_text, R.id.tv_confirm, R.id.tv_buy_city_owner,R.id.tv_buy_record, R.id.tv_special_price, R.id.tv_benefit_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (detail!=null){
                    if (detail.isDereliction()){
                        IntentUtils.startWebActivity(this,getString(R.string.rule), UrlConfig.getHtmlUrl(UrlConfig.URL_REGION_RULE));
                    }
                }
                break;
            case R.id.tv_confirm:
                if (detail!=null) {
                    IntentUtils.startPurchaseActivity(this,Constant.TYPE_PURCHASE_REGION,detail.getBiddingPrice(),detail.getRegionId());
                }
                break;
            case R.id.tv_buy_city_owner:
                if(detail!=null){
                    IntentUtils.startPurchaseActivity(this,Constant.TYPE_PURCHASE_REGION,detail.getBiddingPrice(),detail.getRegionId());
                }
                break;
            case R.id.tv_buy_record:
                if(detail!=null){
                    IntentUtils.startCityBidRecordActivity(this,detail.getRegionId());
                }
                break;
            case R.id.tv_special_price:
                IntentUtils.startWebActivity(this,detail.getUrlName(),detail.getUrl());
                break;
            case R.id.tv_benefit_explain:
                if (detail!=null) {
                    new CityBenefitExplainDialog(this, detail.getExplain()).show();
                }
                break;
        }
    }

    @Override
    public void setData(RegionDetail data) {
        if (data!=null){
            detail=data;
            if (data.isDereliction()){
                tvTitle.setText(R.string.city_bid_hall_no_land);
                tvText.setText(R.string.rule);
                ivBack.setImageResource(R.drawable.ic_back);
                tvTitle.setTextColor(getResources().getColor(R.color.text_black));
                tvText.setTextColor(getResources().getColor(R.color.text_black));
                setStatusbar(R.color.bg_white,true);
                linRoot.setBackgroundColor(getResources().getColor(R.color.bg_white));
                flContent.setVisibility(View.GONE);
                linCityNone.setVisibility(View.VISIBLE);
                tvCityName.setText(data.getWhereRegion());
                tvBidPrice.setText(String.format(getString(R.string.format_qc),data.getBiddingPrice()));
                return;
            }
            tvTitle.setText(R.string.city);
            tvText.setText(null);
            ivBack.setImageResource(R.drawable.ic_back_white);
            tvTitle.setTextColor(getResources().getColor(R.color.text_white));
            tvText.setTextColor(getResources().getColor(R.color.text_white));
            setStatusbar(R.color.bg_red,false);
            linRoot.setBackgroundColor(getResources().getColor(R.color.bg_red));
            flContent.setVisibility(View.VISIBLE);
            linCityNone.setVisibility(View.GONE);
            GlideImgManager.loadCircleImage(this,data.getAvatar(),ivAvatar);
            tvArea.setText(data.getWhereRegion());
            tvNextBuyTime.setText(data.getBiddingTime());
            tvNickname.setText(data.getNickName());
            tvMoney.setText(String.format(getString(R.string.format_qc),data.getBiddingPrice()));
            tvSummary.setText(data.getContent());
            if (data.isBidding()){
                linNextBuyTime.setVisibility(View.INVISIBLE);
                tvBuyCityOwner.setVisibility(View.VISIBLE);
            }else {
                if (TextUtils.isEmpty(data.getBiddingTime())){
                    linNextBuyTime.setVisibility(View.INVISIBLE);
                }else {
                    linNextBuyTime.setVisibility(View.VISIBLE);
                }
                tvBuyCityOwner.setVisibility(View.GONE);
            }
            if (data.getPicture()!=null&&data.getPicture().size()>0){
                pictureArr.addAll(data.getPicture());
                adapter.notifyDataSetChanged();
            }
            if (!TextUtils.isEmpty(data.getUrlName())&&!TextUtils.isEmpty(data.getUrl())){
                tvSpecialPrice.setVisibility(View.VISIBLE);
                tvSpecialPrice.setText(data.getUrlName());
            }else {
                tvSpecialPrice.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
       showShortToast(msg);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==Constant.REQUEST_PURCHASE){
            mvpPresenter.getRegionDetail(regionId);
        }
    }
}
