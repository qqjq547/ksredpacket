package com.guochuang.mimedia.ui.activity.redbag;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.IndicatorLineUtil;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.ReceiveRedbagFragment;
import com.guochuang.mimedia.ui.fragment.SendRedbagFragment;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RedbagDynamicActivity2 extends MvpActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    Fragment[] fragments = new Fragment[2];
    String[] titles = {"收的红包", "发的红包"};

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_redbag_dynamic2;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.bg_red, false);

        fragments[0] = new ReceiveRedbagFragment();
        fragments[1] = new SendRedbagFragment();

        MyFragmentPagerAdapter redbagDynamicAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(redbagDynamicAdapter);
        tabLayout.setupWithViewPager(viewpager);

        for (int i = 0; i < titles.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tab_item_redbag_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.tab_tv_title);
            tv.setText(titles[i]);
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(view);
        }


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int index = 0; index < tabLayout.getTabCount(); index++) {
                    tabLayout.getTabAt(index).getCustomView().findViewById(R.id.iv_indcator).setSelected(false);
                }
                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.iv_indcator).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @OnClick({R.id.iv_back, R.id.tab_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tab_layout:

                break;
        }
    }
}
