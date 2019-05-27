package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.InviterUser;
import com.guochuang.mimedia.mvp.presenter.UpgradeAgentPresenter;
import com.guochuang.mimedia.mvp.view.UpgradeAgentView;
import com.guochuang.mimedia.tools.UrlConfig;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UpgradeAgentActivity extends MvpActivity<UpgradeAgentPresenter> implements UpgradeAgentView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_upgrade)
    TextView tvUpgrade;
    int money=0;

    @Override
    protected UpgradeAgentPresenter createPresenter() {
        return new UpgradeAgentPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_upgrade_agent;
    }

    @Override
    public void initViewAndData() {
        tvText.setText(R.string.agent_rule);
        showLoadingDialog(null);
        mvpPresenter.getAgentMoney();
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                IntentUtils.startImageActivity(this,UrlConfig.getHtmlUrl(UrlConfig.URL_AGENT_RULE));
                break;
            case R.id.tv_upgrade:
                  if (money>0){
                       showLoadingDialog(null);
                       mvpPresenter.getMyInviter();
                    }
                break;
        }
    }

    @Override
    public void setData(Integer data) {
        closeLoadingDialog();
        if (data!=null){
            money=data.intValue();
        }
    }

    @Override
    public void setInviter(InviterUser data) {
        closeLoadingDialog();
        if (data!=null){
            IntentUtils.startPurchaseActivity(this,Constant.TYPE_PURCHASE_AGENT,String.valueOf(money),0);
        }else {
            showShortToast(R.string.pls_set_inviter);
            startActivity(new Intent(UpgradeAgentActivity.this,SetInviterActivity.class));
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==Constant.REQUEST_PURCHASE){
            sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
            sendBroadcast(new Intent(Constant.ACTION_CHANGE_AGENT));
            finish();
        }
    }
}
