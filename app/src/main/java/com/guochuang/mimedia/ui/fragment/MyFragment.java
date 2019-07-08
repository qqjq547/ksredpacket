package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MySeal;
import com.guochuang.mimedia.mvp.model.MyQC;
import com.guochuang.mimedia.mvp.model.NestAuctionMsg;
import com.guochuang.mimedia.mvp.model.RegionCore;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.UrlConfig;
import com.guochuang.mimedia.ui.activity.MainActivity;
import com.guochuang.mimedia.ui.activity.beenest.AdBidActivity;
import com.guochuang.mimedia.ui.activity.beenest.MyAdActivity;
import com.guochuang.mimedia.ui.activity.city.CityActivity;
import com.guochuang.mimedia.ui.activity.redbag.RedbagDynamicActivity;
import com.guochuang.mimedia.ui.activity.user.MyAddressActivity;
import com.guochuang.mimedia.ui.activity.user.MySealActivity;
import com.guochuang.mimedia.ui.activity.user.MyQCActivity;
import com.guochuang.mimedia.ui.adapter.MyViewListAdapter;
import com.guochuang.mimedia.ui.dialog.SheetDialog;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.MyMenuItem;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.MyPresenter;
import com.guochuang.mimedia.mvp.view.MyView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.ScaleTransformer;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.user.MessageActivity;
import com.guochuang.mimedia.ui.activity.user.MyCollectActivity;
import com.guochuang.mimedia.ui.activity.user.MyCommentActivity;
import com.guochuang.mimedia.ui.activity.operation.OperationCenterActivity;
import com.guochuang.mimedia.ui.activity.user.RecommendAgentActivity;
import com.guochuang.mimedia.ui.activity.user.RecommendFanActivity;
import com.guochuang.mimedia.ui.activity.user.SafeCenterActivity;
import com.guochuang.mimedia.ui.activity.user.SettingActivity;
import com.guochuang.mimedia.ui.activity.user.WelfareActivity;
import com.guochuang.mimedia.ui.adapter.MyMenuAdapter;
import com.guochuang.mimedia.view.BadgeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFragment extends MvpFragment<MyPresenter> implements MyView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_my_header)
    ImageView ivMyHeader;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    @BindView(R.id.vp_my)
    ViewPager vpMy;
    @BindView(R.id.ll_my_lamp)
    LinearLayout llMyLamp;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.lin_nestad)
    LinearLayout linNestad;

    TextView tvMySeal;
    TextView tvSealBalance;

    TextView tvMyQc;
    TextView tvQcBalance;
    TextView tvTotalIncome;
    TextView tvYesterdayIncome;
    TextView tvProvince;
    TextView tvLevel;
    TextView tvAgent;
    TextView tvFans;
    TextView tvRecommendTotalIncome;
    LinearLayout linAgent;

    List<MyMenuItem> itemArr = new ArrayList<>();
    MyMenuAdapter menuAdapter;

    List<View> viewArr=new ArrayList<>();
    View sealView,qcView, regionCoreView, recommendView;
    MyViewListAdapter pagerAdapter;
    UserInfo userInfo;
    BadgeView badgeView;
    RecommendData recommendData;
    boolean isShowNestAd = false;


    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViewAndData() {
        if (Constant.isDebug) {
            tvTitle.setVisibility(View.VISIBLE);
            int debugHost = getPref().getInt(PrefUtil.DEBUGHOST, Constant.DEFAULT_HOST);
            switch (debugHost) {
                case 0://测试host
                    tvTitle.setText(R.string.test_version);
                    break;
                case 1://生产host
                    tvTitle.setText(R.string.release_version);
                    break;
                case 2://开发host
                    tvTitle.setText(R.string.dev_version);
                    break;
            }
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (isShowNestAd) {
            linNestad.setVisibility(View.VISIBLE);
        } else {
            linNestad.setVisibility(View.GONE);
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        sealView = inflater.inflate(R.layout.layout_my_seal, null);
        sealView.setOnClickListener(pageOnClickListener);
        qcView = inflater.inflate(R.layout.layout_my_qc, null);
        qcView.setOnClickListener(pageOnClickListener);
        regionCoreView = inflater.inflate(R.layout.layout_my_operation_center, null);
        regionCoreView.setOnClickListener(pageOnClickListener);
        recommendView = inflater.inflate(R.layout.layout_my_recommend, null);
        recommendView.setOnClickListener(pageOnClickListener);
        viewArr.add(sealView);
        viewArr.add(qcView);
        viewArr.add(recommendView);


        tvMySeal = ButterKnife.findById(sealView, R.id.tv_my_ksb);
        tvSealBalance = ButterKnife.findById(sealView, R.id.tv_balance);

        tvMyQc = ButterKnife.findById(qcView, R.id.tv_my_ksb);
        tvQcBalance = ButterKnife.findById(qcView, R.id.tv_balance);

        tvTotalIncome = ButterKnife.findById(regionCoreView, R.id.tv_total_income);
        tvYesterdayIncome = ButterKnife.findById(regionCoreView, R.id.tv_yesterday_income);
        tvProvince = ButterKnife.findById(regionCoreView, R.id.tv_province);

        tvLevel = ButterKnife.findById(recommendView, R.id.tv_level);
        tvAgent = ButterKnife.findById(recommendView, R.id.tv_agent);
        tvFans = ButterKnife.findById(recommendView, R.id.tv_fans);
        tvRecommendTotalIncome = ButterKnife.findById(recommendView, R.id.tv_total_income);
        linAgent = ButterKnife.findById(recommendView, R.id.lin_agent);

        pagerAdapter = new MyViewListAdapter(viewArr);
        vpMy.setAdapter(pagerAdapter);
        initDot();
        vpMy.setPageTransformer(false, new ScaleTransformer());
        vpMy.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setPageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setPageSelected(0);
        itemArr.add(new MyMenuItem(R.drawable.ic_my_city, R.string.text_my_city));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_dynamic, R.string.text_my_dynamic));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_ad_bid, R.string.ad_bid));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_ad, R.string.my_ad));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_recommend, R.string.text_my_recommend));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_order, R.string.text_my_order));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_welfare, R.string.text_my_welfare));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_collection, R.string.text_my_collection));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_comments, R.string.text_my_comments));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_safe, R.string.text_my_safe));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_address, R.string.text_my_address));
        itemArr.add(new MyMenuItem(R.drawable.ic_my_help, R.string.text_my_help));
        rvMenu.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        menuAdapter = new MyMenuAdapter(itemArr);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (itemArr.get(position).getIconResId()) {
                    case R.drawable.ic_my_operate_center:
                        startActivity(new Intent(getActivity(), OperationCenterActivity.class));
                        break;
                    case R.drawable.ic_my_recommend:
                        if (recommendData != null) {
                            if (getPref().getInt(PrefUtil.USER_ROLE, Constant.USER_ROLE_FANS) > Constant.USER_ROLE_FANS) {
                                Intent intent = new Intent(getActivity(), RecommendAgentActivity.class);
                                intent.putExtra(Constant.RECOMMENDDATA, recommendData);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), RecommendFanActivity.class);
                                intent.putExtra(Constant.RECOMMENDDATA, recommendData);
                                startActivity(intent);
                            }
                        } else {
                            mvpPresenter.getRecommendData();
                        }
                        break;
                    case R.drawable.ic_my_city:
                        startActivity(new Intent(getActivity(), CityActivity.class));
                        break;
                    case R.drawable.ic_my_order:
                        IntentUtils.startWebActivity(getActivity(), getString(R.string.text_my_order), UrlConfig.getHtmlUrl(UrlConfig.URL_MY_ORDER));
                        break;
                    case R.drawable.ic_my_dynamic:
                        startActivity(new Intent(getActivity(), RedbagDynamicActivity.class));
                        break;
                    case R.drawable.ic_my_collection:
                        startActivity(new Intent(getActivity(), MyCollectActivity.class));
                        break;
                    case R.drawable.ic_my_comments:
                        startActivity(new Intent(getActivity(), MyCommentActivity.class));
                        break;
                    case R.drawable.ic_my_safe:
                        startActivity(new Intent(getActivity(), SafeCenterActivity.class));
                        break;
                    case R.drawable.ic_my_address:
                        startActivity(new Intent(getActivity(), MyAddressActivity.class));
                        break;
                    case R.drawable.ic_my_help:
                        IntentUtils.startWebActivity(getActivity(), getString(R.string.help_center), UrlConfig.getHtmlUrl(UrlConfig.URL_HELP_CENTER));
                        break;
                    case R.drawable.ic_my_welfare:
                        startActivity(new Intent(getActivity(), WelfareActivity.class));
                        break;
                    case R.drawable.ic_my_seal:
                        startActivity(new Intent(getActivity(), MySealActivity.class));
                        break;
                    case R.drawable.ic_my_ad_bid:
                        //广告竞购
                        ((MainActivity) getActivity()).clearMarker();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getActivity(), AdBidActivity.class));
                            }
                        }, 500);
                        break;
                    case R.drawable.ic_my_ad:
                    startActivity(new Intent(getActivity(), MyAdActivity.class));
                        break;
                }
            }
        });
        rvMenu.setAdapter(menuAdapter);
        if (userInfo == null) {
            userInfo = App.getInstance().getUserInfo();
        }
        mvpPresenter.getRecommendData();
        mvpPresenter.getRegionCore();
        mvpPresenter.getMySeal();
        mvpPresenter.getMyQC();

    }

    View.OnClickListener pageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lin_my_seal:
                    startActivity(new Intent(getActivity(), MySealActivity.class));
                    break;
                case R.id.lin_my_qc:
                    startActivity(new Intent(getActivity(), MyQCActivity.class));
                    break;
                case R.id.lin_my_operation_center:
                    startActivity(new Intent(getActivity(), OperationCenterActivity.class));
                    break;
                case R.id.lin_my_recommend:
                    mvpPresenter.getRecommendData();
                    if (recommendData != null) {
                        mvpPresenter.getRecommendData();
                        if (getPref().getInt(PrefUtil.USER_ROLE, Constant.USER_ROLE_FANS) > Constant.USER_ROLE_FANS) {
                            Intent intent = new Intent(getActivity(), RecommendAgentActivity.class);
                            intent.putExtra(Constant.RECOMMENDDATA, recommendData);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), RecommendFanActivity.class);
                            intent.putExtra(Constant.RECOMMENDDATA, recommendData);
                            startActivity(intent);
                        }
                    }
                    break;

            }

        }
    };

    public void setPageSelected(int pos) {
        for (int i = 0; i < viewArr.size(); i++) {
            if (pos == i) {
                llMyLamp.getChildAt(i).setSelected(true);
            } else {
                llMyLamp.getChildAt(i).setSelected(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpUser();
        mvpPresenter.getAuctionMsg();
        mvpPresenter.getMyQC();
    }

    @OnClick({R.id.iv_setting,
            R.id.iv_message,
            R.id.lin_my_seal,
            R.id.lin_my_qc,
            R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.iv_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.lin_my_seal:


                startActivity(new Intent(getActivity(), MySealActivity.class));

                break;
            case R.id.lin_my_qc:

                startActivity(new Intent(getActivity(), MyQCActivity.class));
                break;
            case R.id.tv_title:
                List<String> itemArr = new ArrayList<>();
                itemArr.add(getString(R.string.test_version));
                itemArr.add(getString(R.string.release_version));
                itemArr.add(getString(R.string.dev_version));
                SheetDialog sheetDialog = new SheetDialog(getActivity(), itemArr, new SheetDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        App.getInstance().forceLogin();
                        switch (position) {
                            case 0:
                                getPref().setInt(PrefUtil.DEBUGHOST, 0);
                                break;
                            case 1:
                                getPref().setInt(PrefUtil.DEBUGHOST, 1);
                                break;
                            case 2:
                                getPref().setInt(PrefUtil.DEBUGHOST, 2);
                                break;
                        }
                        ApiClient.changeEnv();
                    }
                });
                sheetDialog.show();
                break;
        }
    }

    public void setUpUser() {
        userInfo = App.getInstance().getUserInfo();
        if (isAdded() && userInfo != null) {
            GlideImgManager.loadCircleImage(getActivity(), userInfo.getAvatar(), ivMyHeader);
            tvMyName.setText(userInfo.getNickName());
            tvMySeal.setText(getPref().getString(PrefUtil.COIN, ""));
            tvSealBalance.setText(getPref().getString(PrefUtil.MONEY, ""));
            setMsgDotView();
        }

    }

    @Override
    public void setRecommendData(RecommendData data) {
        if (data != null) {
            recommendData = data;
            getPref().setInt(PrefUtil.USER_ROLE, data.getRole());
            tvAgent.setText(String.valueOf(data.getDirectAgent()));
            tvFans.setText(String.valueOf(data.getDirectUser()));
            tvRecommendTotalIncome.setText(data.getCumulativeCoin());
            if (data.getRole() > Constant.USER_ROLE_FANS) {
                linAgent.setVisibility(View.VISIBLE);
            } else {
                linAgent.setVisibility(View.GONE);
            }
            switch (data.getRole()) {
                case Constant.USER_ROLE_FANS:
                    tvLevel.setText("");
                    break;
                case Constant.USER_ROLE_AGENT:
                    tvLevel.setText(R.string.agent);
                    break;
                case Constant.USER_ROLE_MANAGER:
                    tvLevel.setText(R.string.manager);
                    break;
                case Constant.USER_ROLE_CHIEF:
                    tvLevel.setText(R.string.inspector);
                    break;
                case Constant.USER_ROLE_STAR_CHIEF:
                    tvLevel.setText(R.string.star_inspector);
                    break;
            }
        }
    }
   public void initDot(){
        llMyLamp.removeAllViews();
        for (int i=0;i<viewArr.size();i++){
            View view=new View(getActivity());
            LinearLayout.LayoutParams layoutParams=
                    new LinearLayout.LayoutParams(CommonUtil.dip2px(getContext(),10),CommonUtil.dip2px(getContext(),10));
            if (i>0) {
                layoutParams.leftMargin = CommonUtil.dip2px(getContext(), 10);
            }
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.drawable.ic_my_dot_selector);
            llMyLamp.addView(view);
        }

   }
    @Override
    public void setRegionCoreData(RegionCore data) {
        if (data != null && data.isIsCenter()) {
            tvTotalIncome.setText(data.getTotalIncome());
            tvYesterdayIncome.setText(data.getYesterDayIncome());
            tvProvince.setText(data.getWhereRegion());
            int insertPos=2;
            itemArr.add(insertPos, new MyMenuItem(R.drawable.ic_my_operate_center, R.string.operation_center));
            menuAdapter.notifyItemInserted(insertPos);
            viewArr.add(insertPos,regionCoreView);
            pagerAdapter = new MyViewListAdapter(viewArr);
            vpMy.setAdapter(pagerAdapter);
            initDot();
        }
    }

    @Override
    public void setAuctionMsg(NestAuctionMsg data) {
        if (data != null && !TextUtils.isEmpty(data.getShowMsg())) {
            new DialogBuilder(getContext())
                    .setTitle(R.string.tip)
                    .setMessage(data.getShowMsg())
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.goto_add, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), MyAdActivity.class));
                        }
                    })
                    .create().show();
        }
    }

    @Override
    public void setMySeal(MySeal data) {
        if (data!=null){
            tvMySeal.setText(data.getCoin());
            tvSealBalance.setText(data.getMoney());
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
    @Override
    public void setMyQC(MyQC data) {
        //设置qc
        tvMyQc.setText(data.getCoin());
        tvQcBalance.setText(data.getMoney());

    }

    public void setMsgDotView() {
        if (badgeView == null)
            badgeView = new BadgeView(getActivity(), ivMessage);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.text_white));
        badgeView.setBadgeBackgroundColor(Color.WHITE);
        badgeView.setTextSize(6);
        int width = CommonUtil.dip2px(getActivity(), 8);
        badgeView.setWidth(width);
        badgeView.setHeight(width);
        badgeView.setBadgeMargin(CommonUtil.dip2px(getActivity(), 5), CommonUtil.dip2px(getActivity(), 8)); //各边间隔
        if (getPref().getBoolean(PrefUtil.MSGISNEW, false)) {
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }

    public void refreshUseRole() {
        if (isAdded()) {
            mvpPresenter.getRecommendData();
        }
    }

    public void refreshMyQc(){
        mvpPresenter.getMyQC();
    }
    public void openNestAd() {
        isShowNestAd = true;
        if (isAdded()) {
            linNestad.setVisibility(View.VISIBLE);
        }
    }
}
