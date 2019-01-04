package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.KsbDetailsTypeAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KsbDetailsTypePop extends PopupWindow {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.lin_root)
    View linRoot;

    KsbDetailsTypeAdapter ksbDetailsTypeAdapter;

    @OnClick(R.id.lin_root)
    public void onViewClicked() {
        dismiss();
    }

    public interface OnSelectItemListener {
        void onSelectItem(int position);
    }

    public KsbDetailsTypePop(Activity activity, List<String> ksbTypes, final OnSelectItemListener onSelectItemListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.ppw_ksb_details_type, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        rv.setLayoutManager(new GridLayoutManager(activity, 3));
        rv.addItemDecoration(new GridItemDecoration(3,5,true));
        ksbDetailsTypeAdapter = new KsbDetailsTypeAdapter(ksbTypes);
        ksbDetailsTypeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        ksbDetailsTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onSelectItemListener.onSelectItem(position);
                dismiss();
            }
        });
        rv.setAdapter(ksbDetailsTypeAdapter);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
