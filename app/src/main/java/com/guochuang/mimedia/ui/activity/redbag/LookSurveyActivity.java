package com.guochuang.mimedia.ui.activity.redbag;

import android.support.v7.widget.LinearLayoutManager;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.base.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.mvp.presenter.LookSurveyPresenter;
import com.guochuang.mimedia.mvp.view.LookView;
import com.guochuang.mimedia.ui.adapter.LookSurveyAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查看问卷问题界面
 */
public class LookSurveyActivity extends MvpActivity<LookSurveyPresenter> implements LookView {
    @BindView(R.id.wrap_em_recycle)
    WrapEmptyRecyclerView mWrapEmRecycle;
    LookSurveyAdapter mLookSurveyAdapter;
    List<String> mData = new ArrayList<>();
    private LookSurveyPresenter mLookSurveyPresenter;

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
    public void initViewAndData() {
        initData();
        mWrapEmRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLookSurveyAdapter = new LookSurveyAdapter(this, mData, new MultiTypeSupport<String>() {
            @Override
            public int getLayoutId(String item, int position) {
                return 0;
            }
        });

        mWrapEmRecycle.setAdapter(mLookSurveyAdapter);
        mWrapEmRecycle.isShowEmptyPage();

    }

    /**
     * 请求数据
     */
    private void initData() {
        mLookSurveyPresenter.getProblems();


    }

}
