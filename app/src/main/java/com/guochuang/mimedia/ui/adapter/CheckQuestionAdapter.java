package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

public class CheckQuestionAdapter extends BaseQuickAdapter<LookVideoResult.QuestionListBean,BaseViewHolder> {
    boolean showAns=false;
    public CheckQuestionAdapter(@Nullable List<LookVideoResult.QuestionListBean> data) {
        super(R.layout.item_answer,data);
    }

    public void setShowAns(boolean showAns) {
        this.showAns = showAns;
    }

    @Override
    protected void convert(final BaseViewHolder helper, LookVideoResult.QuestionListBean item) {
        helper.setText(R.id.tv_index,(helper.getAdapterPosition()+1)+".");
        switch (item.getType()){
            case 0://单选题
                helper.setGone(R.id.lin_direct_answer,false);
                helper.setGone(R.id.rgroup_answer,true);
                helper.setGone(R.id.lin_check,false);
                helper.setText(R.id.tv_question,mContext.getString(R.string.single_choice)+item.getTitle());
                RadioGroup group=helper.getView(R.id.rgroup_answer);
                List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr=item.getOptionsList();
                for (int i=0;i<optionArr.size();i++){
                    LookVideoResult.QuestionListBean.OptionsListBean bean=optionArr.get(i);
                    RadioButton rbtn=((RadioButton)group.getChildAt(i));
                    rbtn.setText(bean.getOptionName()+"."+bean.getOptionValue());
                    rbtn.setVisibility(View.VISIBLE);
                    rbtn.setChecked(bean.getIsAnswer()==1);
                    rbtn.setEnabled(false);
                    if (!showAns){
                        rbtn.setButtonDrawable(null);
                    }
                }
                break;
            case 1://多选题
                helper.setGone(R.id.lin_direct_answer,false);
                helper.setGone(R.id.rgroup_answer,false);
                helper.setGone(R.id.lin_check,true);
                helper.setText(R.id.tv_question,mContext.getString(R.string.muti_choice)+item.getTitle());
                List<CheckBox> checkBoxList=new ArrayList<>();
                checkBoxList.add(((CheckBox)helper.getView(R.id.cb_one)));
                checkBoxList.add(((CheckBox)helper.getView(R.id.cb_two)));
                checkBoxList.add(((CheckBox)helper.getView(R.id.cb_three)));
                checkBoxList.add(((CheckBox)helper.getView(R.id.cb_four)));
                List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr1=item.getOptionsList();
                for (int i=0;i<optionArr1.size();i++){
                    LookVideoResult.QuestionListBean.OptionsListBean bean=optionArr1.get(i);
                    checkBoxList.get(i).setText(bean.getOptionName()+"."+bean.getOptionValue());
                    checkBoxList.get(i).setVisibility(View.VISIBLE);
                    checkBoxList.get(i).setChecked(bean.getIsAnswer()==1);
                    checkBoxList.get(i).setEnabled(false);
                    if (!showAns){
                        checkBoxList.get(i).setButtonDrawable(null);
                    }
                }
                break;
            case 2://填空题
                helper.setGone(R.id.lin_direct_answer,true);
                helper.setGone(R.id.rgroup_answer,false);
                helper.setGone(R.id.lin_check,false);
                helper.setText(R.id.tv_question,mContext.getString(R.string.input_blank)+item.getTitle());
                helper.setGone(R.id.et_direct_answeer,showAns);
//                helper.setText(R.id.et_direct_answeer,item.get().get(0).getOptionValue());

                break;
        }
    }
}
