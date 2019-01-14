package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.calendar.CaledarAdapter;
import com.guochuang.mimedia.tools.calendar.CalendarBean;
import com.guochuang.mimedia.tools.calendar.CalendarDateView;
import com.guochuang.mimedia.tools.calendar.CalendarFactory;
import com.guochuang.mimedia.tools.calendar.CalendarView;
import com.sz.gcyh.KSHongBao.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BidBrandActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.iv_last)
    ImageView ivLast;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.cdv_month)
    CalendarDateView cdvMonth;
    SimpleDateFormat dateFormat;
    List<String> auctionArr = new ArrayList<>();
    List<String> hotArr = new ArrayList<>();
    @BindView(R.id.tv_buy_time)
    TextView tvBuyTime;
    @BindView(R.id.tv_bid_record)
    TextView tvBidRecord;
    @BindView(R.id.tv_current_price)
    TextView tvCurrentPrice;
    @BindView(R.id.tv_bid_price)
    TextView tvBidPrice;
    @BindView(R.id.tv_my_ksb)
    TextView tvMyKsb;
    @BindView(R.id.tv_equal_ksb)
    TextView tvEqualKsb;
    @BindView(R.id.cb_obey_rule)
    CheckBox cbObeyRule;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.lin_edit)
    LinearLayout linEdit;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_next_bid_time)
    TextView tvNextBidTime;
    @BindView(R.id.lin_result)
    LinearLayout linResult;
    @BindView(R.id.lin_ensure)
    LinearLayout linRnsure;
    @BindView(R.id.btn_buy)
    Button btnBuy;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bid_brand;
    }

    @Override
    public void initViewAndData() {
        tvText.setText(R.string.history_put);
        dateFormat = new SimpleDateFormat(Constant.FORMAT_MONTH);
        tvMonth.setText(dateFormat.format(new Date()));
        initMarkData();
        cdvMonth.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_brand_date, null);
                }
                TextView tvDate = convertView.findViewById(R.id.tv_date);
                ImageView ivAuction = convertView.findViewById(R.id.iv_auction);
                ImageView ivHot = convertView.findViewById(R.id.iv_hot);
                tvDate.setText(String.valueOf(bean.day));
                String dateStr = CommonUtil.dateToString(CalendarFactory.getCalendarByBean(bean).getTime(), Constant.FORMAT_DATE_SIMPLE);
                if (auctionArr.contains(dateStr)) {
                    ivAuction.setVisibility(View.VISIBLE);
                } else {
                    ivAuction.setVisibility(View.GONE);
                }
                if (hotArr.contains(dateStr)) {
                    ivHot.setVisibility(View.VISIBLE);
                    tvDate.setBackgroundResource(R.drawable.bg_date_white);
                } else {
                    ivHot.setVisibility(View.GONE);
                    tvDate.setBackgroundResource(R.drawable.bg_date_gray);
                }
                if (bean.mothFlag != 0) {
                    tvDate.setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    tvDate.setTextColor(getResources().getColor(R.color.text_black));
                }
                return convertView;
            }
        });

        cdvMonth.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                String dateStr = CommonUtil.dateToString(CalendarFactory.getCalendarByBean(bean).getTime(), Constant.FORMAT_MONTH);
                tvMonth.setText(dateStr);
            }
        });
    }

    private void initMarkData() {
        auctionArr.add("2019-01-01");
        auctionArr.add("2019-01-02");
        auctionArr.add("2019-01-03");
        auctionArr.add("2019-01-04");
        hotArr.add("2019-01-05");
        hotArr.add("2019-01-06");
        hotArr.add("2019-01-07");
        hotArr.add("2019-01-08");
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.iv_last, R.id.iv_next, R.id.tv_bid_record, R.id.tv_rule, R.id.btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, HistoryPutActivity.class));
                break;
            case R.id.iv_last:
                cdvMonth.setCurrentItem(cdvMonth.getCurrentItem() - 1);
                break;
            case R.id.iv_next:
                cdvMonth.setCurrentItem(cdvMonth.getCurrentItem() + 1);
                break;
            case R.id.tv_bid_record:
                startActivity(new Intent(this, BidHistoryActivity.class));
                break;
            case R.id.tv_rule:
                IntentUtils.startWebActivity(this,null,Constant.URL_FENGCHAO_JINGGOU);
                break;
            case R.id.btn_buy:
                break;
        }
    }

}
