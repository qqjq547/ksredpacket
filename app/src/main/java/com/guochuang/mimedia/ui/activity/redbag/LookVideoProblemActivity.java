package com.guochuang.mimedia.ui.activity.redbag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.base.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.presenter.LookSurveyPresenter;
import com.guochuang.mimedia.mvp.view.LookView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.LookVideoProblemAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查看问卷问题界面
 * 类型 0：单选 1：多选 2：填空题
 */
public class LookVideoProblemActivity extends MvpActivity<LookSurveyPresenter> implements LookView {
    @BindView(R.id.wrap_em_recycle)
    WrapEmptyRecyclerView mWrapEmRecycle;
    @BindView(R.id.tv_answer_number)
    TextView mTvAnswerNumber;
    LookVideoProblemAdapter mLookVideoProblemAdapter;
    List<LookVideoResult.QuestionListBean> mData = new ArrayList<>();
    private LookSurveyPresenter mLookSurveyPresenter;
    private String mSurveyId;
    private String mRedPackgeId;


    @Override
    protected LookSurveyPresenter createPresenter() {
        mLookSurveyPresenter = new LookSurveyPresenter(this);
        return mLookSurveyPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_looksurvey;
    }

    @Override
    protected void paserIntent(Bundle bundle) {
        mSurveyId = bundle.getString(Constant.LOOK_PROBLEM_RED_PACKET_ID);
        mRedPackgeId = bundle.getString(Constant.RED_PACKET_ID);

    }


    @Override
    public void initViewAndData() {
        initTitle();
        initData();
        mWrapEmRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLookVideoProblemAdapter = new LookVideoProblemAdapter(this, mData, R.layout.item_lookvideoproblem_layout);

        mWrapEmRecycle.setAdapter(mLookVideoProblemAdapter);
        mWrapEmRecycle.isShowEmptyPage();

    }

    private void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("视频红包问题").build();
    }

    /**
     * 请求数据
     */
    private void initData() {

        mLookSurveyPresenter.getProblems(Long.parseLong(mSurveyId), mRedPackgeId);

    }

    @Override
    public void setData(LookVideoResult data) {
        mTvAnswerNumber.setText(Html.fromHtml("<font color='#ff7519'>" + data.getDrawNumber() + "</font>人回答正确"));

        mData.addAll(data.getQuestionList());

        mLookVideoProblemAdapter.notifyDataSetChanged();

        mWrapEmRecycle.isShowEmptyPage();
    }

    @Override
    public void setError(String message) {

    }
}