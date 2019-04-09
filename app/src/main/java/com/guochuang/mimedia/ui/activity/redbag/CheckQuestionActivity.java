package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.AnswerFrom;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.presenter.AnswerPresenter;
import com.guochuang.mimedia.mvp.presenter.CheckQuestionPresenter;
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.mvp.view.CheckQuestionView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.adapter.AnswerAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckQuestionActivity extends MvpActivity<CheckQuestionPresenter> implements CheckQuestionView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;

    List<LookVideoResult.QuestionListBean> dataArr=new ArrayList<>();
    AnswerAdapter adapter;
    String redPacketUuid="";
    long surveyId=0;
    String redPacketType;


    @Override
    protected CheckQuestionPresenter createPresenter() {
        return new CheckQuestionPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_check_question;
    }

    @Override
    public void initViewAndData() {
        redPacketUuid=getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        surveyId=getIntent().getLongExtra(Constant.SURVEYID,0);
        redPacketType=getIntent().getStringExtra(Constant.RED_PACKET_TYPE);
        if (TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_VIDEO)) {
            tvTitle.setText(R.string.video_redbag);
        }else {
            tvTitle.setText(R.string.questionnaire_redbag);
        }
        rvAnswer.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new AnswerAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAnswer.setAdapter(adapter);
        showLoadingDialog(null);
        mvpPresenter.getQuestion(surveyId,redPacketUuid);


    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
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
    public void setError(String msg) {
       closeLoadingDialog();
       showShortToast(msg);
    }
}
