package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.CoinAddressPresenter;
import com.guochuang.mimedia.mvp.view.CoinAddress;
import com.guochuang.mimedia.mvp.view.CoinAddressView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.CoinAddressAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CoinAddressActivity extends MvpActivity<CoinAddressPresenter> implements CoinAddressView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    CoinAddressAdapter adapter;
    List<CoinAddress> addressArr=new ArrayList<>();

    @Override
    protected CoinAddressPresenter createPresenter() {
        return new CoinAddressPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_coin_address;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.out_address);
        rvAddress.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new CoinAddressAdapter(addressArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=getIntent();
                intent.putExtra(Constant.COIN_ADDRESS,addressArr.get(position).getChainAddress());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(CoinAddressActivity.this,EditCoinAddressActivity.class);
                intent.putExtra(Constant.ISADD,false);
                intent.putExtra(Constant.COIN_ADDRESS,addressArr.get(position));
                startActivityForResult(intent,Constant.REQUEST_EDIT_COIN_ADDRESS);
            }
        });
        rvAddress.setAdapter(adapter);
        mvpPresenter.getCoinAddress();
    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                Intent intent=new Intent(this,EditCoinAddressActivity.class);
                intent.putExtra(Constant.ISADD,true);
                startActivityForResult(intent,Constant.REQUEST_EDIT_COIN_ADDRESS);
                break;
        }
    }

    @Override
    public void setData(List<CoinAddress> data) {
        closeLoadingDialog();
        addressArr.clear();
        if (data!=null){
            addressArr.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_EDIT_COIN_ADDRESS:
                    showLoadingDialog(null);
                    mvpPresenter.getCoinAddress();
                    break;
            }
        }
    }
}
