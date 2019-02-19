package com.guochuang.mimedia.ui.activity.beenest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NestTemplate;
import com.guochuang.mimedia.mvp.presenter.BeeNestTempPresneter;
import com.guochuang.mimedia.mvp.view.BeeNestTempView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.ScaleTransformer;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.BeeNestTempFragment;
import com.sz.gcyh.KSHongBao.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class BeeNestTempActivity extends MvpActivity<BeeNestTempPresneter> implements BeeNestTempView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_empty)
    LinearLayout linEmpty;
    @BindView(R.id.vp_temp)
    ViewPager vpTemp;
    @BindView(R.id.lin_indicate)
    LinearLayout linIndicate;
    @BindView(R.id.btn_use_temp)
    Button btnUseTemp;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.tv_total)
    TextView tvTotal;

    List<NestTemplate> tempArr=new ArrayList<>();
    BeeNestTempFragment[] fragments;
    ImageView[] views;
    boolean isDeleteOperated=false;//是否有删除模板
    @Override
    protected BeeNestTempPresneter createPresenter() {
        return new BeeNestTempPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_beenest_temp;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.ad_temp);
        showLoadingDialog(null);
        mvpPresenter.getTempList();
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
        tvTotal.setText(String.format(getString(R.string.format_count_ad_temp),tempArr.size()));
        fragments=new BeeNestTempFragment[tempArr.size()];
        views=new ImageView[tempArr.size()];
        linIndicate.removeAllViews();
        for (int i=0;i<tempArr.size();i++){
            fragments[i]=new BeeNestTempFragment();
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

    @Override
    public void setData(List<NestTemplate> data) {
        closeLoadingDialog();
        this.tempArr=data;
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
    public void setDelete(Boolean data) {
        isDeleteOperated=true;
        closeLoadingDialog();
        showShortToast(R.string.delete_success);
        tempArr.remove(vpTemp.getCurrentItem());
        setData();
        setEmptyView();
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
                                mvpPresenter.deleteTemplate(tempArr.get(vpTemp.getCurrentItem()).getNestTemplateId());
                            }
                        }).create().show();
                break;
        }
    }


    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}
