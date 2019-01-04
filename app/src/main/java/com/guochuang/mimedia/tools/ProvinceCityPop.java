package com.guochuang.mimedia.tools;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.mvp.model.Area;
import com.guochuang.mimedia.ui.adapter.MenuChildAdapter;
import com.guochuang.mimedia.ui.adapter.MenuGroupAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProvinceCityPop extends PopupWindow {

    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    @BindView(R.id.rv_child)
    RecyclerView rvChild;
    List<Area> areaArr = new ArrayList<>();
    List<String> groupArr = new ArrayList<>();
    MenuGroupAdapter groupAdapter;
    List<String> childArr = new ArrayList<>();
    MenuChildAdapter childAdapter;

    OnSelectItemListener onSelectItemListener;

    public interface OnSelectItemListener {
        void onSelectItem(int group, int child);
    }

    public ProvinceCityPop(Activity activity, List<Area> areaArr,final OnSelectItemListener onSelectItemListener) {
        super(activity);
        this.onSelectItemListener = onSelectItemListener;
        this.areaArr=areaArr;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.ppw_province_city, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        rvGroup.setLayoutManager(new LinearLayoutManager(activity, OrientationHelper.VERTICAL, false));
        rvGroup.addItemDecoration(new VerticalDecoration(activity));
        initData();
        groupAdapter = new MenuGroupAdapter(groupArr);
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                groupAdapter.setSelectPos(position);
                setChildData(position);
            }
        });
        rvGroup.setAdapter(groupAdapter);
        rvChild.setLayoutManager(new LinearLayoutManager(activity, OrientationHelper.VERTICAL, false));
        rvChild.addItemDecoration(new VerticalDecoration(activity));
        childAdapter = new MenuChildAdapter(childArr);
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                childAdapter.setSelectPos(position);
                if (onSelectItemListener != null)
                    onSelectItemListener.onSelectItem(groupAdapter.getSelectPos(), position);
                dismiss();
            }
        });
        rvChild.setAdapter(childAdapter);
        setChildData(0);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.bg_trans)));
    }
   public void initData(){
        for (Area area:areaArr){
            groupArr.add(area.getName());
        }
   }
    public void setChildData(int position){
        childArr.clear();
        for (Area.ChildrenBean bean:areaArr.get(position).getChildren()) {
            childArr.add(bean.getName());
        }
        childAdapter.setNewData(childArr);
    }
    @OnClick(R.id.lin_root)
    public void onViewClicked() {
        dismiss();
    }
}
