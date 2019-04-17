package com.guochuang.mimedia.ui.activity.city;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Area;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.mvp.presenter.CityBidHallPresenter;
import com.guochuang.mimedia.mvp.view.CityBidHallView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.ProvinceCityPop;
import com.guochuang.mimedia.ui.adapter.CityBidHallAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CityBidHallActivity extends MvpActivity<CityBidHallPresenter> implements CityBidHallView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_city_bid_hall_query_cancel)
    TextView tvCityBidHallQueryCancel;
    @BindView(R.id.tv_city_bid_hall_price)
    TextView tvCityBidHallPrice;
    @BindView(R.id.tv_city_bid_hall_screen_city)
    TextView tvCityBidHallScreenCity;
    @BindView(R.id.rv_city_bid_hall)
    RecyclerView rvCityBidHall;
    @BindView(R.id.rv_city_bid_hall_refresh)
    SmartRefreshLayout rvCityBidHallRefresh;
    @BindView(R.id.et_city_bid_hall_query)
    EditText etCityBidHallQuery;
    @BindView(R.id.tv_city_bid_hall_hot_city)
    TextView tvCityBidHallHotCity;
    @BindView(R.id.tv_city_bid_hall_no_land)
    TextView tvCityBidHallNoLand;

    CityBidHallAdapter cityBidHallAdapter;
    ProvinceCityPop provinceCityPop;
    int hotAndNone = 0;
    int priceType = 0;
    List<Area> areas = new ArrayList<>();

    int currentPage = 1;
    List<CityBidHall> cityBidHalls = new ArrayList<>();
    /**
     * 默认初进：0
     * 输入搜索：1
     * 筛选搜索：2
     */
    int searchType = 0;
    int hottest = 0;
    int dereliction = 0;
    String province;
    String city;

    @Override
    protected CityBidHallPresenter createPresenter() {
        return new CityBidHallPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_city_bid_hall;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.city_bid_hall_title));
        tvText.setText(getResources().getString(R.string.rule));
        cityBidHallAdapter = new CityBidHallAdapter(cityBidHalls);
        cityBidHallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startCityDetailActivity(CityBidHallActivity.this, cityBidHallAdapter.getData().get(position).getRegionId());
            }
        });
        cityBidHallAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
        cityBidHallAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvCityBidHall.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvCityBidHall.setItemAnimator(new DefaultItemAnimator());
        rvCityBidHall.setAdapter(cityBidHallAdapter);
        rvCityBidHallRefresh.setEnableRefresh(true);
        rvCityBidHallRefresh.setEnableLoadmore(true);
        rvCityBidHallRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPage++;
                seartch();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                seartch();
            }
        });
        etCityBidHallQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = etCityBidHallQuery.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)) {
                        tvCityBidHallQueryCancel.callOnClick();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
        mvpPresenter.getRegion();
        tvCityBidHallHotCity.callOnClick();
    }

    private void seartch() {
        switch (hotAndNone) {
            case 0:
                hottest = 0;
                dereliction = 0;
                break;
            case 1:
                hottest = 1;
                dereliction = 0;
                break;
            case 2:
                hottest = 0;
                dereliction = 1;
                break;
        }
        switch (searchType) {
            case 0:
                mvpPresenter.userBidCityHall(
                        currentPage,
                        Constant.PAGE_SIZE,
                        getPref().getLatitude(),
                        getPref().getLongitude(),
                        "",
                        "",
                        "",
                        hottest,
                        dereliction,
                        priceType
                );
                break;
            case 1:
                mvpPresenter.userBidCityHall(currentPage,
                        Constant.PAGE_SIZE,
                        null,
                        null,
                        "",
                        "",
                        etCityBidHallQuery.getText().toString(),
                        hottest,
                        dereliction,
                        priceType
                );
                break;
            case 2:
                mvpPresenter.userBidCityHall(currentPage,
                        Constant.PAGE_SIZE,
                        null,
                        null,
                        province,
                        city,
                        "",
                        hottest,
                        dereliction,
                        priceType
                );
                break;
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_city_bid_hall_query_cancel, R.id.tv_city_bid_hall_price, R.id.tv_city_bid_hall_screen_city, R.id.tv_city_bid_hall_hot_city, R.id.tv_city_bid_hall_no_land, R.id.et_city_bid_hall_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                IntentUtils.startImageActivity(this, Constant.URL_REGION_RULE);
                break;
            case R.id.tv_city_bid_hall_query_cancel:
                if (provinceCityPop != null && provinceCityPop.isShowing()) {
                    provinceCityPop.dismiss();
                }
                CommonUtil.hideInput(this, etCityBidHallQuery);
                if (TextUtils.isEmpty(etCityBidHallQuery.getText().toString())) {
                    showShortToast(getResources().getString(R.string.city_bid_hall_query_hint));
                    return;
                }
                searchType = 1;
                currentPage = 1;
                seartch();
                break;
            case R.id.tv_city_bid_hall_hot_city:
                if (provinceCityPop != null && provinceCityPop.isShowing()) {
                    provinceCityPop.dismiss();
                    tvCityBidHallScreenCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_city_bid_hall_screen_off);
                    tvCityBidHallScreenCity.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                }
                switch (hotAndNone) {
                    case 0:
                        hotAndNone = 1;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_drak_black));
                        seartch();
                        break;
                    case 1:
                        hotAndNone = 0;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_drak_black));
                        seartch();
                        break;
                    case 2:
                        hotAndNone = 1;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_drak_black));
                        seartch();
                        break;
                }
                break;
            case R.id.tv_city_bid_hall_no_land:
                if (provinceCityPop != null && provinceCityPop.isShowing()) {
                    provinceCityPop.dismiss();
                    tvCityBidHallScreenCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_city_bid_hall_screen_off);
                    tvCityBidHallScreenCity.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                }
                switch (hotAndNone) {
                    case 0:
                        hotAndNone = 2;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        seartch();
                        break;
                    case 1:
                        hotAndNone = 2;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        seartch();
                        break;
                    case 2:
                        hotAndNone = 0;
                        tvCityBidHallHotCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                        tvCityBidHallNoLand.setTextColor(getResources().getColor(R.color.text_drak_black));
                        seartch();
                        break;
                }
                break;
            case R.id.tv_city_bid_hall_price:
                if (provinceCityPop != null && provinceCityPop.isShowing()) {
                    provinceCityPop.dismiss();
                    tvCityBidHallScreenCity.setTextColor(getResources().getColor(R.color.text_drak_black));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_city_bid_hall_screen_off);
                    tvCityBidHallScreenCity.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                }
                switch (priceType) {
                    case 0:
                        priceType = 2;
                        tvCityBidHallPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_city_bid_hall_price_up), null);
                        tvCityBidHallPrice.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        seartch();
                        break;
                    case 2:
                        priceType = 1;
                        tvCityBidHallPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_city_bid_hall_price_down), null);
                        tvCityBidHallPrice.setTextColor(getResources().getColor(R.color.text_city_yellow));
                        seartch();
                        break;
                    case 1:
                        priceType = 0;
                        tvCityBidHallPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_city_bid_hall_price_defult), null);
                        tvCityBidHallPrice.setTextColor(getResources().getColor(R.color.text_drak_black));
                        seartch();
                        break;
                }
                break;
            case R.id.tv_city_bid_hall_screen_city:
                if (areas.size() > 0) {
                    showCityPop();
                } else {
                    showLoadingDialog(null);
                    mvpPresenter.getRegion();
                }
                break;
            case R.id.et_city_bid_hall_query:
                if (provinceCityPop != null && provinceCityPop.isShowing()) {
                    provinceCityPop.dismiss();
                }
                break;
        }
    }

    public void showCityPop() {
        if (provinceCityPop == null) {
            provinceCityPop = new ProvinceCityPop(this, areas, new ProvinceCityPop.OnSelectItemListener() {
                @Override
                public void onSelectItem(int group, int child) {
                    searchType = 2;
                    CityBidHallActivity.this.province = areas.get(group).getName();
                    CityBidHallActivity.this.city = areas.get(group).getChildren().get(child).getName();
                    seartch();
                    provinceCityPop.dismiss();
//                            showShortToast("group="+group+",child="+child);
//                            tvTitle.setText(child);
                }
            });
        }
        if (provinceCityPop.isShowing()) {
            provinceCityPop.dismiss();
            tvCityBidHallScreenCity.setTextColor(getResources().getColor(R.color.text_drak_black));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_city_bid_hall_screen_off);
            tvCityBidHallScreenCity.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            provinceCityPop.showAsDropDown(tvCityBidHallScreenCity);
            tvCityBidHallScreenCity.setTextColor(getResources().getColor(R.color.text_city_yellow));
            Drawable drawable = getResources().getDrawable(R.drawable.ic_city_bid_hall_screen_on);
            tvCityBidHallScreenCity.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    @Override
    public void setArea(List<Area> data) {
        closeLoadingDialog();
        if (data != null && data.size() > 0) {
//            showCityPop();
            areas.addAll(data);
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setCityData(Page<CityBidHall> data) {
        rvCityBidHallRefresh.finishRefresh();
        rvCityBidHallRefresh.finishLoadmore();
        if (data != null) {
            currentPage = data.getCurrentPage();
            if (currentPage == 1) {
                cityBidHalls.clear();
            }
            cityBidHalls.addAll(data.getDataList());
            cityBidHallAdapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                rvCityBidHallRefresh.setEnableLoadmore(false);
            } else {
                rvCityBidHallRefresh.setEnableLoadmore(true);
            }
        }else {
            currentPage=1;
            cityBidHalls.clear();
            cityBidHallAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setCityError(String msg) {
        rvCityBidHallRefresh.finishRefresh();
        rvCityBidHallRefresh.finishLoadmore();
        showShortToast(msg);
    }

}
