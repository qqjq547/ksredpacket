package com.guochuang.mimedia.ui.activity;

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
import com.guochuang.mimedia.ui.adapter.AddressAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAddressActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    int mode;

    AddressAdapter adapter;
    List<String> dataArr=new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_address;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.received_address);
        mode=getIntent().getIntExtra(Constant.ADDRESS_MODE,Constant.MODE_ADDRESS_CHECK);
        if (mode==Constant.MODE_ADDRESS_SELECT){
            tvText.setText(R.string.confirm);
            tvAdd.setVisibility(View.VISIBLE);
        }else {
            tvText.setText(null);
            tvAdd.setVisibility(View.GONE);
        }
        rvAddress.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        dataArr.add("");
        dataArr.add("");
        dataArr.add("");
        adapter=new AddressAdapter(dataArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mode==Constant.MODE_ADDRESS_SELECT){
                    MyAddressActivity.this.adapter.setSelectPos(position);
                }
            }
        });
        adapter.setSelectAble(true);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAddress.setAdapter(adapter);
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

    @OnClick({R.id.iv_back,R.id.tv_text,R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (mode==Constant.MODE_ADDRESS_SELECT){
                    int selectPos=adapter.getSelectPos();
//                    getIntent().putExtra(Constant.)
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(this,AddAddressActivity.class),Constant.REQUEST_ADD_ADDRESS);
                break;
        }

    }
}
