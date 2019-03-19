package com.guochuang.mimedia.ui.activity.redbag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.presenter.AnswerPresenter;
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.AddressAdapter;
import com.guochuang.mimedia.ui.adapter.AnswerAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerActivity extends MvpActivity<AnswerPresenter> implements AnswerView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;
    @BindView(R.id.btn_open)
    Button btnOpen;

    List<LookVideoResult.QuestionListBean> dataArr=new ArrayList<>();
    AnswerAdapter adapter;
    String redPacketUuid="";
    long surveyId=0;

    @Override
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.video_redbag);
        redPacketUuid=getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        surveyId=getIntent().getLongExtra(Constant.SURVEYID,0);
        rvAnswer.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new AnswerAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAnswer.setAdapter(adapter);
        showLoadingDialog(null);
        mvpPresenter.getRemain(redPacketUuid);
        mvpPresenter.getProblems(surveyId,redPacketUuid);
    }

    @OnClick({R.id.iv_back, R.id.btn_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_open:
//                List<AnswerAdapter.ItemAnswer> data=adapter.getResult();
//                for (String item:dataArr){
////                    data.
//                }
                break;
        }
    }

    @Override
    public void setRemain(Integer data) {
        if (data!=null){
            int remain=data.intValue();
            if (remain>0){
                showLoadingDialog(null);
            }else {
                tvNotice.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void setData(LookVideoResult data) {
        closeLoadingDialog();
        if (data!=null){
           dataArr.addAll(data.getQuestionList());
           adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setRedbagDetail(RedbagDetail data) {

    }

    @Override
    public void setError(String msg) {
       closeLoadingDialog();
       showShortToast(msg);
    }
}
