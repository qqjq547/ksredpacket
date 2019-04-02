package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.base.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.mvp.presenter.LookSurveymPresenter;
import com.guochuang.mimedia.mvp.view.LookVideoProblemView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.LookSurevyAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LookSurevyActivity extends MvpActivity<LookSurveymPresenter> implements LookVideoProblemView {
    @BindView(R.id.tv_anser_number)
    TextView tvAnserNumber;
    @BindView(R.id.wrap_em_recycle)
    WrapEmptyRecyclerView wrapEmRecycle;

    LookSurevyAdapter mLookSurevyAdapter;
    List<LookSurevyResult.StatisticsListBean> mData = new ArrayList<LookSurevyResult.StatisticsListBean>();
    private LookSurveymPresenter mLookSurveymPresenter;
    private String mSurveyId;
    private String mRedPackgeId;

    @Override
    protected LookSurveymPresenter createPresenter() {
        return mLookSurveymPresenter = new LookSurveymPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_lookvideoproblem;
    }

    @Override
    public void initViewAndData() {
        mSurveyId = getIntent().getStringExtra(Constant.LOOK_PROBLEM_RED_PACKET_ID);
        mRedPackgeId = getIntent().getStringExtra(Constant.RED_PACKET_ID);
        initTitle();
        initData();
        wrapEmRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



    }

    private void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle(getString(R.string.looksurevyactivity_title)).build();
    }

    private void initData() {
        mLookSurveymPresenter.getVideoProblemAnswerList(Long.parseLong(mSurveyId), mRedPackgeId);

    }

    @Override
    public void setError(String message) {

    }

    @Override
    public void setData(LookSurevyResult data) {
        tvAnserNumber.setText(Html.fromHtml(String.format(getString(R.string.number_questions_answered),data.getDrawNumber())));
        mData.addAll(data.getStatisticsList());
        mLookSurevyAdapter = new LookSurevyAdapter(this, mData, R.layout.item_looksurvey_layout,data.getDrawNumber());
        mLookSurevyAdapter.setOnItmeChildren(new LookSurevyAdapter.OnItmeChildrenClick() {
            @Override
            public void itmeChildrenClick(int viewId, int position) {
                LookSurevyResult.StatisticsListBean statisticsListBean = mData.get(position);
                startActivity(new Intent(LookSurevyActivity.this,FillAnswerInfoActivity.class).putExtra(Constant.FILL_ANSWER_INFO,statisticsListBean));
            }
        });

        wrapEmRecycle.setAdapter(mLookSurevyAdapter);
        wrapEmRecycle.isShowEmptyPage();
    }

}
