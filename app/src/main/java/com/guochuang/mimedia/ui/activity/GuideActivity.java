package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.ui.activity.user.LoginActivity;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.MyViewPagerAdapter;

import butterknife.BindView;


public class GuideActivity extends MvpActivity implements View.OnClickListener {

    @BindView(R.id.vp_guide)
    ViewPager vp;
    @BindView(R.id.tv_guide_into)
    TextView tvInto;
    @BindView(R.id.ll_guide_location)
    LinearLayout llLocation;

    int[] imageIds=new int[]{
            R.drawable.bg_guide_1,
            R.drawable.bg_guide_2,
            R.drawable.bg_guide_3
    };
    View[] viewArr = new View[3];

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViewAndData() {
        tvInto.setOnClickListener(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        viewArr[0] = inflater.inflate(R.layout.layout_guide_image, null);
        ((ImageView)viewArr[0].findViewById(R.id.iv_guide)).setImageResource(imageIds[0]);
        viewArr[1] = inflater.inflate(R.layout.layout_guide_image, null);
        ((ImageView)viewArr[1].findViewById(R.id.iv_guide)).setImageResource(imageIds[1]);
        viewArr[2] = inflater.inflate(R.layout.layout_guide_image, null);
        ((ImageView)viewArr[2].findViewById(R.id.iv_guide)).setImageResource(imageIds[2]);
        vp.setAdapter(new MyViewPagerAdapter(viewArr));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setPageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setPageSelected(0);
    }
    public void setPageSelected(int pos) {
        for (int i = 0; i < llLocation.getChildCount(); i++) {
            if (pos == i) {
                llLocation.getChildAt(i).setSelected(true);
            } else {
                llLocation.getChildAt(i).setSelected(false);
            }
            if (pos==llLocation.getChildCount()-1){
                tvInto.setVisibility(View.VISIBLE);
            }else {
                tvInto.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide_into:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
