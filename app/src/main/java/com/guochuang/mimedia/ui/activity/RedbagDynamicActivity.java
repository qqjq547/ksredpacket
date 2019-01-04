package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.ui.fragment.ReceiveRedbagFragment;
import com.guochuang.mimedia.ui.fragment.SendRedbagFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedbagDynamicActivity extends MvpActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rbtn_receive)
    RadioButton rbtnReceive;
    @BindView(R.id.rbtn_send)
    RadioButton rbtnSend;
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
        return R.layout.activity_redbag_dynamic;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.bg_red,false);
        fragments[0] = new ReceiveRedbagFragment();
        fragments[1] = new SendRedbagFragment();
        rgNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_receive:
                        selectTab(0);
                        break;
                    case R.id.rbtn_send:
                        selectTab(1);
                        break;
                }
            }
        });
        rbtnReceive.setChecked(true);
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
}
