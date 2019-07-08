package com.guochuang.mimedia.ui.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.presenter.RecommendDetailPresenter;
import com.guochuang.mimedia.mvp.view.RecommendDetailView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.RecommendDetailAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecommendDetailActivity extends MvpActivity<RecommendDetailPresenter> implements RecommendDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_agent_count)
    TextView tvAgentCount;
    @BindView(R.id.lin_agent)
    LinearLayout linAgent;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;
    @BindView(R.id.tv_undirect_count)
    TextView tvUndirectCount;
    @BindView(R.id.lin_undirect)
    LinearLayout linUndirect;
    @BindView(R.id.lin_direct_agent)
    LinearLayout linDirectAgent;
    @BindView(R.id.lin_direct_fans)
    LinearLayout linDirectFans;
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
//    @BindView(R.id.iv_arrow)
//    ImageView ivArrow;
    @BindView(R.id.tv_head_time)
    TextView tvHeadTime;

    List<RecommedUser> itemArr=new ArrayList<>();
    RecommendDetailAdapter adapter;
    int curTab=-1;
    int curPage=1;
    RecommendData data;
    String sort=Constant.SORT_DEFAULT;


    @Override
    protected RecommendDetailPresenter createPresenter() {
        return new RecommendDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_recommend_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.recommend_detail);
        data= (RecommendData)getIntent().getSerializableExtra(Constant.RECOMMENDDATA);
        boolean isAgent=getIntent().getBooleanExtra(Constant.ISAGENT,false);
        if (data!=null) {
            tvAgentCount.setText(String.valueOf(data.getDirectAgent()));
            tvFans.setText(String.valueOf(data.getDirectUser()));
        }
        rvDetail.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvDetail.addItemDecoration(new VerticalDecoration(this));
        adapter=new RecommendDetailAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvDetail.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (curTab==0) {
                    mvpPresenter.getDirectAgent(curPage + 1, Constant.PAGE_SIZE,sort);
                }else {
                    mvpPresenter.getDirectFans(curPage + 1, Constant.PAGE_SIZE,sort);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (curTab==0) {
                    mvpPresenter.getDirectAgent(1, Constant.PAGE_SIZE,sort);
                }else {
                    mvpPresenter.getDirectFans(1, Constant.PAGE_SIZE,sort);
                }
            }
        });
        setSelected(isAgent?0:1);
        mvpPresenter.getMarketCount();
    }


    @OnClick({R.id.iv_back, R.id.lin_agent, R.id.lin_fans, R.id.lin_undirect, R.id.lin_direct_agent, R.id.lin_direct_fans,R.id.lin_benefit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_agent:
                break;
            case R.id.lin_fans:
                break;
            case R.id.lin_undirect:
                break;
            case R.id.lin_direct_agent:
                setSelected(0);
                break;
            case R.id.lin_direct_fans:
                setSelected(1);
                break;
            case R.id.lin_benefit:
//                switch (sort){
//                    case Constant.SORT_DEFAULT:
//                        sort=Constant.SORT_DESC;
//                        ivArrow.setImageResource(R.drawable.ic_arrow_benefit_down);
//                        break;
//                    case Constant.SORT_DESC:
//                        sort=Constant.SORT_ASC;
//                        ivArrow.setImageResource(R.drawable.ic_arrow_benefit_up);
//                        break;
//                    case Constant.SORT_ASC:
//                        sort=Constant.SORT_DEFAULT;
//                        ivArrow.setImageResource(R.drawable.ic_arrow_benefit);
//                        break;
//                }
                if (curTab==0) {
                    mvpPresenter.getDirectAgent(1, Constant.PAGE_SIZE,sort);
                }else {
                    mvpPresenter.getDirectFans(1, Constant.PAGE_SIZE,sort);
                }
                break;
        }
    }
    public void setSelected(int pos){
        if (curTab==pos){
            return;
        }
        curTab=pos;
        if (pos==0){
            linDirectAgent.setSelected(true);
            linDirectFans.setSelected(false);
            tvHeadTime.setText(R.string.upgrade_time);
        }else {
            linDirectAgent.setSelected(false);
            linDirectFans.setSelected(true);
            tvHeadTime.setText(R.string.register_time);
        }
        if (curTab==0) {
            mvpPresenter.getDirectAgent(1, Constant.PAGE_SIZE,sort);
        }else {
            mvpPresenter.getDirectFans(1, Constant.PAGE_SIZE,sort);
        }
    }

    @Override
    public void setMarketCount(String data) {
        if (data!=null){
            tvUndirectCount.setText(data);
        }
    }

    @Override
    public void setRecommendUser(Page<RecommedUser> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (data!=null) {
            curPage = data.getCurrentPage();
            if (curPage == 1) {
                itemArr.clear();
            }
            if (data.getDataList() != null) {
                itemArr.addAll(data.getDataList());
            }
            adapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
       showShortToast(msg);
    }
}
