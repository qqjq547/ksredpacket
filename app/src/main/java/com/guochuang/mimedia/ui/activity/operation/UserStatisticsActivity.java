package com.guochuang.mimedia.ui.activity.operation;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.UserStatistics;
import com.guochuang.mimedia.mvp.presenter.UserStatisticsPresenter;
import com.guochuang.mimedia.mvp.view.UserStatisticsView;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.InComeTimePop;
import com.guochuang.mimedia.tools.pay.UserTypePop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class UserStatisticsActivity extends MvpActivity<UserStatisticsPresenter> implements UserStatisticsView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_type)
    LinearLayout linType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.lin_time)
    LinearLayout linTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    @BindView(R.id.linechart)
    LineChartView linechart;
    @BindView(R.id.tv_total_user)
    TextView tvTotalUser;
    @BindView(R.id.tv_today_increase)
    TextView tvTodayIncrease;
    @BindView(R.id.tv_yesterday_increase)
    TextView tvYesterdayIncrease;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_increase)
    TextView tvIncrease;
    @BindView(R.id.tv_active)
    TextView tvActive;

    private int numberOfPoints = 12;
    private LineChartData data;
    UserTypePop userTypePop;
    InComeTimePop inComeTimePop;
    List<UserStatistics.ListBean> userData;
    int maxValue=0;
    String beginDateStr;
    String endDateStr;
    @Override
    protected UserStatisticsPresenter createPresenter() {
        return new UserStatisticsPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_user_statistics;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.user_statistics);
        linechart.setInteractive(true);
        linechart.setZoomType(ZoomType.HORIZONTAL);
        linechart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
        linechart.setValueSelectionEnabled(true);
        linechart.setValueTouchEnabled(true);

        Calendar calendar= Calendar.getInstance();
        endDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
        calendar.add(Calendar.DATE,-6);
        beginDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
        mvpPresenter.getUserStatistics(beginDateStr,endDateStr);
    }

    @OnClick({R.id.iv_back, R.id.tv_summary,R.id.tv_increase,R.id.tv_active, R.id.lin_type, R.id.lin_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_summary:
            case R.id.tv_increase:
            case R.id.tv_active:
                IntentUtils.startIncreaseDetailActivity(this,tvTime.getText().toString(),beginDateStr,endDateStr);
                break;
            case R.id.lin_type:
                setSelected(0);
                if (inComeTimePop != null && inComeTimePop.isShowing()) {
                    inComeTimePop.dismiss();
                }
                if (userTypePop == null) {
                    userTypePop = new UserTypePop(UserStatisticsActivity.this, new UserTypePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(String type) {
                            tvType.setText(type);
                        }
                    });
                }
                if (userTypePop.isShowing()) {
                    userTypePop.dismiss();
                } else {
                    userTypePop.showAsDropDown(rgNav);
                }
                break;
            case R.id.lin_time:
                setSelected(1);
                if (userTypePop != null && userTypePop.isShowing()) {
                    userTypePop.dismiss();
                }
                if (inComeTimePop == null) {
                    inComeTimePop = new InComeTimePop(UserStatisticsActivity.this, new InComeTimePop.OnSelectItemListener() {

                        @Override
                        public void onSelectItem(String section, Date startDate, Date endDate) {
                            tvTime.setText(section);
                            beginDateStr=CommonUtil.dateToString(startDate,Constant.FORMAT_DATE_SIMPLE);
                            endDateStr=CommonUtil.dateToString(endDate,Constant.FORMAT_DATE_SIMPLE);
                            mvpPresenter.getUserStatistics(beginDateStr,endDateStr);
                        }
                    });
                }
                if (inComeTimePop.isShowing()) {
                    inComeTimePop.dismiss();
                } else {
                    inComeTimePop.showAsDropDown(rgNav);
                }
                break;
        }
    }

    public void setSelected(int pos) {
        if (pos == 0) {
            linType.setSelected(true);
            linTime.setSelected(false);
        } else {
            linType.setSelected(false);
            linTime.setSelected(true);
        }

    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(linechart.getMaximumViewport());
        v.bottom = 0;
        v.left = 0;
        v.top=(float) maxValue*3/2;
        v.right = numberOfPoints+numberOfPoints/20;
        linechart.setMaximumViewport(v);
        linechart.setCurrentViewport(v);
    }

    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values1 = new ArrayList<PointValue>();
        for (int j = 0; j < numberOfPoints; ++j) {
            int userNum=userData.get(j).getIncrementUser();
            PointValue value = new PointValue(j,userNum);
            value.setLabel(userData.get(j).getStatisticsDate()+"\n"+userNum);
            values1.add(value);
            if (userNum>maxValue){
                maxValue=userNum;
            }
        }

        Line line1 = new Line(values1);
        line1.setColor(getResources().getColor(R.color.bg_yellow));
        line1.setShape(ValueShape.CIRCLE);
        line1.setStrokeWidth(1);
        line1.setCubic(true);
        line1.setFilled(true);
        line1.setHasLabels(true);
        line1.setHasLabelsOnlyForSelected(true);
        line1.setHasPoints(false);
        line1.setPointRadius(2);
        line1.setPointColor(Color.WHITE);
        line1.setStrokeWidth(1);
        line1.setHasGradientToTransparent(true);

        List<PointValue> values2 = new ArrayList<PointValue>();
        for (int j = 0; j < numberOfPoints; ++j) {
            int userNum=userData.get(j).getActiveUser();
            PointValue value = new PointValue(j, userNum);
            value.setLabel(userData.get(j).getStatisticsDate()+"\n"+userNum);
            values2.add(value);
            if (userNum>maxValue){
                maxValue=userNum;
            }
        }

        Line line2 = new Line(values2);
        line2.setColor(getResources().getColor(R.color.bg_active));
        line2.setShape(ValueShape.CIRCLE);
        line2.setStrokeWidth(1);
        line2.setCubic(true);
        line2.setFilled(true);
        line2.setHasLabels(true);
        line2.setHasLabelsOnlyForSelected(true);
        line2.setHasPoints(false);
        line2.setPointRadius(2);
        line2.setPointColor(Color.WHITE);
        line2.setStrokeWidth(1);
        line2.setHasGradientToTransparent(true);

        lines.add(line1);
        lines.add(line2);

        data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setHasLines(false);
        axisX.setHasSeparationLine(false);
        axisX.setTextColor(Color.WHITE);
        axisX.setTextSize(12);

        List<AxisValue> xlist=new ArrayList<>();
        for (int i=0;i<userData.size();i++){
            if (i==0||i==(numberOfPoints/2)||i==numberOfPoints-1){
                xlist.add(new AxisValue(i).setLabel(userData.get(i).getStatisticsDate().substring(5)));
            }
        }
        axisX.setValues(xlist);

        Axis axisY = new Axis();
        axisY.setHasLines(false);
        axisY.setHasSeparationLine(false);
        axisY.setTextColor(Color.WHITE);
        axisY.setTextSize(12);
        int charLength= String.valueOf((int)maxValue).length();
        axisY.setMaxLabelChars(charLength);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setValueLabelBackgroundColor(Color.WHITE);
        data.setValueLabelsTextColor(Color.BLACK);
        data.setValueLabelTextSize(7);
        data.setValueLabelTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        linechart.setLineChartData(data);
    }

    @Override
    public void setData(UserStatistics data) {
        closeLoadingDialog();
        if (data!=null){
            tvTotalUser.setText(String.valueOf(data.getSumUserNum()));
            tvTodayIncrease.setText(String.valueOf(data.getTodayUserNum()));
            tvYesterdayIncrease.setText(String.valueOf(data.getYesterdayUserNum()));
            String str=String.format(getString(R.string.format_user_statistics),beginDateStr,endDateStr);
            tvSummary.setText(str);
            String incrase=String.valueOf(data.getStatPeriodUserNum());
            String increaseStr=String.format(getString(R.string.format_increase),incrase);
            SpannableString span1=new SpannableString(increaseStr);
            int index1=increaseStr.lastIndexOf(incrase);
            span1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_yellow)), index1, index1 + incrase.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvIncrease.setText(span1);

            String active=String.valueOf(data.getStatPeriodActiveNum());
            String activeStr=String.format(getString(R.string.format_active),active);
            SpannableString span2=new SpannableString(activeStr);
            int index2=activeStr.lastIndexOf(active);
            span2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_active)), index2, index2 + active.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvActive.setText(span2);

            if (data.getList()!=null){
                userData = data.getList();
                numberOfPoints = userData.size();
                generateData();
                linechart.setViewportCalculationEnabled(false);
                resetViewport();
            }
        }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}
