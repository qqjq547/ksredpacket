package com.guochuang.mimedia.ui.activity.operation;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.IncomeStatistics;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.presenter.IncomeStatisticsPresenter;
import com.guochuang.mimedia.mvp.view.IncomeStatisticsView;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.InComeTimePop;
import com.guochuang.mimedia.tools.InComeTypePop;

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

public class IncomeStatisticsActivity extends MvpActivity<IncomeStatisticsPresenter> implements IncomeStatisticsView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_type)
    LinearLayout linType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.lin_time)
    LinearLayout linTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_summary)
    TextView tvSummary;

    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    @BindView(R.id.linechart)
    LineChartView linechart;
    private int numberOfPoints = 12;
    private LineChartData data;
    InComeTypePop inComeTypePop;
    InComeTimePop inComeTimePop;
    long statisticsId;
    String parentType;
    String sonType;
    String beginDateStr;
    String endDateStr;

    List<IncomeStatistics.StatisticListBean> ksbData;
    double maxValue=0;
    List<DictionaryType> typeList=new ArrayList<>();
    @Override
    protected IncomeStatisticsPresenter createPresenter() {
        return new IncomeStatisticsPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_income_statistics;
    }

    @Override
    public void initViewAndData() {
        statisticsId=getIntent().getLongExtra(Constant.STATISTICSID,0);
        tvTitle.setText(R.string.income_statistics);
        tvText.setText(R.string.unit_ksb);
        linechart.setInteractive(true);
        linechart.setZoomType(ZoomType.HORIZONTAL);
        linechart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
        linechart.setValueSelectionEnabled(true);
        linechart.setValueTouchEnabled(true);
        Calendar calendar= Calendar.getInstance();
        endDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
        calendar.add(Calendar.DATE,-6);
        beginDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
        mvpPresenter.getIncomeStatisticsType(Constant.TYPE_REGION_STATISTICS);
        mvpPresenter.getIncreaseUser(statisticsId,parentType,sonType,beginDateStr,endDateStr);
    }

    @OnClick({R.id.iv_back,R.id.tv_summary,R.id.lin_type,R.id.lin_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_summary:
                IntentUtils.startIncomeDetailActivity(this,statisticsId,tvType.getText().toString(),tvTime.getText().toString(),beginDateStr,endDateStr,parentType,sonType);
                break;
            case R.id.lin_type:
                setSelected(0);
                if (inComeTimePop!=null&&inComeTimePop.isShowing()){
                    inComeTimePop.dismiss();
                }
                if (inComeTypePop==null){
                    inComeTypePop=new InComeTypePop(IncomeStatisticsActivity.this, typeList, new InComeTypePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(int group, int child) {
                            tvType.setText(typeList.get(group).getChildren().get(child).getName());
                            parentType=typeList.get(group).getDescription();
                            sonType=typeList.get(group).getChildren().get(child).getDescription();
                            mvpPresenter.getIncreaseUser(statisticsId,parentType,sonType,beginDateStr,endDateStr);
                        }
                    });
                }
                if (inComeTypePop.isShowing()){
                    inComeTypePop.dismiss();
                }else {
                    inComeTypePop.showAsDropDown(rgNav);
                }
                break;
            case R.id.lin_time:
                setSelected(1);
                if (inComeTypePop!=null&&inComeTypePop.isShowing()){
                    inComeTypePop.dismiss();
                }
                if (inComeTimePop==null){
                    inComeTimePop=new InComeTimePop(IncomeStatisticsActivity.this, new InComeTimePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(String section,Date startDate, Date endDate) {

                            tvTime.setText(section);
                            beginDateStr=CommonUtil.dateToString(startDate,Constant.FORMAT_DATE_SIMPLE);
                            endDateStr=CommonUtil.dateToString(endDate,Constant.FORMAT_DATE_SIMPLE);
                            mvpPresenter.getIncreaseUser(statisticsId,parentType,sonType,beginDateStr,endDateStr);
                        }
                    });
                }
                if (inComeTimePop.isShowing()){
                    inComeTimePop.dismiss();
                }else {
                    inComeTimePop.showAsDropDown(rgNav);
                }
                break;
        }
    }
    public void setSelected(int pos){
        if (pos==0){
            linType.setSelected(true);
            linTime.setSelected(false);
        }else {
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
        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < numberOfPoints; ++j) {
            double money=0.0d;
            if (!TextUtils.isEmpty(ksbData.get(j).getCoin())){
                money= Double.parseDouble(ksbData.get(j).getCoin());
            }
            PointValue value = new PointValue(j, (float)money);
            value.setLabel(ksbData.get(j).getTime()+"\n"+ksbData.get(j).getCoin());
            values.add(value);
            if (money>maxValue){
                maxValue=money;
            }
        }

        Line line = new Line(values);
        line.setColor(Color.WHITE);
        line.setShape(ValueShape.CIRCLE);
        line.setStrokeWidth(1);
        line.setCubic(true);
        line.setFilled(true);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasPoints(false);
        line.setPointRadius(2);
        line.setPointColor(Color.WHITE);
        line.setStrokeWidth(1);
        line.setHasGradientToTransparent(true);
        lines.add(line);

        data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setHasLines(false);
        axisX.setHasSeparationLine(false);
        axisX.setTextColor(Color.WHITE);
        axisX.setTextSize(12);
        List<AxisValue> xlist=new ArrayList<>();
        for (int i=0;i<ksbData.size();i++){
            if (i==0||i==(numberOfPoints/2)||i==numberOfPoints-1){
                xlist.add(new AxisValue(i).setLabel(ksbData.get(i).getTime().substring(5)));
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
    public void setData(IncomeStatistics data) {
        closeLoadingDialog();
            if (data!=null){
                String str=String.format(getString(R.string.format_income_statistics),beginDateStr,endDateStr,data.getStatistics());
                SpannableString span=new SpannableString(str);
                int index=str.lastIndexOf(data.getStatistics());
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), index, index + data.getStatistics().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvSummary.setText(span);
                if (data.getStatistics()!=null) {
                    ksbData = data.getStatisticList();
                    numberOfPoints = ksbData.size();
                    generateData();
                    linechart.setViewportCalculationEnabled(false);
                    resetViewport();
                }
        }
    }

    @Override
    public void setType(List<DictionaryType> data) {
        if (data!=null&&data.size()>0){
            typeList.addAll(data);
            parentType=data.get(0).getDescription();
            mvpPresenter.getIncreaseUser(statisticsId,parentType,sonType,beginDateStr,endDateStr);
        }
    }

    @Override
    public void setError(String msg) {
     closeLoadingDialog();
     showShortToast(msg);
    }
}
