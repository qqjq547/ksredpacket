package com.guochuang.mimedia.ui.activity.redbag;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.base.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.mvp.model.LookVideoPBResult;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.presenter.LookVideoProblemPresenter;
import com.guochuang.mimedia.mvp.view.LookVideoProblemView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.LookSurevyAdapter;
import com.guochuang.mimedia.ui.adapter.LookVideoProblemAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LookSurevyActivity extends MvpActivity<LookVideoProblemPresenter> implements LookVideoProblemView {
    @BindView(R.id.tv_anser_number)
    TextView tvAnserNumber;
    @BindView(R.id.wrap_em_recycle)
    WrapEmptyRecyclerView wrapEmRecycle;

    LookSurevyAdapter mLookSurevyAdapter;
    List<LookSurevyResult.StatisticsListBean> mData = new ArrayList<LookSurevyResult.StatisticsListBean>();
    private LookVideoProblemPresenter mLookVideoProblemPresenter;
    private String mSurveyId;
    private String mRedPackgeId;

    @Override
    protected LookVideoProblemPresenter createPresenter() {
        return mLookVideoProblemPresenter = new LookVideoProblemPresenter(this);
    }

    @Override
    protected void paserIntent(Bundle bundle) {
        mSurveyId = bundle.getString(Constant.LOOK_PROBLEM_RED_PACKET_ID);
        mRedPackgeId = bundle.getString(Constant.RED_PACKET_ID);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_lookvideoproblem;
    }

    @Override
    public void initViewAndData() {
        initTitle();
        initData();
        wrapEmRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//


    }

    private void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("问卷红包问题").build();
    }

    private void initData() {
        mLookVideoProblemPresenter.getVideoProblemAnswerList(Long.parseLong(mSurveyId), mRedPackgeId);

    }

    @Override
    public void setError(String message) {

    }

    @Override
    public void setData(LookSurevyResult data) {

        tvAnserNumber.setText(Html.fromHtml("<font color='#ff7519'>" + data.getDrawNumber() + "</font>人回答问题"));
        mData.addAll(data.getStatisticsList());
        mLookSurevyAdapter = new LookSurevyAdapter(this, mData, R.layout.item_looksurvey_layout,data.getDrawNumber());
        mLookSurevyAdapter.setOnItmeChildren(new LookSurevyAdapter.OnItmeChildrenClick() {
            @Override
            public void itmeChildrenClick(int viewId, int position) {
                LookSurevyResult.StatisticsListBean statisticsListBean = mData.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.FILL_ANSWER_INFO,statisticsListBean);
                startActivity(FillAnswerInfoActivity.class,bundle);
            }
        });

        wrapEmRecycle.setAdapter(mLookSurevyAdapter);
        wrapEmRecycle.isShowEmptyPage();
    }

}
