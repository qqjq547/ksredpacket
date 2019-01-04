package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.SheetAdapter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class SheetDialog extends Dialog {

    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    
    SheetAdapter adapter;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    OnItemClickListener onItemClickListener;
    
    public SheetDialog(@NonNull Context context, @NonNull List<String> dataArr, final OnItemClickListener onItemClickListener) {
        super(context, R.style.dialog_bottom_full);
        this.onItemClickListener=onItemClickListener;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_sheet, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        rvMenu.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL,false));
        rvMenu.addItemDecoration(new DividerItemDecoration(context,OrientationHelper.VERTICAL));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        adapter=new SheetAdapter(dataArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position); 
                }
            }
        });
        rvMenu.setAdapter(adapter);
    }
    @OnClick({R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
