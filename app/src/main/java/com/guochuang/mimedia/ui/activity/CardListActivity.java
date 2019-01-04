package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CardList;
import com.guochuang.mimedia.mvp.presenter.CardListPresenter;
import com.guochuang.mimedia.mvp.view.CardListView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.CardListAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardListActivity extends MvpActivity<CardListPresenter> implements CardListView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_card)
    RecyclerView rvCard;

    List<CardList> cardLists = new ArrayList<>();
    CardListAdapter adapter;

    @Override
    protected CardListPresenter createPresenter() {
        return new CardListPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_card_list;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_bankcard);
        rvCard.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvCard.addItemDecoration(new VerticalDecoration(this, R.drawable.bg_divide_card));
        adapter = new CardListAdapter(cardLists);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startCardAddActivity(
                        CardListActivity.this,
                        cardLists.get(position).getId(),
                        cardLists.get(position).getName(),
                        cardLists.get(position).getCardNumber(),
                        cardLists.get(position).getMobile());
            }
        });
        rvCard.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.userBankCardList();
    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(this, CardAddActivity.class), Constant.REFRESH);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.REFRESH) {
            setResult(RESULT_OK);
        }
    }

    @Override
    public void setData(List<CardList> data) {
        cardLists = data;
        adapter.setNewData(data);
    }

    @Override
    public void setError(String msg) {
        showShortToast(msg);
    }
}
