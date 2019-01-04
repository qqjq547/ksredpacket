package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.fragment.CollectInfoFragment;
import com.guochuang.mimedia.ui.fragment.CollectRedbagFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectActivity extends MvpActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rbtn_redbag)
    RadioButton rbtnRedbag;
    @BindView(R.id.rbtn_info)
    RadioButton rbtnInfo;
    @BindView(R.id.rg_nav)
    RadioGroup rgNav;
    int currentId = -1;
    Fragment[] fragments = new Fragment[2];

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.text_my_collection);
        fragments[0] = new CollectRedbagFragment();
        fragments[1] = new CollectInfoFragment();
        rgNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_redbag:
                        selectTab(0);
                        break;
                    case R.id.rbtn_info:
                        selectTab(1);
                        break;
                }
            }
        });
        rbtnRedbag.setChecked(true);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
    public void selectTab(int index) {
        if (currentId == index) {
            return;
        }
        if (currentId >= 0) {
            switchContent(fragments[currentId], fragments[index]);
        } else {
            switchContent(null, fragments[index]);
        }
        currentId = index;
    }

    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(R.id.fl_content, to).commit();
        } else if (from != to) {
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fl_content, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragments[currentId].onActivityResult(requestCode,resultCode,data);
    }
}
