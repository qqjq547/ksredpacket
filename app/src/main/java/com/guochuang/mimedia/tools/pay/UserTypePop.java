package com.guochuang.mimedia.tools.pay;

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
import com.guochuang.mimedia.ui.adapter.MenuChildAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTypePop extends PopupWindow {

    @BindView(R.id.rv_type)
    RecyclerView rvType;

    List<String> typeArr =new ArrayList<>();
    MenuChildAdapter menuAdapter;

    OnSelectItemListener onSelectItemListener;
    public interface OnSelectItemListener{
        void onSelectItem(String type);
    }

    public UserTypePop(Activity activity, final OnSelectItemListener onSelectItemListener) {
        super(activity);
        this.onSelectItemListener=onSelectItemListener;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.ppw_user_type, null);
        setContentView(contentView);
        ButterKnife.bind(this,contentView);

        rvType.setLayoutManager(new LinearLayoutManager(activity,OrientationHelper.VERTICAL,false));
        rvType.addItemDecoration(new VerticalDecoration(activity));
        typeArr.add("粉丝");
        typeArr.add("普通代理商");
        typeArr.add("经理");
        typeArr.add("总监");
        menuAdapter =new MenuChildAdapter(typeArr);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                menuAdapter.setSelectPos(position);
                if (onSelectItemListener!=null)
                onSelectItemListener.onSelectItem(menuAdapter.getData().get(menuAdapter.getSelectPos()));
                dismiss();
            }
        });
        rvType.setAdapter(menuAdapter);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.bg_trans)));
    }

    @OnClick(R.id.lin_root)
    public void onViewClicked() {
        dismiss();
    }
}
