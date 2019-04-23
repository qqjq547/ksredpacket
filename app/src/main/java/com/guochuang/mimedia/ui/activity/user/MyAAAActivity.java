package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.presenter.MyAAAAPresenter;
import com.guochuang.mimedia.mvp.view.MyAAAAView;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAAAActivity extends MvpActivity<MyAAAAPresenter> implements MyAAAAView {
    @BindView(R.id.ll_root_view)
    LinearLayout mLlRootView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_goto_real_name)
    TextView mTvGotoRealName;
    @BindView(R.id.ll_aaa_root)
    LinearLayout mLlAaaRoot;
    @BindView(R.id.tv_aaa_number)
    TextView tvAaaNumber;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_aaa_price)
    TextView tvAaaPrice;
    @BindView(R.id.tv_tibi)
    TextView tvTibi;
    @BindView(R.id.tv_transform_ksb)
    TextView tvTransformKsb;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_aaa_address)
    TextView tvAaaAddress;

    boolean mIsRealName;

    @Override
    protected MyAAAAPresenter createPresenter() {
        return new MyAAAAPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_myaaa;
    }

    @Override
    public void initViewAndData() {
        //判断是否实名
        getMyAAA();
        tvTitle.setText(getResources().getString(R.string.my_aaa_str));



    }

    private void getMyAAA() {
        showLoadingDialog(null);
        mvpPresenter.getMyAAA();
    }


    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_tibi, R.id.tv_transform_ksb, R.id.tv_copy, R.id.tv_goto_real_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, AAADetailedActivity.class));
                break;
            case R.id.tv_tibi:

                break;
            case R.id.tv_transform_ksb:

                break;
            case R.id.tv_copy:

                break;
            case R.id.tv_goto_real_name:
                startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                break;
        }
    }

    @Override
    public void setError(String message) {
        closeLoadingDialog();
        showShortToast(message);
    }

    /**
     * 获取我的AAA  返回数据
     *
     * @param data
     */
    @Override
    public void setData(MyAAA data) {
        closeLoadingDialog();


        tvText.setText(getResources().getString(R.string.aaa_detailed_str));
        if (mIsRealName) {
            tvText.setVisibility(View.VISIBLE);
            mLlAaaRoot.setVisibility(View.VISIBLE);
            mTvGotoRealName.setVisibility(View.GONE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            tvText.setVisibility(View.GONE);
            mLlAaaRoot.setVisibility(View.GONE);
            mTvGotoRealName.setVisibility(View.VISIBLE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.color_6a4bf1));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (Constant.REFRESH == requestCode) {
                //实名成功
                initViewAndData();
            }
        }
    }
}
