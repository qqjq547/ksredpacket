package com.guochuang.mimedia.ui.activity;

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
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.presenter.MyKsbDetailsPresenter;
import com.guochuang.mimedia.mvp.view.MyKsbDetailsView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.KsbDetailsTypePop;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.adapter.MyKsbDetailsAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyKsbDetailsActivity extends MvpActivity<MyKsbDetailsPresenter> implements MyKsbDetailsView {

    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_num_all)
    TextView tvNumAll;
    @BindView(R.id.rv_ksb)
    RecyclerView rvKsb;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    KsbDetailsTypePop ksbDetailsTypePop;
    MyKsbDetailsAdapter myKsbDetailsAdapter;
    List<KsbRecord> itemArr=new ArrayList<>();
    List<DictionaryType> subjectArr=new ArrayList<>();
    List<String> subjectName=new ArrayList<>();
    String type="00";
    String defaultIndex="0";
    String startIndex=defaultIndex;

    @Override
    protected MyKsbDetailsPresenter createPresenter() {
        return new MyKsbDetailsPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ksb_details;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_ksb_details_title));
        tvText.setText(getResources().getString(R.string.all));
        tvNumAll.setText(getPref().getString(PrefUtil.COIN,""));
        if (myKsbDetailsAdapter != null) {
            return;
        }
        myKsbDetailsAdapter = new MyKsbDetailsAdapter(itemArr);
        myKsbDetailsAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        myKsbDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        myKsbDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        myKsbDetailsAdapter.openLoadAnimation();
        View emptyView=getLayoutInflater().inflate(R.layout.layout_empty,null);
        myKsbDetailsAdapter.setEmptyView(emptyView);
        rvKsb.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvKsb.setItemAnimator(new DefaultItemAnimator());
        rvKsb.setAdapter(myKsbDetailsAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getKsbRecord(type,startIndex,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex=defaultIndex;
                mvpPresenter.getKsbRecord(type,startIndex,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getSubject(Constant.TYPE_KSB_TYPE);
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
                                myKsbDetailsAdapter.notifyDataSetChanged();
                                startIndex=defaultIndex;
                                mvpPresenter.getKsbRecord(type,startIndex,Constant.PAGE_SIZE);
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
    public void setData(List<KsbRecord> data) {
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
        myKsbDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSubject(List<DictionaryType> data) {
        if (data!=null&&data.size()>0){
            subjectArr.addAll(data);
            for (int i=0;i<subjectArr.size();i++){
                subjectName.add(subjectArr.get(i).getName());
            }
            type=data.get(0).getCode();
            mvpPresenter.getKsbRecord(type,startIndex,Constant.PAGE_SIZE);
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
