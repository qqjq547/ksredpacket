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
import com.guochuang.mimedia.mvp.presenter.MyKsbDetailsPresenter;
import com.guochuang.mimedia.mvp.presenter.MyQCDetailsPresenter;
import com.guochuang.mimedia.mvp.view.MyKsbDetailsView;
import com.guochuang.mimedia.mvp.view.MyQCDetailsView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.KsbDetailsTypePop;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.adapter.MyKsbDetailsAdapter;
import com.guochuang.mimedia.ui.adapter.MyQCDetailsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyQCDetailsActivity extends MvpActivity<MyQCDetailsPresenter> implements MyQCDetailsView {

    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_sum)
    LinearLayout linSum;
    @BindView(R.id.tv_num_all)
    TextView tvNumAll;
    @BindView(R.id.rv_ksb)
    RecyclerView rvKsb;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    KsbDetailsTypePop ksbDetailsTypePop;
    MyQCDetailsAdapter mMyQCDetailsAdapter;
    List<SealRecord> itemArr = new ArrayList<>();
    List<DictionaryType> subjectArr = new ArrayList<>();
    List<String> subjectName = new ArrayList<>();
    String type = "100";
    String defaultIndex = "0";
    String startIndex = defaultIndex;
    String defaultCode = "";

    @Override
    protected MyQCDetailsPresenter createPresenter() {
        return new MyQCDetailsPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_qc_details;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_qc_details_title));
        tvText.setText(getResources().getString(R.string.all));
        defaultCode = getIntent().getStringExtra(Constant.DEFAULT_CODE);
        tvNumAll.setText(getPref().getString(PrefUtil.COIN, ""));
        if (mMyQCDetailsAdapter != null) {
            return;
        }
        mMyQCDetailsAdapter = new MyQCDetailsAdapter(itemArr);
        mMyQCDetailsAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
        mMyQCDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mMyQCDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(mMyQCDetailsAdapter.getData().get(position).getRemark())) {
                    return;
                }
                new DialogBuilder(MyQCDetailsActivity.this)
                        .setMessage(mMyQCDetailsAdapter.getData().get(position).getRemark())
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).create().show();
            }
        });
        mMyQCDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
        mMyQCDetailsAdapter.setEmptyView(emptyView);
        rvKsb.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvKsb.setItemAnimator(new DefaultItemAnimator());
        rvKsb.setAdapter(mMyQCDetailsAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getQCDetail(type, startIndex, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex = defaultIndex;
                mvpPresenter.getQCDetail(type, startIndex, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getSubject(Constant.TYPE_QC_TYPE);
    }

    @OnClick({R.id.iv_back, R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (ksbDetailsTypePop == null) {
                    ksbDetailsTypePop = new KsbDetailsTypePop(this, subjectName, new KsbDetailsTypePop.OnSelectItemListener() {
                        @Override
                        public void onSelectItem(int position) {
                            String selectType = subjectArr.get(position).getCode();
                            if (!TextUtils.equals(selectType, type)) {
                                tvText.setText(subjectName.get(position));
                                type = selectType;
                                itemArr.clear();
                                mMyQCDetailsAdapter.notifyDataSetChanged();
                                startIndex = defaultIndex;
                                mvpPresenter.getQCDetail(type, startIndex, Constant.PAGE_SIZE);
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
    public void setSubject(List<DictionaryType> data) {
        if (data != null && data.size() > 0) {
            subjectArr.addAll(data);
            for (int i = 0; i < subjectArr.size(); i++) {
                subjectName.add(subjectArr.get(i).getName());
                if (TextUtils.equals(defaultCode, subjectArr.get(i).getCode())) {
                    type = data.get(i).getCode();
                    tvText.setText(subjectArr.get(i).getName());
                    mvpPresenter.getQCDetail(type, startIndex, Constant.PAGE_SIZE);
                }
            }
            if (TextUtils.isEmpty(defaultCode)) {
                type = data.get(0).getCode();
                mvpPresenter.getQCDetail(type, startIndex, Constant.PAGE_SIZE);
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

    @Override
    public void setQCDetail(List<SealRecord> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (startIndex.equals(defaultIndex)) {
            itemArr.clear();
        }
        if (data == null || data.size() < Constant.PAGE_SIZE) {
            startIndex = defaultIndex;
            srlRefresh.setEnableLoadmore(false);
            itemArr.addAll(data);
        } else {
            startIndex = data.get(data.size() - 1).getStartIndex();
            itemArr.addAll(data);
            srlRefresh.setEnableLoadmore(true);
        }
        mMyQCDetailsAdapter.notifyDataSetChanged();
    }
}
