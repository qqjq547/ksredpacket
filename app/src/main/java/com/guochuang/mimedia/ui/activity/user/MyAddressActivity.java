package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.presenter.MyAddressPresenter;
import com.guochuang.mimedia.mvp.view.MyAddressView;
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

public class MyAddressActivity extends MvpActivity<MyAddressPresenter> implements MyAddressView {
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
    int curPage;
    long snatchId=0;

    AddressAdapter adapter;
    List<Address> dataArr=new ArrayList<>();

    @Override
    protected MyAddressPresenter createPresenter() {
        return new MyAddressPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_address;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.received_address);
        snatchId=getIntent().getLongExtra(Constant.SNATCHID,0);
        if (snatchId>0){
            tvText.setText(R.string.confirm);
        }else {
            tvText.setText(null);
        }
        rvAddress.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new AddressAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (snatchId>0){
                    MyAddressActivity.this.adapter.setSelectPos(position);
                }else {
                    startActivityForResult(new Intent(MyAddressActivity.this,AddAddressActivity.class).putExtra(Constant.ADDRESS,dataArr.get(position)),Constant.REQUEST_ADD_ADDRESS);
                }
            }
        });
        if (snatchId>0){
            adapter.setSelectAble(true);
        }else {
            adapter.setSelectAble(false);
        }
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAddress.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.userAddressList(curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.userAddressList(1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.userAddressList(1,Constant.PAGE_SIZE);
    }

    @OnClick({R.id.iv_back,R.id.tv_text,R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (snatchId>0){
                    showLoadingDialog(null);
                    int selectPos=adapter.getSelectPos();
                    mvpPresenter.setWinAddress(snatchId,dataArr.get(selectPos).getUuid());
                }
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(this,AddAddressActivity.class),Constant.REQUEST_ADD_ADDRESS);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.REQUEST_ADD_ADDRESS:
                     mvpPresenter.userAddressList(1,Constant.PAGE_SIZE);
                    break;
            }
        }

    }

    @Override
    public void setData(Page<Address> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            dataArr.clear();
        }
        if (data.getDataList() != null) {
            dataArr.addAll(data.getDataList());
        }
        adapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setAddressData(Boolean data) {
        closeLoadingDialog();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }
}
