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
import com.guochuang.mimedia.mvp.view.AnswerView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
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
    String redPacketType;


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
        mvpPresenter.getRemain(redPacketUuid);

    }

    @OnClick({R.id.iv_back, R.id.btn_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_open:
                if (dataArr.size()>0){
                    ArrayList<AnswerFrom.AnswerAddListBean> answerList=new ArrayList<>();
                    for (LookVideoResult.QuestionListBean listBean:dataArr) {
                        AnswerFrom.AnswerAddListBean answerItem=new AnswerFrom.AnswerAddListBean();
                        LogUtil.object(listBean.getOptionsList());
                        List<String> optId = new ArrayList<>();
                        List<String> optName = new ArrayList<>();
                        List<String> optValue = new ArrayList<>();
                        if (listBean.getType() == 2) {//填空题
                            if (listBean.getOptionsList() != null && listBean.getOptionsList().size() > 0) {
                                LookVideoResult.QuestionListBean.OptionsListBean bean = listBean.getOptionsList().get(0);
                                if (bean.isSelect()) {
                                    optId.add(String.valueOf(bean.getOptionId()));
                                    optName.add(bean.getOptionName());
                                    optValue.add(bean.getOptionValue());
                                }
                            }
                        } else {
                            for (LookVideoResult.QuestionListBean.OptionsListBean bean : listBean.getOptionsList()) {
                                if (bean.isSelect()) {
                                    optId.add(String.valueOf(bean.getOptionId()));
                                    optName.add(bean.getOptionName());
                                    optValue.add(bean.getOptionValue());
                                }
                            }
                        }

                        if (optName.size() > 0) {
                            answerItem.setSourceId(redPacketUuid);
                            answerItem.setSurveyId(String.valueOf(surveyId));
                            answerItem.setQuestionId(String.valueOf(listBean.getQuestionId()));
                            answerItem.setOptionId(TextUtils.join(",", optId));
                            answerItem.setOptionName(TextUtils.join(",", optName));
                            answerItem.setOptionValue(TextUtils.join(",", optValue));
                            answerList.add(answerItem);
                        }
                        else {
                        showShortToast(R.string.has_not_answer);
                        return;
                         }
                        }
                            showLoadingDialog(null);
                            AnswerFrom answerFrom=new AnswerFrom();
                            answerFrom.setRedPacketUuid(redPacketUuid);
                            answerFrom.setLatitude(getPref().getLatitude());
                            answerFrom.setLongitude(getPref().getLongitude());
                            answerFrom.setAnswerAddList(answerList);
                            if (TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_VIDEO)){
                                mvpPresenter.videoSubmit(answerFrom);
                            }else {
                                mvpPresenter.surveySubmit(answerFrom);
                            }
                    }else {
                    showShortToast(R.string.cannot_get_question);
                }
                break;
        }
    }

    @Override
    public void setRemain(Integer data) {
        if (data!=null){
            int remain=data.intValue();
            if (remain>0){
                showLoadingDialog(null);
                mvpPresenter.getProblems(surveyId,redPacketUuid);
            }else {
                closeLoadingDialog();
                tvNotice.setVisibility(View.VISIBLE);
                showShortToast(R.string.redbag_has_robe);
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
       closeLoadingDialog();
       if (data!=null){
           if (data.isDrawSuccess()){
               showShortToast(R.string.answer_success_open_redbag);
               Intent intent=getIntent();
               intent.putExtra(Constant.RED_PACKET_DETAIL,data);
               setResult(RESULT_OK,intent);
               finish();
           }else {
               showShortToast(data.getReason());
           }
       }
    }

    @Override
    public void setError(String msg) {
       closeLoadingDialog();
       showShortToast(msg);
    }
}
