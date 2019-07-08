package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.BottomWithdrawalAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomWithdrawalDialog extends Dialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv)
    RecyclerView rv;

    BottomWithdrawalAdapter bottomWithdrawalAdapter;
    private int checkItem;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setCheckItem(int checkItem) {
        this.checkItem = checkItem;
    }

    public BottomWithdrawalDialog(@NonNull Context context, List<KsbTransfer.PayTypeListBean> list,
                                  final OnItemClickListener onItemClickListener) {
        super(context, R.style.dialog_bottom);
        View view = View.inflate(context, R.layout.dialog_bottom_item, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        tvTitle.setText(context.getResources().getString(R.string.ksb_transfer_type_title));
        ivRight.setImageResource(R.drawable.ic_black_cross);
        rv.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL, false));
        rv.addItemDecoration(new VerticalDecoration(context));
        bottomWithdrawalAdapter = new BottomWithdrawalAdapter(list);
        bottomWithdrawalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onItemClickListener.onClick(position);
                BottomWithdrawalDialog.this.dismiss();
            }
        });
        rv.setAdapter(bottomWithdrawalAdapter);

        //默认设置
        setCanceledOnTouchOutside(true);
        Window window = BottomWithdrawalDialog.this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    @Override
    public void show() {
        bottomWithdrawalAdapter.setCheckItem(checkItem);
        bottomWithdrawalAdapter.notifyDataSetChanged();
        super.show();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    @OnClick(R.id.iv_right)
    public void onViewClicked() {
        dismiss();
    }
}
