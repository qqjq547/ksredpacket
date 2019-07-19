package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.ui.activity.city.BidPriceActivity;
import com.guochuang.mimedia.ui.activity.city.EditBoardActivity;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MyRegion;
import com.guochuang.mimedia.mvp.presenter.MyCityPresenter;
import com.guochuang.mimedia.mvp.view.MyCityView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.MyCityAdapter;
import com.guochuang.mimedia.ui.dialog.CityBenefitExplainDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCityActivity extends MvpActivity<MyCityPresenter> implements MyCityView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_city_income)
    TextView tvCityIncome;
    @BindView(R.id.tv_bid_run)
    TextView tvBidRun;
    @BindView(R.id.tv_city_num)
    TextView tvCityNum;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    List<MyRegion.MyRegionListDtoBean> regionArr = new ArrayList<>();
    MyCityAdapter myCityAdapter;
    MyRegion myRegion;
    int operatePos=0;

    @Override
    protected MyCityPresenter createPresenter() {
        return new MyCityPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_city;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_city_title);
        tvText.setText(R.string.my_city_income_rule);
        myCityAdapter=new MyCityAdapter(regionArr);
        myCityAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        myCityAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        myCityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyRegion.MyRegionListDtoBean item=regionArr.get(position);
                IntentUtils.startCityDetailActivity(MyCityActivity.this,item.getRegionId());
            }
        });
        myCityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                operatePos=position;
                long regionId=myRegion.getMyRegionListDto().get(position).getRegionId();
                if (view.getId()==R.id.iv_finger_pwd){
                    showLoadingDialog(null);
                    mvpPresenter.cityIsLock(regionId,regionArr.get(position).isAuction()?0:1);
                }else if(view.getId()==R.id.tv_notice_alter){
                    startActivityForResult(new Intent(MyCityActivity.this,EditBoardActivity.class).putExtra(Constant.REGIONID,regionId),Constant.REQUEST_SET_BOARD);
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL,false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(myCityAdapter);
        srl.setEnableRefresh(true);
        srl.setEnableLoadmore(false);
        srl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getMyRegion();
            }
        });
        mvpPresenter.getMyRegion();
    }

    @OnClick({R.id.iv_back,
              R.id.tv_text,
              R.id.lin_bid_run})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (myRegion!=null&&!TextUtils.isEmpty(myRegion.getExplain())) {
                    new CityBenefitExplainDialog(this,myRegion.getExplain()).show();
                }
                break;
            case R.id.lin_bid_run:
                startActivity(new Intent(this,BidPriceActivity.class));
                break;

        }
    }

    @Override
    public void setData(MyRegion data) {
        srl.finishRefresh();
        if (data!=null){
            myRegion=data;
            int regionNumber=0;
            if (data.getRegionStatisticsDto()!=null){
                regionNumber = data.getRegionStatisticsDto().getRegionNumber();
                tvCityIncome.setText(data.getRegionStatisticsDto().getTotalIncome());
                tvBidRun.setText(data.getRegionStatisticsDto().getCompensate());
            }
            String numStr = String.format(getString(R.string.format_current_own_city), regionNumber);
            SpannableString span = new SpannableString(numStr);
            int index = numStr.indexOf(String.valueOf(regionNumber));
            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_city_yellow)), index, index + String.valueOf(regionNumber).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvCityNum.setText(span);
            regionArr.clear();
            if (data.getMyRegionListDto()!=null){
                regionArr.addAll(data.getMyRegionListDto());
            }
            myCityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIsLock(Boolean data) {
        closeLoadingDialog();
        if (data!=null){
            boolean show=regionArr.get(operatePos).isAuction();
            regionArr.get(operatePos).setAuction(!show);
            myCityAdapter.notifyItemChanged(operatePos);
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
        if (resultCode==RESULT_OK){
            mvpPresenter.getMyRegion();
        }
    }
}
