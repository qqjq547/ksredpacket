package com.guochuang.mimedia.ui.activity.beenest;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.guochuang.mimedia.ui.activity.MainActivity;
import com.sz.gcyh.KSHongBao.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    @BindView(R.id.rgroup_date)
    RadioGroup rGroupDate;
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
    int selectDayCount=5;
    CaledarAdapter caledarAdapter;
    HashMap<String,String> dateAvatar=new HashMap<>();

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
                tvEqualKsb.setText(String.valueOf(DoubleUtil.divide(money*selectDayCount,rate)));
            }
        });
        rGroupDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbtn_five:
                         setSelect(5);
                        break;
                    case R.id.rbtn_ten:
                        setSelect(10);
                        break;
                    case R.id.rbtn_fifth:
                        setSelect(15);
                        break;
                }
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
               ImageView ivAvatar = convertView.findViewById(R.id.iv_avatar);

               if (bean.mothFlag != 0) {
                   tvDate.setTextColor(getResources().getColor(R.color.text_gray));
               } else {
                   tvDate.setTextColor(getResources().getColor(R.color.text_black));
               }
               tvDate.setText(String.valueOf(bean.day));
               String dateStr = CommonUtil.dateToString(CalendarFactory.getCalendarByBean(bean).getTime(), Constant.FORMAT_DATE_SIMPLE);
               if (currentArr.contains(dateStr)) {
                   ivAuction.setVisibility(View.VISIBLE);
                   ivHot.setVisibility(View.GONE);
                   tvDate.setBackgroundResource(R.drawable.bg_date_gray);
                   ivAvatar.setVisibility(View.VISIBLE);
                   GlideImgManager.loadCircleImage(BidBrandActivity.this,dateAvatar.get(dateStr),ivAvatar);
               } else if(nextArr.contains(dateStr)){
                   ivAuction.setVisibility(View.GONE);
                   ivHot.setVisibility(View.VISIBLE);
                   ivAvatar.setVisibility(View.VISIBLE);
                   if (currentSelectArr.contains(dateStr)){
                       tvDate.setBackgroundResource(R.drawable.bg_date_red);
                       tvDate.setTextColor(getResources().getColor(R.color.text_white));
                   }else {//当期被选择
                       tvDate.setBackgroundResource(R.drawable.bg_date_white);
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
                if(timeInfo!=null){
                    startActivity(new Intent(this, BidRecordActivity.class).putExtra(Constant.NESTLOCATIONID, nestLocationId).putExtra(Constant.STARTDATE, timeInfo.getStartDate()));
                }
                break;
            case R.id.btn_buy:
                if (timeInfo!=null) {
                    String bidPrice = etBidPrice.getText().toString();
                    if (TextUtils.isEmpty(bidPrice)) {
                        showShortToast(R.string.buy_price_not_empty);
                    } else {
                        int price = Integer.parseInt(bidPrice);
                        if (price<=timeInfo.getCurrentPrice()){
                            showShortToast(R.string.buy_price_limit);
                        }else if (price >= timeInfo.getMaxPrice()) {
                            showShortToast(R.string.buy_price_low);
                        } else {
                            IntentUtils.startPurchaseActivity(this, Constant.TYPE_PURCHASE_NESTAD, nestLocationId, price,String.valueOf(price*selectDayCount),selectDayCount,timeInfo.getStartDate(),latitude,longitude);
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
            dateAvatar.clear();
            currentArr.clear();
            nextArr.clear();
            if (data.getBuyList()!=null&&data.getBuyList().size()>0){
                for (NestTimeInfo.BuyListBean listBean:data.getBuyList()){
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(CommonUtil.stringToDate(listBean.getStartDate(), Constant.FORMAT_DATE_SIMPLE));
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(CommonUtil.stringToDate(listBean.getEndDate(), Constant.FORMAT_DATE_SIMPLE));
                    int dayCount=differentDays(startCal.getTime(),endCal.getTime())+1;
                    for (int i = 0; i < dayCount; i++) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startCal.getTime());
                        cal.add(Calendar.DATE, i);
                        dateAvatar.put(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE),listBean.getUserAvatar());
                        currentArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                    }
                }
            }
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(CommonUtil.stringToDate(data.getStartDate(), Constant.FORMAT_DATE_SIMPLE));
            for(int i=0;i<15;i++){
                Calendar cal = Calendar.getInstance();
                cal.setTime(startCal.getTime());
                cal.add(Calendar.DATE, i);
                nextArr.add(CommonUtil.dateToString(cal.getTime(), Constant.FORMAT_DATE_SIMPLE));
            }

            setSelect(5);

            etBidPrice.setText(etBidPrice.getText());

            linEdit.setVisibility(View.GONE);
            linResult.setVisibility(View.VISIBLE);
            linRnsure.setVisibility(View.GONE);
            linEdit.setVisibility(View.VISIBLE);
            linResult.setVisibility(View.GONE);
            linRnsure.setVisibility(View.VISIBLE);
            tvCurrentPrice.setText(String.valueOf(data.getCurrentPrice()));
            tvMyKsb.setText(String.valueOf(timeInfo.getKsb()));
        }
    }
    public void setSelect(int dayCount){
        this.selectDayCount=dayCount;
        currentSelectArr.clear();
        currentSelectArr.addAll(nextArr.subList(0,dayCount));
        tvBuyTime.setText(String.format(getString(R.string.format_time_to_time), currentSelectArr.get(0), currentSelectArr.get(currentSelectArr.size()-1)));
        tvBuyDayCount.setText(String.format(getString(R.string.format_day_count), currentSelectArr.size()));
        cdvMonth.update();
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

    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
}
