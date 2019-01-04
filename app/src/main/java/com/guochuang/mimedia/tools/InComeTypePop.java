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
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.MenuChildAdapter;
import com.guochuang.mimedia.ui.adapter.MenuGroupAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InComeTypePop extends PopupWindow {
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    @BindView(R.id.rv_child)
    RecyclerView rvChild;

    List<String> groupArr=new ArrayList<>();
    MenuGroupAdapter groupAdapter;
    List<String> childArr=new ArrayList<>();
    MenuChildAdapter childAdapter;
    OnSelectItemListener onSelectItemListener;
    List<DictionaryType> typeList;
    public interface OnSelectItemListener{
        void onSelectItem(int parentPos,int childPos);
    }

    public InComeTypePop(Activity activity, final List<DictionaryType> typeList, final OnSelectItemListener onSelectItemListener) {
        super(activity);
        this.typeList=typeList;
        this.onSelectItemListener=onSelectItemListener;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.ppw_income_type, null);
        setContentView(contentView);
        ButterKnife.bind(this,contentView);
        rvGroup.setLayoutManager(new LinearLayoutManager(activity,OrientationHelper.VERTICAL,false));
        rvGroup.addItemDecoration(new VerticalDecoration(activity));
        for (DictionaryType type:typeList){
            groupArr.add(type.getName());
        }
        groupAdapter=new MenuGroupAdapter(groupArr);
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                groupAdapter.setSelectPos(position);
                setChildItem(position);
//                if (typeList.get(position).getChildren()==null){
//                    if (onSelectItemListener!=null)
//                        onSelectItemListener.onSelectItem(groupAdapter.getSelectPos(),childAdapter.getSelectPos());
//                    dismiss();
//                }
            }
        });
        rvGroup.setAdapter(groupAdapter);
        rvChild.setLayoutManager(new LinearLayoutManager(activity,OrientationHelper.VERTICAL,false));
        rvChild.addItemDecoration(new VerticalDecoration(activity));
        childAdapter=new MenuChildAdapter(childArr);
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                childAdapter.setSelectPos(position);
                if (onSelectItemListener!=null)
                onSelectItemListener.onSelectItem(groupAdapter.getSelectPos(),childAdapter.getSelectPos());
                dismiss();
            }
        });
        rvChild.setAdapter(childAdapter);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.bg_trans)));
    }
    public void setChildItem(int parentPos){
        childArr.clear();
        if (typeList.get(parentPos).getChildren()!=null){
            for (DictionaryType.ChildrenBean bean:typeList.get(parentPos).getChildren()){
                childArr.add(bean.getName());
            }
        }
        childAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.lin_root)
    public void onViewClicked() {
        dismiss();
    }
}
