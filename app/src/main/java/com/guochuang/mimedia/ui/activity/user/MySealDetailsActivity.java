package com.guochuang.mimedia.ui.activity.user;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;
import com.guochuang.mimedia.mvp.presenter.MySealDetailsPresenter;
import com.guochuang.mimedia.mvp.view.MySealDetailsView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.KsbDetailsTypePop;
import com.guochuang.mimedia.ui.adapter.MyKsbDetailsAdapter;
import com.guochuang.mimedia.ui.adapter.MySealDetailsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MySealDetailsActivity extends MvpActivity<MySealDetailsPresenter> implements MySealDetailsView {

    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_ksb)
    RecyclerView rvKsb;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    KsbDetailsTypePop ksbDetailsTypePop;
    MySealDetailsAdapter mySealDetailsAdapter;
    List<SealRecord> itemArr=new ArrayList<>();
    List<DictionaryType> subjectArr=new ArrayList<>();
    List<String> subjectName=new ArrayList<>();
    String type="100";
    String defaultIndex="0";
    String startIndex=defaultIndex;
    String defaultCode ="";
    private final String coinType="2";
    @Override
    protected MySealDetailsPresenter createPresenter() {
        return new MySealDetailsPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_seal_details;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_seal_details_title));
        tvText.setText(getResources().getString(R.string.all));
        defaultCode =getIntent().getStringExtra(Constant.DEFAULT_CODE);
        if (mySealDetailsAdapter != null) {
            return;
        }
        mySealDetailsAdapter = new MySealDetailsAdapter(itemArr);
        mySealDetailsAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        mySealDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mySealDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(mySealDetailsAdapter.getData().get(position).getRemark())){
                    return;
                }
                new DialogBuilder(MySealDetailsActivity.this)
                        .setMessage(mySealDetailsAdapter.getData().get(position).getRemark())
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).create().show();
            }
        });
        mySealDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        View emptyView=getLayoutInflater().inflate(R.layout.layout_empty,null);
        mySealDetailsAdapter.setEmptyView(emptyView);
        rvKsb.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvKsb.setItemAnimator(new DefaultItemAnimator());
        rvKsb.setAdapter(mySealDetailsAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getCoinRecord(type,coinType,startIndex,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex=defaultIndex;
                mvpPresenter.getCoinRecord(type,coinType,startIndex,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getSubject(Constant.TYPE_SEAL_TYPE);
    }

    @OnClick({R.id.iv_back, R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (ksbDetailsTypePop == null) {
                    ksbDetailsTypePop = new KsbDetailsTypePop(this,subjectName, new KsbDetailsTypePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(int position) {
                            String selectType=subjectArr.get(position).getCode();
                            if (!TextUtils.equals(selectType,type)){
                                tvText.setText(subjectName.get(position));
                                type=selectType;
                                itemArr.clear();
                                mySealDetailsAdapter.notifyDataSetChanged();
                                startIndex=defaultIndex;
                                mvpPresenter.getCoinRecord(type,coinType,startIndex,Constant.PAGE_SIZE);
                            }
                        }
                    });
                }
                if (ksbDetailsTypePop.isShowing()) {
                    ksbDetailsTypePop.dismiss();
                } else {
                    ksbDetailsTypePop.showAsDropDown(linTitle);
                }
                break;
        }
    }

    @Override
    public void setData(List<SealRecord> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (startIndex.equals(defaultIndex)){
            itemArr.clear();
        }
        if (data==null||data.size()<Constant.PAGE_SIZE){
            startIndex=defaultIndex;
            srlRefresh.setEnableLoadmore(false);
            itemArr.addAll(data);
        }else {
            startIndex = data.get(data.size()-1).getStartIndex();
            itemArr.addAll(data);
            srlRefresh.setEnableLoadmore(true);
        }
        mySealDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSubject(List<DictionaryType> data) {
        if (data!=null&&data.size()>0){
            subjectArr.addAll(data);
            for (int i=0;i<subjectArr.size();i++){
                subjectName.add(subjectArr.get(i).getName());
                if (TextUtils.equals(defaultCode,subjectArr.get(i).getCode())){
                    type = data.get(i).getCode();
                    tvText.setText(subjectArr.get(i).getName());
                    mvpPresenter.getCoinRecord(type, coinType,startIndex, Constant.PAGE_SIZE);
                }
            }
            if (TextUtils.isEmpty(defaultCode)) {
                type = data.get(0).getCode();
                mvpPresenter.getCoinRecord(type, coinType,startIndex, Constant.PAGE_SIZE);
            }
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
