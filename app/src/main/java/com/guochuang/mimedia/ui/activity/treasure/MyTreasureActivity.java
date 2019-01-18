package com.guochuang.mimedia.ui.activity.treasure;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.MyAddressActivity;
import com.guochuang.mimedia.ui.adapter.AddressAdapter;
import com.guochuang.mimedia.ui.adapter.MyTreasureAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTreasureActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_treasure)
    RecyclerView rvTreasure;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    MyTreasureAdapter adapter;
    List<String> dataArr=new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_treasure;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_treasure);
        rvTreasure.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        dataArr.add("0");
        dataArr.add("1");
        dataArr.add("2");
        dataArr.add("3");
        dataArr.add("4");
        adapter=new MyTreasureAdapter(dataArr);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_address:
                        startActivity(new Intent(MyTreasureActivity.this,MyAddressActivity.class).putExtra(Constant.ADDRESS_MODE,Constant.MODE_ADDRESS_SELECT));
                        break;
                    case R.id.tv_comment:
                        startActivity(new Intent(MyTreasureActivity.this,ShowListActivity.class));
                        break;
                    case R.id.tv_express:
                        startActivity(new Intent(MyTreasureActivity.this,ExpressInfoActivity.class));
                        break;
                }
            }
        });
        rvTreasure.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });

    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
