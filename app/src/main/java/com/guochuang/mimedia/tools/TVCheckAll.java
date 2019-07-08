package com.guochuang.mimedia.tools;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;

public class TVCheckAll {
    private int maxLine = 3;
    private Context context;
    private TextView tv;
    private String content;
    private SpannableString elipseString;//收起的文字
    private SpannableString notElipseString;//展开的文字

    public TVCheckAll( Context context, TextView tv, String content,int maxLine) {
        this.context = context;
        this.tv = tv;
        this.content = content;
        this.maxLine = maxLine;
        getLastIndexForLimit();
    }

    private void getLastIndexForLimit() {
        //获取TextView的画笔对象
        TextPaint paint = tv.getPaint();
        //每行文本的布局宽度
        int width =context.getResources().getDisplayMetrics().widthPixels - CommonUtil.dip2px(context,40);
        //实例化StaticLayout 传入相应参数
        StaticLayout staticLayout = new StaticLayout(content,paint,width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        //判断content是行数是否超过最大限制行数3行
        if (staticLayout.getLineCount()>maxLine) {
            //定义展开后的文本内容
            String string1 = content + "    收起";
            notElipseString = new SpannableString(string1);
            //给收起两个字设成蓝色
            notElipseString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_blue)), string1.length() - 2, string1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //获取到第三行最后一个文字的下标
            int index = staticLayout.getLineStart(maxLine) - 1;
            //定义收起后的文本内容
            String substring = content.substring(0, index - 6) + "..." + "查看全部";
            elipseString = new SpannableString(substring);
            //给查看全部设成蓝色
            elipseString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_blue)), substring.length() - 4, substring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置收起后的文本内容
            tv.setText(elipseString);
            tv.setOnClickListener(onClickListener);
            //将textview设成选中状态 true用来表示文本未展示完全的状态,false表示完全展示状态，用于点击时的判断
            tv.setSelected(true);
        } else {
            //没有超过 直接设置文本
            tv.setText(content);
            tv.setOnClickListener(null);
        }
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.isSelected()) {
                //如果是收起的状态
                tv.setText(notElipseString);
                tv.setSelected(false);
            } else {
                //如果是展开的状态
                tv.setText(elipseString);
                tv.setSelected(true);
            }
        }
    };
}
