package com.guochuang.mimedia.ui.activity.redbag;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.mvp.presenter.LinkRedbagDetailPresenter;
import com.guochuang.mimedia.mvp.view.LinkRedbagDetailView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class LinkRedbagDetailActivity extends MvpActivity<LinkRedbagDetailPresenter> implements LinkRedbagDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wv_content)
    WebView wvContent;

    RedbagDetail redbagDetail;
    String redPacketUuid;
    String redPacketType;
    String roleType;

    @Override
    protected LinkRedbagDetailPresenter createPresenter() {
        return new LinkRedbagDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_link_redbag_detail;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.bg_white, true);
        redbagDetail = (RedbagDetail) getIntent().getSerializableExtra(Constant.RED_PACKET_DETAIL);
        if (redbagDetail == null) {
            showLoadingDialog(null);
            redPacketUuid = getIntent().getStringExtra(Constant.RED_PACKET_UUID);
            roleType = getIntent().getStringExtra(Constant.ROLE_TYPE);
            redPacketType = getIntent().getStringExtra(Constant.RED_PACKET_TYPE);//可能为空
            String startIndex=getIntent().getStringExtra(Constant.START_INDEX);
            mvpPresenter.getRedPacketInfo(redPacketUuid, roleType, startIndex);
            return;
        }
        redPacketUuid = getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        redPacketType=getIntent().getStringExtra(Constant.RED_PACKET_TYPE);
        tvTitle.setText(redbagDetail.getCoin()+getString(R.string.ksb));
        CommonUtil.initH5WebView(this, wvContent);
        wvContent.loadUrl(redbagDetail.getLinkAddress());
        startCountDown(redbagDetail.getReadingSecond());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
    private void startCountDown(int second) {
        addSubscription(RxUtil.countdown(second)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        ivBack.setVisibility(View.GONE);
                        tvBack.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ivBack.setVisibility(View.VISIBLE);
                        tvBack.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ivBack.setVisibility(View.VISIBLE);
                        tvBack.setVisibility(View.GONE);
                        showShortToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvBack.setText(String.format(getString(R.string.format_second_unit), integer));
                    }
                }));
    }
    @Override
    public void setInfo(RedbagInfo data) {
        closeLoadingDialog();
       if (data!=null){
           tvTitle.setText(data.getDrawCoin()+getString(R.string.ksb));
           CommonUtil.initH5WebView(this, wvContent);
           wvContent.loadUrl(data.getLinkAddress());
       }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
    @Override
    public void onBackPressed() {
        if (ivBack.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }
    }
}
