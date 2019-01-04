package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.ReportAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class ReportDialog extends Dialog {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.et_content)
    EditText etContent;

    List<DictionaryType> itemArr = new ArrayList<>();
    ReportAdapter reportAdapter;

    OnResultListener onResultListener;
    public interface OnResultListener{
        void onReport(List<DictionaryType> items, String content);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public ReportDialog(@NonNull Context context, List<DictionaryType> item) {
        super(context, R.style.dialog_bottom_full);
        this.itemArr = item;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_report, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rvList.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL, false));
        rvList.addItemDecoration(new VerticalDecoration(context));
        reportAdapter = new ReportAdapter(itemArr);
        reportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                reportAdapter.onClick(position);
            }
        });
        rvList.setAdapter(reportAdapter);
    }


    @OnClick({R.id.tv_cancel, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ensure:
                List<DictionaryType> selectItem=reportAdapter.getSelectItem();
                String content=etContent.getText().toString().trim();
                if (selectItem.size()>0||!TextUtils.isEmpty(content)){
                    onResultListener.onReport(selectItem,content);
                    dismiss();
                }else {
                    Toast.makeText(getContext(),R.string.pls_select_report_content,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
