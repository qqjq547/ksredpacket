package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.MenuTimeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InComeTimePop extends PopupWindow {
    Activity activity;
    @BindView(R.id.rv_time)
    RecyclerView rvTime;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.lin_root)
    View linRoot;

    List<String> timeArr = new ArrayList<>();
    MenuTimeAdapter timeAdapter;
    OnSelectItemListener onSelectItemListener;
    Calendar startCal,endCal;

    public interface OnSelectItemListener {
        void onSelectItem(String section, Date startDate, Date endDate);
    }

    public InComeTimePop(Activity activity,final OnSelectItemListener onSelectItemListener) {
        super(activity);
        this.activity=activity;
        this.onSelectItemListener = onSelectItemListener;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.ppw_income_time, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        startCal=Calendar.getInstance();
        endCal=Calendar.getInstance();
        tvStartDate.setText(CommonUtil.dateToString(startCal.getTime(),Constant.FORMAT_DATE_SIMPLE));
        tvEndDate.setText(CommonUtil.dateToString(endCal.getTime(),Constant.FORMAT_DATE_SIMPLE));
        rvTime.setLayoutManager(new GridLayoutManager(activity, 4));
        rvTime.setItemAnimator(new DefaultItemAnimator());
        timeArr.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.recent_day)));
        timeAdapter = new MenuTimeAdapter(timeArr);
        timeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                timeAdapter.setSelectPos(position);
                dismiss();
                Calendar start=Calendar.getInstance();
                Calendar end=Calendar.getInstance();
                switch (position){
                    case 0:
                        start.add(Calendar.DATE,-7+1);
                        break;
                    case 1:
                        start.add(Calendar.DATE,-14+1);
                        break;
                    case 2:
                        start.add(Calendar.DATE,-30+1);
                        break;
                    case 3:
                        start.add(Calendar.MONTH,-1);
                        start.set(Calendar.DATE,1);
                        end.add(Calendar.MONTH,-1);
                        int maxday=end.getActualMaximum(Calendar.DATE);
                        end.set(Calendar.DATE,maxday);
                        break;
                    case 4:
                        start.add(Calendar.DATE,-90+1);
                        break;
                    case 5:
                        start.add(Calendar.DATE,-180+1);
                        break;

                }
                if (onSelectItemListener != null) {
                    onSelectItemListener.onSelectItem(timeAdapter.getItem(position), start.getTime(),end.getTime());
                }
            }
        });
        rvTime.setAdapter(timeAdapter);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.bg_trans)));
    }

    @OnClick(R.id.lin_root)
    public void onViewClicked() {
        dismiss();
    }
    @OnClick({R.id.lin_root,R.id.lin_start_date, R.id.lin_end_date, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_root:
                dismiss();
                break;
            case R.id.lin_start_date:
                if (startCal==null){
                    startCal=Calendar.getInstance();
                }
                pickDate(startCal);
                break;
            case R.id.lin_end_date:
                if (endCal==null){
                    endCal=Calendar.getInstance();
                }
                pickDate(endCal);
                break;
            case R.id.tv_sure:
                if (startCal!=null&&endCal!=null){
                    int day = (int) ((endCal.getTimeInMillis()  -  startCal.getTimeInMillis())  /  (24  *  3600  *  1000));
                    if (day>=0&&day<=180){
                        timeAdapter.setSelectPos(-1);
                        dismiss();
                        if (onSelectItemListener != null) {
                            onSelectItemListener.onSelectItem(CommonUtil.dateToString(startCal.getTime(),Constant.FORMAT_DATE_SIMPLE)+"\n"+CommonUtil.dateToString(endCal.getTime(),Constant.FORMAT_DATE_SIMPLE), startCal.getTime(), endCal.getTime());
                        }
                        return ;
                    }else {
                        if (day<0){
                            Toast.makeText(activity,R.string.start_date_is_less,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(activity,R.string.only_chose_half_year,Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(activity,R.string.pls_select_date,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void pickDate(final Calendar calendar){
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                if (startCal!=null) {
                    tvStartDate.setText(CommonUtil.dateToString(startCal.getTime(), Constant.FORMAT_DATE_SIMPLE));
                }
                if (endCal!=null){
                    tvEndDate.setText(CommonUtil.dateToString(endCal.getTime(),Constant.FORMAT_DATE_SIMPLE));
                }
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }
}
