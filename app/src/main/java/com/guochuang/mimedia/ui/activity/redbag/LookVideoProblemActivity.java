package com.guochuang.mimedia.ui.activity.redbag;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.view.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.view.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.presenter.LookVideoProblemPresenter;
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
public class LookVideoProblemActivity extends MvpActivity<LookVideoProblemPresenter> implements LookView {
    @BindView(R.id.wrap_em_recycle)
    WrapEmptyRecyclerView mWrapEmRecycle;
    @BindView(R.id.tv_answer_number)
    TextView mTvAnswerNumber;
    LookVideoProblemAdapter mLookVideoProblemAdapter;
    List<LookVideoResult.QuestionListBean> mData = new ArrayList<>();
    private LookVideoProblemPresenter mLookVideoProblemPresenter;
    private long mSurveyId;
    private String mRedPackgeId;


    @Override
    protected LookVideoProblemPresenter createPresenter() {
        mLookVideoProblemPresenter = new LookVideoProblemPresenter(this);
        return mLookVideoProblemPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_looksurvey;
    }


    @Override
    public void initViewAndData() {
        mSurveyId = getIntent().getLongExtra(Constant.SURVEYID,0);
        mRedPackgeId = getIntent().getStringExtra(Constant.RED_PACKET_ID);
        initTitle();
        initData();
        mWrapEmRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLookVideoProblemAdapter = new LookVideoProblemAdapter(this, mData, R.layout.item_lookvideoproblem_layout);

        mWrapEmRecycle.setAdapter(mLookVideoProblemAdapter);
        mWrapEmRecycle.isShowEmptyPage();

    }

    private void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle(getResources().getString(R.string.video_redbag_problem)).build();
    }

    /**
     * 请求数据
     */
    private void initData() {

        mLookVideoProblemPresenter.getProblems(mSurveyId, mRedPackgeId);

    }

    @Override
    public void setData(LookVideoResult data) {
        mTvAnswerNumber.setText(Html.fromHtml(String.format(getString(R.string.answer_ok_number), data.getDrawNumber())));

        mData.addAll(data.getQuestionList());

        mLookVideoProblemAdapter.notifyDataSetChanged();

        mWrapEmRecycle.isShowEmptyPage();
    }

    @Override
    public void setError(String message) {

    }
}
