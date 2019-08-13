package com.guochuang.mimedia.ui.activity.city;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MallRegionAll;
import com.guochuang.mimedia.mvp.model.MallRegionInfo;
import com.guochuang.mimedia.mvp.presenter.CityBuyPrePresenter;
import com.guochuang.mimedia.mvp.view.CityBuyPreView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.CityPreBuyAdapter;
import com.guochuang.mimedia.ui.dialog.AreaPickDialog;
import com.guochuang.mimedia.ui.dialog.BidNodeDialog;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityPreBuyActivity extends MvpActivity<CityBuyPrePresenter> implements CityBuyPreView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rg_mall)
    RadioGroup rgMall;
    @BindView(R.id.rb_province)
    RadioButton rbProvince;
    @BindView(R.id.rb_city)
    RadioButton rbCity;
    @BindView(R.id.rb_area)
    RadioButton rbArea;
    @BindView(R.id.tv_current_location)
    TextView tvCurrentLocation;
    @BindView(R.id.rv_location)
    RecyclerView rvLocation;

    CityPreBuyAdapter adapter;
    List<MallRegionInfo.RegionNodeListBean> nodeList=new ArrayList<>();
    List<MallRegionAll> regionAlls=new ArrayList<>();
    int provincePos=0;
    int cityPos=0;
    int areaPos=0;

    MallRegionInfo mallRegionInfo;
    String code="";
    int level=0;
    AreaPickDialog dialog;

    @Override
    protected CityBuyPrePresenter createPresenter() {
        return new CityBuyPrePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_city_prebuy;
    }

    @Override
    public void initViewAndData() {
        rbProvince.setChecked(true);
        rgMall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_province:
                        setSelectItem(provincePos);
                        break;
                    case R.id.rb_city:
                        setSelectItem(cityPos);
                        break;
                    case R.id.rb_area:
                        setSelectItem(areaPos);
                        break;
                }
            }
        });
        adapter=new CityPreBuyAdapter(nodeList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final MallRegionInfo.RegionNodeListBean data=nodeList.get(position);
                if (data.getIsBuy()==0){//未购买
                    BidNodeDialog bidNodeDialog= new BidNodeDialog(CityPreBuyActivity.this);
                    bidNodeDialog.setOnResultListener(new BidNodeDialog.OnResultListener() {
                        @Override
                        public void onResult(int money) {
                            int purchaseType=Constant.TYPE_PURCHASE_MALLNODE;
                            if (mallRegionInfo.getMinQc()>=mallRegionInfo.getMaxQc()){
                                showShortToast("已达到竞购最大金额");
                                return;
                            }
                            String bidPrice=String.valueOf(money);
                            int bidType=1;
                            long mallRegionId=mallRegionInfo.getId();
                            int nodeNumber=data.getNodeNumber();
                            IntentUtils.startPurchaseActivity(CityPreBuyActivity.this,purchaseType,bidPrice,bidType,mallRegionId,nodeNumber);
                        }
                    });
                    bidNodeDialog.show();
                }else {
                    startActivity(new Intent(CityPreBuyActivity.this,MyMallActivity.class));
                }
            }
        });
         rvLocation.setLayoutManager(new GridLayoutManager(this,5));
         rvLocation.addItemDecoration(new GridItemDecoration(5,30,false));
         rvLocation.setAdapter(adapter);
         mvpPresenter.getRegionAll();

    }
    @OnClick({R.id.iv_back,R.id.lin_bid_area})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_bid_area:
                int level=0;
                if (rbProvince.isChecked()){
                    level=0;
                }else if(rbCity.isChecked()){
                    level=1;
                }else if(rbArea.isChecked()){
                    level=2;
                }
                dialog=new AreaPickDialog(this, regionAlls, level,provincePos,cityPos);
                dialog.setOnItemSelectListener(new AreaPickDialog.OnItemSelectListener() {
                    @Override
                    public void onSelectItem(int position,int level,int provincePos,int cityPos) {
                        CityPreBuyActivity.this.provincePos=provincePos;
                        CityPreBuyActivity.this.cityPos=cityPos;
                        setSelectItem(position);
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void setRegionAll(List<MallRegionAll> data) {
        closeLoadingDialog();
     if (data!=null){
         regionAlls.addAll(data);
         setSelectItem(0);
     }
    }
    public void setSelectItem(int position){
        if (rbProvince.isChecked()){
            provincePos=position;
            cityPos=0;
            areaPos=0;
            code=regionAlls.get(provincePos).getCode();
            level=regionAlls.get(provincePos).getLevel();
        }else if(rbCity.isChecked()){
            cityPos=position;
            areaPos=0;
            code=regionAlls.get(provincePos).getCityList().get(cityPos).getCode();
            level=regionAlls.get(provincePos).getCityList().get(cityPos).getLevel();
        }else if(rbArea.isChecked()){
            areaPos=position;
            code=regionAlls.get(provincePos).getCityList().get(cityPos).getAreaList().get(areaPos).getCode();
            level=regionAlls.get(provincePos).getCityList().get(cityPos).getAreaList().get(areaPos).getLevel();
        }
        showLoadingDialog(null);
        mvpPresenter.getRegionInfo(code,level);
    }

    @Override
    public void setRegionInfo(MallRegionInfo data) {
        closeLoadingDialog();
     if (data!=null){
         mallRegionInfo=data;
         tvCurrentLocation.setText(data.getName());
         nodeList.clear();
         nodeList.addAll(data.getRegionNodeList());
         adapter.notifyDataSetChanged();
     }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}
