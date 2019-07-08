package com.guochuang.mimedia.ui.activity.common;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageActivity extends MvpActivity {

    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.iv_back_white)
    ImageView ivBackWhite;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_image;
    }

    @Override
    public void initViewAndData() {
        String url = getIntent().getStringExtra(Constant.URL);
        CommonUtil.initH5WebView(this, wvContent);
        wvContent.loadUrl(CommonUtil.getTimeStampUrl(url));
    }

    @OnClick({R.id.iv_back_white})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back_white:
                onBackPressed();
                break;
        }
    }

}
