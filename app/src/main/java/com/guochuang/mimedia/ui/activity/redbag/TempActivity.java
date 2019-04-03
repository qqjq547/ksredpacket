package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guochuang.mimedia.tools.DialogBuilder;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.presenter.TempPresenter;
import com.guochuang.mimedia.mvp.view.TempView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DepthPageTransformer;
import com.guochuang.mimedia.tools.ScaleTransformer;
import com.guochuang.mimedia.ui.adapter.MyFragmentListAdapter;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.TempFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TempActivity extends MvpActivity<TempPresenter> implements TempView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.lin_empty)
    LinearLayout linEmpty;
    @BindView(R.id.vp_temp)
    ViewPager vpTemp;
    @BindView(R.id.lin_indicate)
    LinearLayout linIndicate;
    @BindView(R.id.btn_use_temp)
    TextView btnUseTemp;
    @BindView(R.id.btn_delete)
    TextView btnDelete;


    List<RedbagTemp> tempArr=new ArrayList<>();
    TempFragment[] fragments;
    ImageView[] views;
    boolean isDeleteOperated=false;//是否有删除模板
    String redPacketType;

    @Override
    protected TempPresenter createPresenter() {
        return new TempPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_temp;
    }

    @Override
    public void initViewAndData() {
        redPacketType=getIntent().getStringExtra(Constant.RED_PACKET_TYPE);
        tvTitle.setText(R.string.select_temp);
        showLoadingDialog(null);
        mvpPresenter.getTemplate(redPacketType);
    }
    public void setSelectIndicate(int position){
        for (int i=0;i<views.length;i++){
            if (position==i){
                views[i].setSelected(true);
            }else {
                views[i].setSelected(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isDeleteOperated){
            setResult(RESULT_OK,getIntent());
            finish();
        }else {
            super.onBackPressed();
        }
    }

    public void setData(){
          switch (redPacketType){
                case Constant.RED_PACKET_TYPE_RANDOM:
                    tvTotal.setText(String.format(getString(R.string.format_count_redbag_temp_random),tempArr.size()));
                    break;
                case Constant.RED_PACKET_TYPE_PASSWORD:
                    tvTotal.setText(String.format(getString(R.string.format_count_redbag_temp_password),tempArr.size()));
                    break;
                case Constant.RED_PACKET_TYPE_LUCKY:
                    tvTotal.setText(String.format(getString(R.string.format_count_redbag_temp_lucky),tempArr.size()));
                    break;
            }
        fragments=new TempFragment[tempArr.size()];
        views=new ImageView[tempArr.size()];
        linIndicate.removeAllViews();
        for (int i=0;i<tempArr.size();i++){
            fragments[i]=new TempFragment();
            fragments[i].setTemp(tempArr.get(i));
            views[i]=new ImageView(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin=CommonUtil.dip2px(this,5);
            layoutParams.setMargins(margin,0,margin,0);
            views[i].setLayoutParams(layoutParams);
            views[i].setBackgroundResource(R.drawable.ic_indicate_selector);
            linIndicate.addView(views[i]);
        }
        if (linIndicate.getChildCount()>0) {
            setSelectIndicate(0);
            btnUseTemp.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }else {
            btnUseTemp.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        vpTemp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,null));
        vpTemp.setPageTransformer(true, new ScaleTransformer());
        vpTemp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setSelectIndicate(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_use_temp, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_use_temp:
                Intent intent=getIntent();
                intent.putExtra(Constant.TEMPLATE,tempArr.get(vpTemp.getCurrentItem()));
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btn_delete:
                new DialogBuilder(this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.ensure_delete_temp)
                        .setNegativeButton(R.string.cancel,null)
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLoadingDialog(null);
                                mvpPresenter.deleteTemplate(redPacketType,tempArr.get(vpTemp.getCurrentItem()).getTemplateId());
                            }
                        }).create().show();
                break;
        }
    }

    @Override
    public void setData(List<RedbagTemp> data) {
        closeLoadingDialog();
        if (data!=null&&data.size()>0) {
            linEmpty.setVisibility(View.GONE);
            vpTemp.setVisibility(View.VISIBLE);
            tempArr.addAll(data);
            setData();
        }
        setEmptyView();
    }

    private void setEmptyView(){
        if (tempArr!=null&&tempArr.size()>0){
            btnUseTemp.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }else {
            linEmpty.setVisibility(View.VISIBLE);
            vpTemp.setVisibility(View.GONE);
        }
    }
    @Override
    public void setDeleteTemp(Boolean data) {
        isDeleteOperated=true;
        closeLoadingDialog();
        showShortToast(R.string.delete_success);
        tempArr.remove(vpTemp.getCurrentItem());
        setData();
        setEmptyView();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
