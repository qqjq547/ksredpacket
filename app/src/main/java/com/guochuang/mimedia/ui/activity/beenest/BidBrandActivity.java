package com.guochuang.mimedia.ui.activity.beenest;

import android.content.Intent;
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
    @BindView(R.id.tv_buy_day_count)
    TextView tvBuyDayCount;
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
    List<String> currentArr = new ArrayList<>();
    List<String> currentSelectArr = new ArrayList<>();
    List<String> nextArr = new ArrayList<>();
    String latitude;
    String longitude;
    long nestLocationId;
    NestTimeInfo timeInfo;
    double rate;
    NestTimeInfo.InfoBean curInfoBean;
    CaledarAdapter caledarAdapter;
    boolean isSelectCur=true;//是否选择当期

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
        latitude=getIntent().getStringExtra(Constant.LATITUDE);
        longitude=getIntent().getStringExtra(Constant.LONGITUDE);
        tvTitle.setText(String.valueOf(nestLocationId));
        dateFormat = new SimpleDateFormat(Constant.FORMAT_MONTH);
        tvMonth.setText(dateFormat.format(new Date()));
        initAdapter();
        cdvMonth.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                Date date=CalendarFactory.getCalendarByBean(bean).getTime();
                String monthStr = CommonUtil.dateToString(date, Constant.FORMAT_MONTH);
                tvMonth.setText(monthStr);
                String dateStr= CommonUtil.dateToString(date, Constant.FORMAT_DATE_SIMPLE);
                if (currentArr.contains(dateStr)){
                    setInfo(timeInfo.getCurrent());
                }else if (nextArr.contains(dateStr)){
                    setInfo(timeInfo.getNext());
                }
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
                double money=Double.parseDouble(text);
                if (money > timeInfo.getMaxPrice()) {
                    etBidPrice.setText(String.valueOf(timeInfo.getMaxPrice()));
                    return;
                }
                tvEqualKsb.setText(String.valueOf(DoubleUtil.divide(money*curInfoBean.getDay(),rate)));
            }
        });
        mvpPresenter.setNestTimeInfo(nestLocationId,latitude,longitude);
    }
   private void initAdapter(){
       caledarAdapter=new CaledarAdapter() {
           @Override
           public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
               if (convertView == null) {
                   convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_brand_date, null);
               }
               TextView tvDate = convertView.findViewById(R.id.tv_date);
               ImageView ivAuction = convertView.findViewById(R.id.iv_auction);
               ImageView ivHot = convertView.findViewById(R.id.iv_hot);
               if (bean.mothFlag != 0) {
                   tvDate.setTextColor(getResources().getColor(R.color.text_gray));
               } else {
                   tvDate.setTextColor(getResources().getColor(R.color.text_black));
               }
               tvDate.setText(String.valueOf(bean.day));
               String dateStr = CommonUtil.dateToString(CalendarFactory.getCalendarByBean(bean).getTime(), Constant.FORMAT_DATE_SIMPLE);
               if (currentArr.contains(dateStr)) {
                   if (timeInfo.getCurrent().isSale()){
                       ivAuction.setVisibility(View.GONE);
                       if (currentSelectArr.contains(dateStr)){
                           ivHot.setVisibility(View.VISIBLE);
                           if (timeInfo.getNext().isSale()){
                               if (isSelectCur){
                                   tvDate.setBackgroundResource(R.drawable.bg_date_red);
                                   tvDate.setTextColor(getResources().getColor(R.color.text_white));
                               }else {
                                   tvDate.setBackgroundResource(R.drawable.bg_date_yellow);
                                   tvDate.setTextColor(getResources().getColor(R.color.text_white));
                               }
                           }else {
                               tvDate.setBackgroundResource(R.drawable.bg_date_white);
                           }
                       }else {
                           ivHot.setVisibility(View.GONE);
                           tvDate.setBackgroundResource(R.drawable.bg_date_gray);
                       }
                   }else {
                       ivAuction.setVisibility(View.VISIBLE);
                       ivHot.setVisibility(View.GONE);
                       tvDate.setBackgroundResource(R.drawable.bg_date_gray);
                   }
               } else if(nextArr.contains(dateStr)){
                   if (timeInfo.getNext().isSale()){
                       ivAuction.setVisibility(View.GONE);
                       ivHot.setVisibility(View.VISIBLE);
                       if (isSelectCur){
                           tvDate.setBackgroundResource(R.drawable.bg_date_white);
                       }else {//当期被选择
                           tvDate.setBackgroundResource(R.drawable.bg_date_red);
                           tvDate.setTextColor(getResources().getColor(R.color.text_white));
                       }
                   }else {
                       ivAuction.setVisibility(View.VISIBLE);
                       ivHot.setVisibility(View.GONE);
                       tvDate.setBackgroundResource(R.drawable.bg_date_gray);
                   }
               }else {
                   tvDate.setBackgroundResource(R.drawable.bg_date_gray);
               }

               return convertView;
           }
       };
       cdvMonth.setAdapter(caledarAdapter);
   }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.iv_last, R.id.iv_next, R.id.tv_bid_record, R.id.btn_buy})
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
                if(curInfoBean!=null){
                    startActivity(new Intent(this, BidRecordActivity.class).putExtra(Constant.NESTTIMEINFOID, curInfoBean.getNestTimeInfoId()));
                }
                break;
            case R.id.btn_buy:
                if (curInfoBean!=null) {
                    String bidPrice = etBidPrice.getText().toString();
                    if (TextUtils.isEmpty(bidPrice)) {
                        showShortToast(R.string.buy_price_not_empty);
                    } else {
                        int price = Integer.parseInt(bidPrice);
                        if (price <= curInfoBean.getPrice()) {
                            showShortToast(R.string.buy_price_limit);
                        } else {
                            IntentUtils.startPurchaseActivity(this, Constant.TYPE_PURCHASE_NESTAD, curInfoBean.getNestTimeInfoId(), String.valueOf(price * curInfoBean.getDay()), price);
                        }
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
            NestTimeInfo.InfoBean currentBean = data.getCurrent();
            NestTimeInfo.InfoBean nestBean = data.getNext();
            Calendar startCal = Calendar.getInstance();
            if (currentBean != null) {
                startCal.setTime(CommonUtil.stringToDate(currentBean.getStartTime(), Constant.FORMAT_DATE_SIMPLE));
                for (int i = 0; i < nestBean.getDay(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startCal.getTime());
                    cal.add(Calendar.DATE, i);
                    currentArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
                startCal.setTime(CommonUtil.stringToDate(currentBean.getSaleStartTime(), Constant.FORMAT_DATE_SIMPLE));
                for (int i = 0; i <currentBean.getDay(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startCal.getTime());
                    cal.add(Calendar.DATE, i);
                    currentSelectArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
            }

            if (nestBean != null) {
                startCal.setTime(CommonUtil.stringToDate(nestBean.getStartTime(), Constant.FORMAT_DATE_SIMPLE));
                for (int i = 0; i < nestBean.getDay(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startCal.getTime());
                    cal.add(Calendar.DATE, i);
                    nextArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
            }
            cdvMonth.update();
            if (data.getCurrent().isSale()&&!data.getNext().isSale()){//当期可卖，并且下期不可卖
                setInfo(currentBean);
            }else  {//显示下一期数据
                setInfo(nestBean);
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
            mvpPresenter.setNestTimeInfo(nestLocationId,latitude,longitude);
        }
    }
    private void setInfo(NestTimeInfo.InfoBean infoBean){
        this.curInfoBean=infoBean;
        if (curInfoBean.getNestTimeInfoId()==timeInfo.getCurrent().getNestTimeInfoId()){
            if (!isSelectCur){
                isSelectCur=true;
                cdvMonth.update();
            }
        }else {
            if (isSelectCur){
                isSelectCur=false;
                cdvMonth.update();
            }
        }
        etBidPrice.setText(etBidPrice.getText());
        tvBuyTime.setText(String.format(getString(R.string.format_time_to_time), infoBean.getSaleStartTime(), infoBean.getEndTime()));
        tvBuyDayCount.setText(String.format(getString(R.string.format_day_count), infoBean.getDay()));
        if(infoBean.isSale()){//显示下一期数据
        linEdit.setVisibility(View.VISIBLE);
        linResult.setVisibility(View.GONE);
        linRnsure.setVisibility(View.VISIBLE);
        tvCurrentPrice.setText(String.valueOf(infoBean.getPrice()));
        tvMyKsb.setText(String.valueOf(timeInfo.getKsb()));
    } else {
        linEdit.setVisibility(View.GONE);
        linResult.setVisibility(View.VISIBLE);
        linRnsure.setVisibility(View.GONE);
        GlideImgManager.loadCircleImage(this, infoBean.getUserAvatar(), ivAvatar);
        tvNickName.setText(infoBean.getUserName());
        String priceStr = String.format(getString(R.string.format_price_and_total), String.valueOf(infoBean.getPrice()), String.valueOf(infoBean.getTotalPrice()));
        SpannableStringBuilder builder = new SpannableStringBuilder(priceStr);
        String dayprice = String.valueOf(infoBean.getPrice());
        int dayIndex = priceStr.indexOf(dayprice);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), dayIndex, dayIndex + dayprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String totalprice = String.valueOf(infoBean.getTotalPrice());
        int totalIndex = priceStr.lastIndexOf(totalprice);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), totalIndex, totalIndex + totalprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);
        tvNextBidTime.setText(getString(R.string.next_start_bid_time) + infoBean.getNextTime());
    }
    }

}
