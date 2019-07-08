package com.guochuang.mimedia.ui.activity.user;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NameAuthGet;
import com.guochuang.mimedia.mvp.presenter.IdentifyResultPresenter;
import com.guochuang.mimedia.mvp.view.IdentifyResultView;

import butterknife.BindView;
import butterknife.OnClick;

public class IdentifyResultActivity extends MvpActivity<IdentifyResultPresenter> implements IdentifyResultView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.iv_inreview)
    ImageView ivInreview;


    @Override
    protected IdentifyResultPresenter createPresenter() {
        return new IdentifyResultPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_identify_result;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.identification);
        etName.setFocusableInTouchMode(false);
        etNumber.setFocusableInTouchMode(false);
        showLoadingDialog(null);
        mvpPresenter.userNameAuthGet();
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void setData(NameAuthGet data) {
        closeLoadingDialog();
        if (data!=null){
            etName.setText(data.getRealName());
            etNumber.setText(data.getIdCard());
            if (data.getAuditStatus()==0){
                ivInreview.setVisibility(View.VISIBLE);
            }else {
                ivInreview.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}
