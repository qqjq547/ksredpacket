package com.guochuang.mimedia.ui.activity.user;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.AAARate;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.presenter.MyAAAAPresenter;
import com.guochuang.mimedia.mvp.view.MyAAAAView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.CustomProDialog;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

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
    @BindView(R.id.scroll_aaa_root)
    ScrollView mScrollAaaRoot;
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
        getMyAAARate();
        tvTitle.setText(getResources().getString(R.string.my_aaa_str));


    }


    /**
     * 五分钟获取一次 税率
     */
    private void getMyAAARate() {
        addSubscription(RxUtil.countdown(60)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mvpPresenter.getMyAAARate();
                        getMyAAARate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                    }
                }));

    }

    private void getMyAAA() {
        showLoadingDialog(null);
        mvpPresenter.getMyAAA();
    }


    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_tibi, R.id.tv_transform_ksb, R.id.tv_copy, R.id.tv_goto_real_name,R.id.lin_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, AAADetailedActivity.class));
                break;
            case R.id.tv_tibi:
                startActivityForResult(new Intent(this, AaaTransferActivity.class),Constant.REFRESH);
                break;
            case R.id.tv_transform_ksb:
                startActivityForResult(new Intent(this, AaaTranKsbActivity.class),Constant.REFRESH);
                break;
            case R.id.tv_copy:
                CommonUtil.copyMsg(this,tvAaaAddress.getText().toString());
                showShortToast(getResources().getString(R.string.copyed));
                break;
            case R.id.tv_goto_real_name:
                startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                break;
            case R.id.lin_price:
                new DialogBuilder(this)
                        .setMessage(getString(R.string.myaaa_tip))
                        .setPositiveButton(R.string.i_known,null)
                        .create().show();
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
        mIsRealName = data.getNameAuthentication() == 1 ? true : false;
       //据实名展示不同的界面
        selectShowView(mIsRealName);
        //设置文字内容
        setContent(data);


    }


    /**
     * 设置文字内容
     */
    private void setContent(MyAAA data) {
        tvText.setText(getResources().getString(R.string.aaa_detailed_str));
        tvAaaNumber.setText(data.getCoin());
        tvMoney.setText(data.getMoney());
        tvAaaPrice.setText(data.getExchangeRate());
        GlideImgManager.loadImage(this,data.getQrcodeUrlKey(),ivCode);
        tvAaaAddress.setText(data.getWalletAddress());


    }

    /**
     * 是否实名 根据实名展示不同的界面
     * @param isRealName
     */
    private void selectShowView(boolean isRealName) {
        if (isRealName) {
            tvText.setVisibility(View.VISIBLE);
            mScrollAaaRoot.setVisibility(View.VISIBLE);
            mTvGotoRealName.setVisibility(View.GONE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            tvText.setVisibility(View.GONE);
            mScrollAaaRoot.setVisibility(View.GONE);
            mTvGotoRealName.setVisibility(View.VISIBLE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.color_6a4bf1));
        }
    }

    @Override
    public void setAAARate(AAARate data) {
        tvAaaPrice.setText(data.getRate()+"");
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
