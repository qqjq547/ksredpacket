package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.ui.activity.ShareActivity;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class RecommendFanActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;
    @BindView(R.id.tv_share_benefit)
    TextView tvShareBenefit;
    @BindView(R.id.lin_share_benefit)
    LinearLayout linShareBenefit;
    @BindView(R.id.tv_equal_count)
    TextView tvEqualCount;
    @BindView(R.id.lin_equal)
    LinearLayout linEqual;
    @BindView(R.id.wv_summary)
    WebView wvSummary;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_recommend_fans;
    }

    @Override
    public void initViewAndData() {
      tvTitle.setText(R.string.recommend);
      CommonUtil.initH5WebView(this,wvSummary);
      wvSummary.loadUrl(Constant.URL_RULE_RECOMMEND);
        RecommendData data= (RecommendData)getIntent().getSerializableExtra(Constant.RECOMMENDDATA);
        if (data!=null) {
            tvFansCount.setText(String.valueOf(data.getDirectUser()));
            tvShareBenefit.setText(data.getCumulativeCoin());
            tvEqualCount.setText(data.getCumulativeMoney());
        }
    }

    @OnClick({R.id.iv_back, R.id.lin_fans, R.id.lin_share_benefit, R.id.lin_equal, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_fans:
                startActivity(new Intent(this,FansDetailActivity.class));
                break;
            case R.id.lin_share_benefit:
                startActivity(new Intent(this,ShareBenefitDetailActivity.class));
                break;
            case R.id.lin_equal:
                break;
            case R.id.btn_start:
                startActivity(new Intent(this,ShareActivity.class));
                break;
        }
    }
}
