package com.guochuang.mimedia.ui.activity.beenest;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NestTimeInfo;
import com.guochuang.mimedia.mvp.presenter.BidBrandPresenter;
import com.guochuang.mimedia.mvp.view.BidBrandView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.calendar.CaledarAdapter;
import com.guochuang.mimedia.tools.calendar.CalendarBean;
import com.guochuang.mimedia.tools.calendar.CalendarDateView;
import com.guochuang.mimedia.tools.calendar.CalendarFactory;
import com.guochuang.mimedia.tools.calendar.CalendarView;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BidBrandActivity extends MvpActivity<BidBrandPresenter> implements BidBrandView {
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
    @BindView(R.id.tv_buy_time)
    TextView tvBuyTime;
    @BindView(R.id.tv_bid_record)
    TextView tvBidRecord;
    @BindView(R.id.tv_current_price)
    TextView tvCurrentPrice;
    @BindView(R.id.et_bid_price)
    EditText etBidPrice;
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

    SimpleDateFormat dateFormat;
    List<String> auctionArr = new ArrayList<>();
    List<String> hotArr = new ArrayList<>();
    long nestInfoId;
    long nestLocationId;
    long nestTimeId;
    NestTimeInfo timeInfo;
    double rate;

    CaledarAdapter caledarAdapter;

    @Override
    protected BidBrandPresenter createPresenter() {
        return new BidBrandPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bid_brand;
    }

    @Override
    public void initViewAndData() {
        tvText.setText(R.string.history_put);
        nestLocationId = getIntent().getLongExtra(Constant.NESTLOCATIONID, 0);
        dateFormat = new SimpleDateFormat(Constant.FORMAT_MONTH);
        tvMonth.setText(dateFormat.format(new Date()));
        initAdapter();
        cdvMonth.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                String dateStr = CommonUtil.dateToString(CalendarFactory.getCalendarByBean(bean).getTime(), Constant.FORMAT_MONTH);
                tvMonth.setText(dateStr);
            }
        });
        etBidPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = etBidPrice.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    tvEqualKsb.setText(null);
                    return;
                }
                if (timeInfo == null) {
                    return;
                }
                if (Double.parseDouble(text) > timeInfo.getMaxPrice()) {
                    etBidPrice.setText(String.valueOf(timeInfo.getMaxPrice()));
                }
                tvEqualKsb.setText(String.valueOf(DoubleUtil.mul(Double.parseDouble(text), rate)));
            }
        });
        mvpPresenter.setNestTimeInfo(nestLocationId);
    }
   private void initAdapter(){
       caledarAdapter=new CaledarAdapter() {
           @Override
           public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
               LogUtil.d("getView");
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
       };
       cdvMonth.setAdapter(caledarAdapter);
   }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.iv_last, R.id.iv_next, R.id.tv_bid_record, R.id.tv_rule, R.id.btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, HistoryPutActivity.class).putExtra(Constant.NESTLOCATIONID,nestLocationId));
                break;
            case R.id.iv_last:
                cdvMonth.setCurrentItem(cdvMonth.getCurrentItem() - 1);
                break;
            case R.id.iv_next:
                cdvMonth.setCurrentItem(cdvMonth.getCurrentItem() + 1);
                break;
            case R.id.tv_bid_record:
                startActivity(new Intent(this, BidRecordActivity.class).putExtra(Constant.NESTTIMEID, timeInfo.getCurrent().getNestTimeInfoId()));
                break;
            case R.id.tv_rule:
                IntentUtils.startWebActivity(this, null, Constant.URL_FENGCHAO_JINGGOU);
                break;
            case R.id.btn_buy:
                if (!cbObeyRule.isChecked()) {
                    showShortToast(R.string.pls_agree_bid_agreement);
                    return;
                }
                String bidPrice = etBidPrice.getText().toString();
                if (TextUtils.isEmpty(bidPrice)) {
                    showShortToast(R.string.buy_price_not_empty);
                } else {
                    int price = Integer.parseInt(bidPrice);
                    if (price <= timeInfo.getNext().getPrice()) {
                        showShortToast(R.string.buy_price_limit);
                    } else {
                        IntentUtils.startPurchaseActivity(this, Constant.TYPE_PURCHASE_NESTAD, timeInfo.getNext().getNestTimeInfoId(), bidPrice);
                    }
                }
                break;
        }
    }

    @Override
    public void setTimeInfo(NestTimeInfo data) {
        closeLoadingDialog();
        if (data != null) {
            timeInfo = data;
            rate = Double.parseDouble(data.getRate());
            NestTimeInfo.CurrentBean currentBean = data.getCurrent();
            NestTimeInfo.NextBean nestBean = data.getNext();
            Calendar startCal = Calendar.getInstance();
            if (currentBean != null) {
                startCal.setTime(CommonUtil.stringToDate(currentBean.getStartTime(), Constant.FORMAT_DATE_SIMPLE));
                for (int i = 0; i < currentBean.getDay(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startCal.getTime());
                    cal.add(Calendar.DATE, i);
                    auctionArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
            }
            if (nestBean != null) {
                startCal.setTime(CommonUtil.stringToDate(nestBean.getStartTime(), Constant.FORMAT_DATE_SIMPLE));
                for (int i = 0; i < nestBean.getDay(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startCal.getTime());
                    cal.add(Calendar.DATE, i);
                    hotArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cdvMonth.update();
                }
            },2000);

            if (data.getNext().isSale()) {
                linEdit.setVisibility(View.VISIBLE);
                linResult.setVisibility(View.GONE);
                linRnsure.setVisibility(View.VISIBLE);
                if (currentBean == null) {
                    tvBuyTime.setText(null);
                    tvCurrentPrice.setText(null);
                } else {
                    tvBuyTime.setText(String.format(getString(R.string.format_time_and_count), currentBean.getSaleStartTime(), currentBean.getEndTime(), currentBean.getDay()));
                    tvCurrentPrice.setText(String.valueOf(data.getCurrent().getPrice()));
                }
                tvMyKsb.setText(String.valueOf(data.getKsb()));
            } else {
                linEdit.setVisibility(View.GONE);
                linResult.setVisibility(View.VISIBLE);
                linRnsure.setVisibility(View.GONE);
                GlideImgManager.loadCircleImage(this, data.getNext().getUserAvatar(), ivAvatar);
                tvNickName.setText(data.getNext().getUserName());
                String priceStr = String.format(getString(R.string.format_price_and_total), String.valueOf(data.getNext().getPrice()), String.valueOf(data.getNext().getTotalPrice()));
                SpannableStringBuilder builder = new SpannableStringBuilder(priceStr);
                String dayprice = String.valueOf(data.getNext().getPrice());
                int dayIndex = priceStr.indexOf(dayprice);
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                String totalprice = String.valueOf(data.getNext().getTotalPrice());
                int totalIndex = priceStr.lastIndexOf(totalprice);
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), totalIndex, totalIndex + totalprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvPrice.setText(builder);
                tvNextBidTime.setText(getString(R.string.next_start_bid_time) + data.getNext().getNextTime());

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
        if (resultCode == RESULT_OK && requestCode == Constant.REQUEST_PURCHASE) {
            showLoadingDialog(null);
            mvpPresenter.setNestTimeInfo(nestLocationId);
        }
    }
}
