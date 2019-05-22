package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.activity.common.ShareActivity;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class RecommendAgentActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_share_benefit)
    TextView tvShareBenefit;
    @BindView(R.id.lin_share_benefit)
    LinearLayout linShareBenefit;
    @BindView(R.id.tv_equal_count)
    TextView tvEqualCount;
    @BindView(R.id.lin_equal)
    LinearLayout linEqual;
    @BindView(R.id.tv_agent_count)
    TextView tvAgentCount;
    @BindView(R.id.lin_agent)
    LinearLayout linAgent;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;
    @BindView(R.id.tv_seller_count)
    TextView tvSellerCount;
    @BindView(R.id.lin_seller)
    LinearLayout linSeller;
    RecommendData data;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_recommend_agent;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.recommend);
//        tvText.setText(R.string.agent_rule);
        data= (RecommendData)getIntent().getSerializableExtra(Constant.RECOMMENDDATA);
       if (data!=null){
           tvFansCount.setText(String.valueOf(data.getDirectUser()));
           tvAgentCount.setText(String.valueOf(data.getDirectAgent()));
           tvShareBenefit.setText(data.getCumulativeCoin());
           tvEqualCount.setText(data.getCumulativeMoney());
           switch (data.getRole()){
               case Constant.USER_ROLE_FANS:
                   tvRole.setText("");
                   break;
               case Constant.USER_ROLE_AGENT:
                   tvRole.setText(R.string.agent);
                   break;
               case Constant.USER_ROLE_MANAGER:
                   tvRole.setText(R.string.manager);
                   break;
               case Constant.USER_ROLE_CHIEF:
                   tvRole.setText(R.string.inspector);
                   break;
               case Constant.USER_ROLE_STAR_CHIEF:
                   tvRole.setText(R.string.star_inspector);
                   break;
           }
       }
    }

    @OnClick({R.id.iv_back,R.id.tv_text, R.id.lin_share_benefit, R.id.lin_equal, R.id.lin_agent, R.id.lin_fans, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
//                IntentUtils.startImageActivity(this,Constant.URL_AGENT_RULE);
                break;
            case R.id.lin_share_benefit:
                startActivity(new Intent(this,ShareBenefitDetailActivity.class));
                break;
            case R.id.lin_equal:
                break;
            case R.id.lin_agent:
                if (data!=null){
                    IntentUtils.startRecommendDetailActivity(this,data,true);
                }
                break;
            case R.id.lin_fans:
                if (data!=null){
                    IntentUtils.startRecommendDetailActivity(this,data,false);
                }
                break;
            case R.id.btn_start:
                startActivity(new Intent(this,ShareActivity.class));
                break;
        }
    }
}
