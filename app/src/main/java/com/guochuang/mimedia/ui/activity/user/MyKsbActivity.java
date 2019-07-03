package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.UrlConfig;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.KsbTrend;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.presenter.MyksbPresenter;
import com.guochuang.mimedia.mvp.view.MyksbView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import java.util.ArrayList;
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

public class MyKsbActivity extends MvpActivity<MyksbPresenter> implements MyksbView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_ksb_count)
    TextView tvKsbCount;
    @BindView(R.id.lin_ksb)
    LinearLayout linKsb;
    @BindView(R.id.tv_share_benefit)
    TextView tvShareBenefit;
    @BindView(R.id.lin_share_benefit)
    LinearLayout linShareBenefit;
    @BindView(R.id.tv_equal_count)
    TextView tvEqualCount;
    @BindView(R.id.lin_equal)
    LinearLayout linEqual;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.btn_ksb_transfer)
    Button btnKsbTransfer;
    @BindView(R.id.btn_transfer)
    Button btnTransfer;
    @BindView(R.id.tv_today_ksb_price)
    TextView tvTodayKsbPrice;
    @BindView(R.id.linechart)
    LineChartView linechart;
    @BindView(R.id.wv_desp)
    WebView wvDesp;

    private int numberOfPoints = 12;
    private LineChartData data;
    List<KsbTrend> ksbData;
    double maxValue=0;

    @Override
    protected MyksbPresenter createPresenter() {
        return new MyksbPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ksb;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_ksb_title);
        tvText.setText(R.string.detail);
        linechart.setInteractive(true);
        linechart.setZoomType(ZoomType.HORIZONTAL);
        linechart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
        linechart.setValueSelectionEnabled(true);
        linechart.setValueTouchEnabled(true);
        CommonUtil.initH5WebView(this, wvDesp);
        wvDesp.loadUrl(UrlConfig.getHtmlUrl(UrlConfig.URL_RULE_QC));
        mvpPresenter.getMyKsb();
        mvpPresenter.getKsbTrend();

    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.lin_ksb, R.id.lin_share_benefit, R.id.lin_equal, R.id.btn_ksb_transfer, R.id.btn_transfer,R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, MyKsbDetailsActivity.class));
                break;
            case R.id.lin_ksb:
                break;
            case R.id.lin_share_benefit:
                startActivity(new Intent(this, ShareBenefitDetailActivity.class));
                break;
            case R.id.lin_equal:
                break;
            case R.id.btn_ksb_transfer:
                if (getPref().getInt(PrefUtil.IDENTITY,0)==0) {
                    showShortToast(R.string.pls_identity_first);
                    startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                }else if(getPref().getInt(PrefUtil.SAFECODE,0)==0){
                    showShortToast(R.string.pls_safecode_first);
                    startActivityForResult(new Intent(this, TradePwdActivity.class), Constant.REFRESH);
                }else {
                    startActivityForResult(new Intent(this, MyKsbTransferActivity.class),Constant.REQUEST_TRANSFER);
                }
                break;
            case R.id.btn_transfer:
                if (getPref().getInt(PrefUtil.IDENTITY,0)==0) {
                    showShortToast(R.string.pls_identity_first);
                    startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                }else if(getPref().getInt(PrefUtil.SAFECODE,0)==0){
                    showShortToast(R.string.pls_safecode_first);
                    startActivityForResult(new Intent(this, TradePwdActivity.class), Constant.REFRESH);
                }else {
                    startActivityForResult(new Intent(this, MyKsbGrantActivity.class),Constant.REQUEST_GRANT);
                }
                break;
            case R.id.tv_copy:
                String address=tvAddress.getText().toString();
                if (!TextUtils.isEmpty(address)){
                    CommonUtil.copyMsg(this,address);
                    showShortToast(R.string.copy_success);
                }
                break;
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
            if (!TextUtils.isEmpty(ksbData.get(j).getMoney())){
                 money= Double.parseDouble(ksbData.get(j).getMoney());
            }
            PointValue value = new PointValue(j, (float)money);
            values.add(value);
            value.setLabel(ksbData.get(j).getStatisticsDate()+"\n"+ksbData.get(j).getMoney());
            if (money>maxValue){
                maxValue=money;
            }
        }

        Line line = new Line(values);
        line.setColor(Color.WHITE);
        line.setShape(ValueShape.CIRCLE);
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
                xlist.add(new AxisValue(i).setLabel(ksbData.get(i).getStatisticsDate().substring(5)));
            }
        }
        axisX.setValues(xlist);


        Axis axisY = new Axis();
        axisY.setHasLines(false);
        axisY.setHasSeparationLine(false);
        axisY.setTextColor(Color.WHITE);
        axisY.setTextSize(12);
        int charLength= String.valueOf(maxValue).length();
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
    public void setData(MyKsb data) {
        if (data!=null){
            tvKsbCount.setText(data.getCoin());
            tvShareBenefit.setText(data.getKsbPrice());
            tvEqualCount.setText(data.getMoney());
            tvAddress.setText(data.getKsbAddress());
            tvTodayKsbPrice.setText(data.getKsbPrice());
        }
    }

    @Override
    public void setKsbTrend(List<KsbTrend> data) {
        if (data!=null&&data.size()>0){
            ksbData=data;
            numberOfPoints=ksbData.size();
            generateData();
            linechart.setViewportCalculationEnabled(false);
            resetViewport();
        }
    }

    @Override
    public void setError(String msg) {
       showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.REQUEST_TRANSFER:
                case Constant.REQUEST_GRANT:
                    mvpPresenter.getMyKsb();
                    break;

            }
        }
    }
}
