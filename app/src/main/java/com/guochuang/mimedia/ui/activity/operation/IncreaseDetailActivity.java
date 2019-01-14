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
import com.guochuang.mimedia.mvp.model.RegistUser;
import com.guochuang.mimedia.mvp.presenter.IncreaseDetailPresenter;
import com.guochuang.mimedia.mvp.view.IncreaseDetailView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.InComeTimePop;
import com.guochuang.mimedia.tools.pay.UserTypePop;
import com.guochuang.mimedia.ui.adapter.IncreaseDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IncreaseDetailActivity extends MvpActivity<IncreaseDetailPresenter> implements IncreaseDetailView {
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
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    UserTypePop userTypePop;
    InComeTimePop inComeTimePop;

    List<RegistUser> itemArr=new ArrayList<>();
    IncreaseDetailAdapter adapter;
    int curPage=1;
    String beginDateStr;
    String endDateStr;


    @Override
    protected IncreaseDetailPresenter createPresenter() {
        return new IncreaseDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_increase_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.increase_detail);
        String time_type=getIntent().getStringExtra(Constant.TIME_TYPE);
        beginDateStr=getIntent().getStringExtra(Constant.START_DATE);
        endDateStr=getIntent().getStringExtra(Constant.END_DATE);
        tvType.setText(time_type);
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
        rvUser.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvUser.setItemAnimator(new DefaultItemAnimator());
        adapter=new IncreaseDetailAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvUser.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getIncreaseUser(curPage+1,Constant.PAGE_SIZE,beginDateStr,endDateStr);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getIncreaseUser(1,Constant.PAGE_SIZE,beginDateStr,endDateStr);
            }
        });
        mvpPresenter.getIncreaseUser(curPage,Constant.PAGE_SIZE,beginDateStr,endDateStr);
    }


    @OnClick({R.id.iv_back,R.id.lin_type,R.id.lin_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_type:
                setSelected(0);
                if (inComeTimePop != null && inComeTimePop.isShowing()) {
                    inComeTimePop.dismiss();
                }
                if (userTypePop == null) {
                    userTypePop = new UserTypePop(IncreaseDetailActivity.this, new UserTypePop.OnSelectItemListener() {
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
                if (userTypePop!=null&&userTypePop.isShowing()){
                    userTypePop.dismiss();
                }
                if (inComeTimePop==null){
                    inComeTimePop=new InComeTimePop(IncreaseDetailActivity.this, new InComeTimePop.OnSelectItemListener() {

                        @Override
                        public void onSelectItem(String section, Date startDate, Date endDate) {
                            beginDateStr =CommonUtil.dateToString(startDate,Constant.FORMAT_DATE_SIMPLE);
                            endDateStr =CommonUtil.dateToString(endDate,Constant.FORMAT_DATE_SIMPLE);
//                            showShortToast("startDate="+beginDateStr +",endDate="+endDateStr);
                            tvTime.setText(section);
                            mvpPresenter.getIncreaseUser(1,Constant.PAGE_SIZE,beginDateStr,endDateStr);
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
    public void setData(Page<RegistUser> data) {
        if (data!=null){
            srlRefresh.finishRefresh();
            srlRefresh.finishLoadmore();
            curPage=data.getCurrentPage();
            if (curPage==1){
                itemArr.clear();
            }
            if (data.getDataList()!=null) {
                itemArr.addAll(data.getDataList());
            }
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
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
