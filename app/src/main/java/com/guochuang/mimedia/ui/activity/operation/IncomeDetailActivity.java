package com.guochuang.mimedia.ui.activity.operation;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.IncomeDetail;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.presenter.IncomeDetailPresenter;
import com.guochuang.mimedia.mvp.view.IncomeDetailView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.InComeTimePop;
import com.guochuang.mimedia.tools.InComeTypePop;
import com.guochuang.mimedia.ui.adapter.IncomeDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IncomeDetailActivity extends MvpActivity<IncomeDetailPresenter> implements IncomeDetailView {
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
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_income)
    RecyclerView rvIncome;

    InComeTypePop inComeTypePop;
    InComeTimePop inComeTimePop;

    List<IncomeDetail> itemArr=new ArrayList<>();
    IncomeDetailAdapter adapter;
    long statisticsId;
    String parentType;
    String sonType;
    String beginDateStr;
    String endDateStr;
    int curPage=1;
    List<DictionaryType> typeList=new ArrayList<>();
    @Override
    protected IncomeDetailPresenter createPresenter() {
        return new IncomeDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_income_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.income_detail);
        tvText.setText(R.string.unit_ksb);
        statisticsId=getIntent().getLongExtra(Constant.STATISTICSID,0);
        String income_type=getIntent().getStringExtra(Constant.INCOME_TYPE);
        String time_type=getIntent().getStringExtra(Constant.TIME_TYPE);
        beginDateStr=getIntent().getStringExtra(Constant.START_DATE);
        endDateStr=getIntent().getStringExtra(Constant.END_DATE);
        parentType=getIntent().getStringExtra(Constant.PARENT_TYPE);
        sonType=getIntent().getStringExtra(Constant.SON_TYPE);
        tvType.setText(income_type);
        if (!TextUtils.isEmpty(time_type)){
            tvTime.setText(time_type);
        }else {
            if (TextUtils.isEmpty(beginDateStr)&&TextUtils.isEmpty(endDateStr)){
                tvTime.setText(getResources().getStringArray(R.array.recent_day)[0]);
                Calendar calendar= Calendar.getInstance();
                endDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
                calendar.add(Calendar.DATE,-6);
                beginDateStr=CommonUtil.dateToString(calendar.getTime(),Constant.FORMAT_DATE_SIMPLE);
            }else {
                tvTime.setText(beginDateStr+"\n"+endDateStr);
            }
        }
        rvIncome.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvIncome.setItemAnimator(new DefaultItemAnimator());
        adapter=new IncomeDetailAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvIncome.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getIncreaseUser(curPage+1,Constant.PAGE_SIZE,statisticsId,parentType,sonType,beginDateStr,endDateStr);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getIncreaseUser(1,Constant.PAGE_SIZE,statisticsId,parentType,sonType,beginDateStr,endDateStr);
            }
        });
         mvpPresenter.getIncreaseUser(curPage,Constant.PAGE_SIZE,statisticsId,parentType,sonType,beginDateStr,endDateStr);
         mvpPresenter.getIncomeStatisticsType(Constant.TYPE_REGION_STATISTICS);
    }


    @OnClick({R.id.iv_back,R.id.lin_type,R.id.lin_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_type:
                setSelected(0);
                if (inComeTimePop!=null&&inComeTimePop.isShowing()){
                    inComeTimePop.dismiss();
                }
                if (inComeTypePop==null){
                    inComeTypePop=new InComeTypePop(IncomeDetailActivity.this,typeList, new InComeTypePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(int group, int child) {
                            tvType.setText(typeList.get(group).getChildren().get(child).getName());
                            parentType=typeList.get(group).getDescription();
                            sonType=typeList.get(group).getChildren().get(child).getDescription();
                            mvpPresenter.getIncreaseUser(1,Constant.PAGE_SIZE,statisticsId,parentType,sonType,beginDateStr,endDateStr);
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
                    inComeTimePop=new InComeTimePop(IncomeDetailActivity.this, new InComeTimePop.OnSelectItemListener() {

                        @Override
                        public void onSelectItem(String section, Date startDate, Date endDate) {
                            tvTime.setText(section);
                            beginDateStr =CommonUtil.dateToString(startDate,Constant.FORMAT_DATE_SIMPLE);
                            endDateStr =CommonUtil.dateToString(endDate,Constant.FORMAT_DATE_SIMPLE);
                            mvpPresenter.getIncreaseUser(1,Constant.PAGE_SIZE,statisticsId,parentType,sonType,beginDateStr,endDateStr);
                        }
                    });
                }
                if (inComeTimePop.isShowing()){
                    inComeTimePop.dismiss();
                }else {
                    inComeTimePop.showAsDropDown(rgNav);
                }
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

    @Override
    public void setData(Page<IncomeDetail> data) {
        if (data!=null){
            srlRefresh.finishRefresh();
            srlRefresh.finishLoadmore();
            curPage=data.getCurrentPage();
            if (curPage==1){
                itemArr.clear();
            }
            itemArr.addAll(data.getDataList());
            adapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
        }else {
            curPage=1;
            itemArr.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setType(List<DictionaryType> data) {
        if (data!=null){
            typeList.addAll(data);
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
