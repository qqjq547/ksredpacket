package com.guochuang.mimedia.ui.activity.city;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.ui.activity.user.MyCityActivity;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.CurrentRegion;
import com.guochuang.mimedia.mvp.presenter.CityPresenter;
import com.guochuang.mimedia.mvp.view.CityView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.adapter.BuyHallAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CityActivity extends MvpActivity<CityPresenter> implements CityView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_next_buy_time)
    TextView tvNextBuyTime;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.iv_image_1)
    ImageView ivImage1;
    @BindView(R.id.iv_image_2)
    ImageView ivImage2;
    @BindView(R.id.iv_image_3)
    ImageView ivImage3;
    @BindView(R.id.btn_buy_city_owner)
    TextView btnBuyCityOwner;
    @BindView(R.id.btn_buy_record)
    TextView btnBuyRecord;
    @BindView(R.id.lin_my_city)
    LinearLayout linMyCity;
    @BindView(R.id.tv_own_city_num)
    TextView tvOwnCityNum;
    @BindView(R.id.tv_city_total_benfit)
    TextView tvCityTotalBenfit;
    @BindView(R.id.lin_city_total_benfit)
    LinearLayout linCityTotalBenfit;
    @BindView(R.id.tv_redbag_benfit)
    TextView tvRedbagBenfit;
    @BindView(R.id.lin_redbag_benfit)
    LinearLayout linRedbagBenfit;
    @BindView(R.id.tv_buy_price_benfit)
    TextView tvBuyPriceBenfit;
    @BindView(R.id.lin_buy_price_benfit)
    LinearLayout linBuyPriceBenfit;
    @BindView(R.id.tv_buy_city_owner)
    TextView tvBuyCityOwner;
    @BindView(R.id.rv_buy_hall)
    RecyclerView rvBuyHall;
    @BindView(R.id.ll_current_city_all)
    LinearLayout llCurrentCityAll;
    @BindView(R.id.lin_city)
    LinearLayout linCity;
    @BindView(R.id.lin_record)
    LinearLayout linRecord;
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


    List<List<CurrentRegion.BiddingHallBean>> allArr = new ArrayList<>();
    List<CurrentRegion.BiddingHallBean> itemArr = new ArrayList<>();
    BuyHallAdapter adapter;
    int totalPage = 1;
    int curPage = 0;
    CurrentRegion currentRegion;
    BroadcastReceiver receiver;

    @Override
    protected CityPresenter createPresenter() {
        return new CityPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_city;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.city);
        tvText.setText(R.string.buy_hall);
        rvBuyHall.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvBuyHall.addItemDecoration(new VerticalDecoration(this, R.drawable.bg_city_divide));
        adapter = new BuyHallAdapter(itemArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(CityActivity.this, CityBidHallActivity.class));
            }
        });
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvBuyHall.setAdapter(adapter);
        mvpPresenter.getCurrentRegion(getPref().getLatitude(), getPref().getLongitude());
//        mvpPresenter.getCurrentRegion("22.527944","113.931690");
        receiver=new CityReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.ACTION_CHANGE_CITY);
        registerReceiver(receiver,filter);
    }

    @OnClick({R.id.iv_back,
            R.id.tv_text,
            R.id.btn_buy_city_owner,
            R.id.btn_buy_record,
            R.id.ll_current_city_all,
            R.id.lin_city,
            R.id.tv_buy_city_owner,
            R.id.iv_bug_city_owner,
            R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (currentRegion != null) {
                    startActivity(new Intent(this, CityBidHallActivity.class));
                }
                break;
            case R.id.btn_buy_city_owner:
                if (currentRegion != null) {
                    IntentUtils.startPurchaseActivity(this, Constant.TYPE_PURCHASE_REGION, currentRegion.getBiddingPrice(), currentRegion.getRegionId());
                }
                break;
            case R.id.lin_city:
                if (currentRegion != null) {
                    startActivity(new Intent(this, MyCityActivity.class));
                }
                break;
            case R.id.btn_buy_record:
                if (currentRegion != null) {
                    IntentUtils.startCityBidRecordActivity(this, currentRegion.getRegionId());
                }
                break;
            case R.id.ll_current_city_all:
                if (currentRegion != null) {
                    IntentUtils.startCityDetailActivity(this, currentRegion.getRegionId());
                }
                break;
            case R.id.tv_buy_city_owner:
                startActivity(new Intent(this, CityBidHallActivity.class));
                break;
            case R.id.iv_bug_city_owner:
                startActivity(new Intent(this, CityBidHallActivity.class));
                break;
            case R.id.tv_confirm:
                if (currentRegion != null) {
                    IntentUtils.startPurchaseActivity(this, Constant.TYPE_PURCHASE_REGION, currentRegion.getBiddingPrice(), currentRegion.getRegionId());
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void setData(CurrentRegion data) {
        if (data != null) {
            currentRegion = data;
            if (data.isDereliction()) {
                llCurrentCityAll.setVisibility(View.GONE);
                linCityNone.setVisibility(View.VISIBLE);
                tvTitle.setText(R.string.city_bid_hall_no_land);
                tvText.setText(R.string.buy_hall);
            } else {
                llCurrentCityAll.setVisibility(View.VISIBLE);
                linCityNone.setVisibility(View.GONE);
                tvTitle.setText(R.string.city);
                tvText.setText(R.string.buy_hall);
            }
            tvCityName.setText(data.getWhereRegion());
            tvBidPrice.setText(String.format(getString(R.string.format_qc), data.getBiddingPrice()));
            tvAddress.setText(data.getWhereRegion());
            if (data.isBidding()) {
                tvNextBuyTime.setVisibility(View.GONE);
                btnBuyCityOwner.setVisibility(View.VISIBLE);
            } else {
                if (TextUtils.isEmpty(data.getBiddingTime())){
                    tvNextBuyTime.setVisibility(View.GONE);
                }else {
                    tvNextBuyTime.setVisibility(View.VISIBLE);
                    tvNextBuyTime.setText(String.format(getString(R.string.format_next_buy_time), data.getBiddingTime()));
                }
                btnBuyCityOwner.setVisibility(View.GONE);
            }
            GlideImgManager.loadCircleImage(this, data.getAvatar(), ivAvatar);
            tvNickname.setText(data.getNickName());
            tvMoney.setText(String.format(getString(R.string.format_qc), data.getBiddingPrice()));
            tvSummary.setText(data.getContent());
            List<String> pictureArr = data.getPicture();
            if (pictureArr != null && pictureArr.size() > 0) {
                if (pictureArr.size() > 0) {
                    GlideImgManager.loadImage(this, data.getPicture().get(0), ivImage1);
                } else {
                    ivImage1.setVisibility(View.GONE);
                }
                if (pictureArr.size() > 1) {
                    GlideImgManager.loadImage(this, data.getPicture().get(1), ivImage2);
                } else {
                    ivImage2.setVisibility(View.GONE);
                }
                if (pictureArr.size() > 2) {
                    GlideImgManager.loadImage(this, data.getPicture().get(2), ivImage3);
                } else {
                    ivImage3.setVisibility(View.GONE);
                }
            } else {
                ivImage1.setVisibility(View.GONE);
                ivImage2.setVisibility(View.GONE);
                ivImage3.setVisibility(View.GONE);
            }
            if (data.getRegionStatisticsDto() != null) {
                String numStr = String.format(getString(R.string.format_current_own_city), data.getRegionStatisticsDto().getRegionNumber());
                SpannableString span = new SpannableString(numStr);
                int index = numStr.indexOf(String.valueOf(data.getRegionStatisticsDto().getRegionNumber()));
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), index, index + String.valueOf(data.getRegionStatisticsDto().getRegionNumber()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvOwnCityNum.setText(span);
                tvCityTotalBenfit.setText(data.getRegionStatisticsDto().getTotalIncome());
                tvRedbagBenfit.setText(data.getRegionStatisticsDto().getRedPacketIncome());
                tvBuyPriceBenfit.setText(data.getRegionStatisticsDto().getCompensate());
            }
            if (data != null && data.getBiddingHall().size() > 0) {
                allArr.clear();
                totalPage = (data.getBiddingHall().size() / 5) + (data.getBiddingHall().size() % 5 > 0 ? 1 : 0);
                for (int i = 0; i < totalPage; i++) {
                    if (i == totalPage - 1) {
                        allArr.add(data.getBiddingHall().subList(i * 5, data.getBiddingHall().size()));
                    } else {
                        allArr.add(data.getBiddingHall().subList(i * 5, (i + 1) * 5));
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            itemArr.clear();
            itemArr.addAll(allArr.get(curPage % totalPage));
            adapter.notifyDataSetChanged();
            if (allArr.size() > 1) {
                curPage++;
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.REQUEST_PURCHASE) {
            mvpPresenter.getCurrentRegion(getPref().getLatitude(), getPref().getLongitude());
        }
    }
    class CityReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mvpPresenter.getCurrentRegion(getPref().getLatitude(), getPref().getLongitude());
        }
    }
}
