package com.guochuang.mimedia.ui.activity.city;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MallBidRecord;
import com.guochuang.mimedia.mvp.model.MallNodeResult;
import com.guochuang.mimedia.mvp.model.MallRegionDetail;
import com.guochuang.mimedia.mvp.model.MyMallRecord;
import com.guochuang.mimedia.mvp.model.MyMallStat;
import com.guochuang.mimedia.mvp.presenter.MyMallPresenter;
import com.guochuang.mimedia.mvp.view.MyMallView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.MyMallAdapter;
import com.guochuang.mimedia.ui.dialog.BidNodeDialog;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMallActivity extends MvpActivity<MyMallPresenter> implements MyMallView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_bid_num)
    TextView tvBidNum;
    @BindView(R.id.tv_total_bid_qc)
    TextView tvTotalBidQc;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    int curPage=1;
    int pageSize=10;

    List<MyMallRecord> dataArr=new ArrayList<>();
    MyMallAdapter adapter;
    @Override
    protected MyMallPresenter createPresenter() {
        return new MyMallPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_mall;
    }

    @Override
    public void initViewAndData() {
        adapter=new MyMallAdapter(dataArr);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final MyMallRecord record=dataArr.get(position);
                switch (view.getId()){
                    case R.id.tv_add:
                        BidNodeDialog bidNodeDialog=new BidNodeDialog(MyMallActivity.this);
                        bidNodeDialog.setOnResultListener(new BidNodeDialog.OnResultListener() {
                            @Override
                            public void onResult(int money) {
                                showLoadingDialog(null);
                                mvpPresenter.mallRegion(record.getRegionId());
                            }
                        });
                        bidNodeDialog.show();
                        break;
                    case R.id.tv_add_result:
                        mvpPresenter.getMallNodeOrderList(record.getRegionId(),record.getNodeNumber());
                        break;
                    case R.id.tv_result:
                        mvpPresenter.getMyNodeResult(record.getRegionId(),record.getNodeNumber());
                        break;
                }
            }
        });

        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        rvRecord.setAdapter(adapter);
        mvpPresenter.getMyMallStat();
        mvpPresenter.getMyMallList(curPage,pageSize);

    }

    @Override
    public void setMyStat(MyMallStat data) {
        closeLoadingDialog();
        if(data!=null){
            tvBidNum.setText(String.valueOf(data.getNodeNum()));
            tvTotalBidQc.setText(String.valueOf(data.getTotalQc()));
        }
    }

    @Override
    public void setList(Page<MyMallRecord> data) {
        closeLoadingDialog();
       if (data!=null){
           dataArr.addAll(data.getDataList());
       }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setMallNodeResult(MallNodeResult data) {

    }

    @Override
    public void setMallNodeOrderst(List<MallBidRecord> data) {

    }

    @Override
    public void setMallRegionDetail(MallRegionDetail data) {
        if (data!=null){
            int purchaseType=Constant.TYPE_PURCHASE_MALLNODE;
            if (data.getMinQc()>=data.getMaxQc()){
                showShortToast("已达到竞购最大金额");
                return;
            }
            int nextPrice=data.getMinQc()+data.getStepPrice();
            String money=String.valueOf(nextPrice);
            int bidType=2;
            long mallRegionId=data.getId();
            int nodeNumber=2;
            IntentUtils.startPurchaseActivity(MyMallActivity.this,purchaseType,money,bidType,mallRegionId,nodeNumber);
        }
    }

    @Override
    public void setError(String msg) {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
