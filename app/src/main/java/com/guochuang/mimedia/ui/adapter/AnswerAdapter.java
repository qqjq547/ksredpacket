package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends BaseQuickAdapter<LookVideoResult.QuestionListBean,BaseViewHolder> {
    List<ItemAnswer> listResult=new ArrayList<>();
    public AnswerAdapter(@Nullable List<LookVideoResult.QuestionListBean> data) {
        super(R.layout.item_answer,data);
        for (int i=0;i<data.size();i++){
            listResult.add(new ItemAnswer());
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, LookVideoResult.QuestionListBean item) {
        helper.setText(R.id.tv_index,helper.getAdapterPosition()+".");
        switch (item.getType()){
            case 0://单选题
                helper.setGone(R.id.lin_direct_answer,true);
                helper.setGone(R.id.rgroup_answer,false);
                helper.setGone(R.id.lin_check,false);
                helper.setText(R.id.tv_question,mContext.getString(R.string.input_blank)+item.getTitle());
                break;
            case 1://多选题
                helper.setGone(R.id.lin_direct_answer,false);
                helper.setGone(R.id.rgroup_answer,true);
                helper.setGone(R.id.lin_check,false);
                helper.setText(R.id.tv_question,mContext.getString(R.string.single_choice)+item.getTitle());
                RadioGroup group=helper.getView(R.id.rgroup_answer);
                group.setOnCheckedChangeListener(new OnCheckListener(helper.getAdapterPosition()));
                break;
            case 2://填空题
                helper.setGone(R.id.lin_direct_answer,false);
                helper.setGone(R.id.rgroup_answer,false);
                helper.setGone(R.id.lin_check,true);
                helper.setText(R.id.tv_question,mContext.getString(R.string.muti_choice)+item.getTitle());
                OnCheckBoxListener listener=new OnCheckBoxListener(helper.getAdapterPosition());
                ((CheckBox)helper.getView(R.id.cb_one)).setOnCheckedChangeListener(listener);
                ((CheckBox)helper.getView(R.id.cb_two)).setOnCheckedChangeListener(listener);
                ((CheckBox)helper.getView(R.id.cb_three)).setOnCheckedChangeListener(listener);
                ((CheckBox)helper.getView(R.id.cb_four)).setOnCheckedChangeListener(listener);
                break;
        }
    }
    class OnCheckListener implements RadioGroup.OnCheckedChangeListener {
        int positon=0;
       public OnCheckListener(int positon){
           this.positon=positon;
       }
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            ItemAnswer answer=listResult.get(positon);
            switch (i){
                case R.id.rbtn_one:
                    answer.setSingleAnswer(0);
                    break;
                case R.id.rbtn_two:
                    answer.setSingleAnswer(1);
                    break;
                case R.id.rbtn_three:
                    answer.setSingleAnswer(2);
                    break;
                case R.id.rbtn_four:
                    answer.setSingleAnswer(3);
                    break;
            }

        }
    }
    public List<ItemAnswer> getResult(){
        return listResult;
    }
    class OnCheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        int positon=0;
        public OnCheckBoxListener(int positon){
            this.positon=positon;
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            ItemAnswer answer=listResult.get(positon);
            switch (compoundButton.getId()){
                case R.id.cb_one:
                    if (!answer.getMutiAnswer().contains(0)){
                        answer.getMutiAnswer().add(0);
                    }
                    break;
                case R.id.cb_two:
                    if (!answer.getMutiAnswer().contains(1)){
                        answer.getMutiAnswer().add(1);
                    }
                    break;
                case R.id.cb_three:
                    if (!answer.getMutiAnswer().contains(2)){
                        answer.getMutiAnswer().add(2);
                    }
                    break;
                case R.id.cb_four:
                    if (!answer.getMutiAnswer().contains(3)){
                        answer.getMutiAnswer().add(3);
                    }
                    break;
            }

        }
    }
    public  class ItemAnswer{
        String textAnswer;
        int singleAnswer;
        List<Integer> mutiAnswer=new ArrayList<>();

        public String getTextAnswer() {
            return textAnswer;
        }

        public void setTextAnswer(String textAnswer) {
            this.textAnswer = textAnswer;
        }

        public int getSingleAnswer() {
            return singleAnswer;
        }

        public void setSingleAnswer(int singleAnswer) {
            this.singleAnswer = singleAnswer;
        }

        public List<Integer> getMutiAnswer() {
            return mutiAnswer;
        }

        public void setMutiAnswer(List<Integer> mutiAnswer) {
            this.mutiAnswer = mutiAnswer;
        }
    }
}
