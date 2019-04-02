package com.guochuang.mimedia.ui.activity.redbag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.view.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.view.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.FillAnswerInfoAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;

import butterknife.BindView;

public class FillAnswerInfoActivity extends MvpActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycle_list)
    WrapEmptyRecyclerView recycleList;
    private LookSurevyResult.StatisticsListBean mLookSurevyStatisticsList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public int getLayout() {
        return R.layout.activity_fillanswerinfo;
    }

    @Override
    public void initViewAndData() {
        mLookSurevyStatisticsList = getIntent().getParcelableExtra(Constant.FILL_ANSWER_INFO);
        initTitle();

        tvTitle.setText(getString(R.string.fill_answer) + mLookSurevyStatisticsList.getTitle());

        recycleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<LookSurevyResult.StatisticsListBean.OptionsListBean> options = new ArrayList<>();
        if(mLookSurevyStatisticsList.getOptionsList() != null) {
            options.addAll(mLookSurevyStatisticsList.getOptionsList());
        }


        FillAnswerInfoAdapter fillAnswerInfoAdapter = new FillAnswerInfoAdapter(this, options,R.layout.item_fillanswerinfo_layout);

        recycleList.setAdapter(fillAnswerInfoAdapter);

        recycleList.isShowEmptyPage();
    }


    private void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle(getString(R.string.look_survey_fill_activity_title)).build();

    }

}
