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
                group.setOnCheckedChangeListener(new OnCheckListener(helper.getAdapterPosition()));
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
                OnCheckBoxListener listener=new OnCheckBoxListener(helper.getAdapterPosition());
                List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr1=item.getOptionsList();
                for (int i=0;i<optionArr1.size();i++){
                    LookVideoResult.QuestionListBean.OptionsListBean bean=optionArr1.get(i);
                    checkBoxList.get(i).setText(bean.getOptionName()+"."+bean.getOptionValue());
                    checkBoxList.get(i).setVisibility(View.VISIBLE);
                    checkBoxList.get(i).setOnCheckedChangeListener(listener);
//                    rbtn.setChecked(bean.getIsAnswer()==1);
//                    rbtn.setEnabled(false);
//                    if (!showAns){
//                        rbtn.setButtonDrawable(null);
//                    }
                }
                break;
            case 2://填空题
                helper.setGone(R.id.lin_direct_answer,true);
                helper.setGone(R.id.rgroup_answer,false);
                helper.setGone(R.id.lin_check,false);
                helper.setText(R.id.tv_question,mContext.getString(R.string.input_blank)+item.getTitle());
                ((EditText)helper.getView(R.id.et_direct_answeer)).addTextChangedListener(new OnTextChangeListener(helper.getAdapterPosition()));
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
            List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr=getData().get(positon).getOptionsList();
            for(LookVideoResult.QuestionListBean.OptionsListBean bean:optionArr){
                bean.setSelect(false);
            }
            switch (i){
                case R.id.rbtn_one:
                    optionArr.get(0).setSelect(true);
                    break;
                case R.id.rbtn_two:
                    optionArr.get(1).setSelect(true);
                    break;
                case R.id.rbtn_three:
                    optionArr.get(2).setSelect(true);
                    break;
                case R.id.rbtn_four:
                    optionArr.get(3).setSelect(true);
                    break;
            }

        }
    }
    class OnCheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        int positon=0;
        public OnCheckBoxListener(int positon){
            this.positon=positon;
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr=getData().get(positon).getOptionsList();
            switch (compoundButton.getId()){
                case R.id.cb_one:
                    optionArr.get(0).setSelect(b);
                    break;
                case R.id.cb_two:
                    optionArr.get(1).setSelect(b);
                    break;
                case R.id.cb_three:
                    optionArr.get(2).setSelect(b);
                    break;
                case R.id.cb_four:
                    optionArr.get(3).setSelect(b);
                    break;
            }

        }
    }
    class OnTextChangeListener implements TextWatcher {
        int positon=0;

        public OnTextChangeListener(int positon) {
            this.positon = positon;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            List<LookVideoResult.QuestionListBean.OptionsListBean> optionArr=getData().get(positon).getOptionsList();
            if (optionArr==null||optionArr.size()==0){
                optionArr=new ArrayList<>();
                optionArr.add(new LookVideoResult.QuestionListBean.OptionsListBean());
            }
            String content=editable.toString().trim();
            if (TextUtils.isEmpty(content)){
                optionArr.get(0).setSelect(false);
                optionArr.get(0).setOptionName("");
                optionArr.get(0).setOptionValue("");
            }else {
                optionArr.get(0).setSelect(true);
                optionArr.get(0).setOptionName("");
                optionArr.get(0).setOptionValue(content);
            }
            getData().get(positon).setOptionsList(optionArr);
        }
    }
}
