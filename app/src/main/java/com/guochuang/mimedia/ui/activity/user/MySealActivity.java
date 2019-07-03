package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.SealRate;
import com.guochuang.mimedia.mvp.model.MySeal;
import com.guochuang.mimedia.mvp.presenter.MySealPresenter;
import com.guochuang.mimedia.mvp.view.MySealView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class MySealActivity extends MvpActivity<MySealPresenter> implements MySealView {
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
    @BindView(R.id.tv_seal_number)
    TextView tvSealNumber;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_seal_price)
    TextView tvSealPrice;
    @BindView(R.id.tv_tibi)
    TextView tvTibi;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_seal_address)
    TextView tvSealAddress;
    @BindView(R.id.lin_content)
    LinearLayout linContent;
    @BindView(R.id.tv_tip)
    TextView tvTip;


    MySeal mySeal;
    SealRate sealRate;

    @Override
    protected MySealPresenter createPresenter() {
        return new MySealPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_seal;
    }

    @Override
    public void initViewAndData() {
        //判断是否实名
        tvTitle.setText(R.string.my_seal);
        tvText.setText(R.string.seal_detailed_str);
        showLoadingDialog(null);
        mvpPresenter.getMySeal();
        getMyAAARate();
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
                        mvpPresenter.getMyAAARate(Constant.DIGITAL_CURRENCY_SEAL);
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


    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_tibi, R.id.tv_transform_qc,R.id.tv_copy, R.id.tv_goto_real_name, R.id.lin_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, MySealDetailsActivity.class));
                break;
            case R.id.tv_tibi:
                startActivityForResult(new Intent(this, SealTransferActivity.class), Constant.REFRESH);
                break;
            case R.id.tv_transform_qc:
                startActivityForResult(new Intent(this, SealTranQcActivity.class), Constant.REFRESH);
                break;
            case R.id.tv_copy:
                CommonUtil.copyMsg(this, tvSealAddress.getText().toString());
                showShortToast(getResources().getString(R.string.copyed));
                break;
            case R.id.tv_goto_real_name:
                startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                break;
            case R.id.lin_price:
                if (mySeal!=null) {
                    new DialogBuilder(this)
                            .setMessage(mySeal.getRemark())
                            .setPositiveButton(R.string.i_known, null)
                            .create().show();
                }
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
    public void setData(MySeal data) {
        closeLoadingDialog();
        if (data!=null){
            mySeal =data;
            selectShowView();
        }

    }

    /**
     * 是否实名 根据实名展示不同的界面
     *
     */
    private void selectShowView() {
        tvSealNumber.setText(mySeal.getCoin());
        tvMoney.setText(mySeal.getMoney());
        tvSealPrice.setText(mySeal.getKsbPrice());
        GlideImgManager.loadImage(this, mySeal.getQrcodeUrlKey(), ivCode);
        tvSealAddress.setText(mySeal.getKsbAddress());
        if (mySeal.getNameAuthentication()==1) {
            mTvGotoRealName.setVisibility(View.GONE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.white));
            tvTip.setText(R.string.seal_des_str);
            tvTibi.setVisibility(View.VISIBLE);
        } else {
            mTvGotoRealName.setVisibility(View.VISIBLE);
            mLlRootView.setBackgroundColor(getResources().getColor(R.color.color_6a4bf1));
            tvTip.setText(R.string.seal_des_str_not_name);
            tvTibi.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAAARate(SealRate data) {
        if (data!=null){
            sealRate =data;
            tvSealPrice.setText(String.valueOf(data.getRate()));
            //更新更新等值等值
            if (mySeal !=null&&!TextUtils.isEmpty(mySeal.getCoin())){
                tvMoney.setText(CommonUtil.formatDouble(sealRate.getRate() * Double.valueOf(mySeal.getCoin()), 2));
            }
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
