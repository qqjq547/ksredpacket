package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.view.CountDownView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 蜂窝差看链接
 */
public class FengWoLinkWebViewActivity extends MvpActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.count_down_view)
    CountDownView mCountDownView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    boolean hasRefresh = true;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_fengwolinkwebview;
    }


    @Override
    public void initViewAndData() {
        mCountDownView.start();
        String title = getIntent().getStringExtra(Constant.TITLE);
        String url = getIntent().getStringExtra(Constant.URL);
        hasRefresh = getIntent().getBooleanExtra(Constant.HAS_REFRESH, true);
        srlRefresh.setEnableRefresh(hasRefresh);
        tvTitle.setText(title);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvContent.getSettings().setLoadWithOverviewMode(true);
        wvContent.getSettings().setSupportZoom(true);
        wvContent.getSettings().setUseWideViewPort(true);
        wvContent.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvContent.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (TextUtils.equals(url, "jxaction://close")) {
                    finish();
                } else {
                    return super.shouldOverrideUrlLoading(view,url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (wvContent.getProgress() == 100) {
                    tvTitle.setText(view.getTitle());
                    srlRefresh.finishRefresh();
                }
            }
        });
        wvContent.loadUrl(CommonUtil.getTimeStampUrl(url));
        srlRefresh.setEnableLoadmore(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                wvContent.reload();
            }
        });
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
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra(Constant.COUNTDOWNTIME,mCountDownView.getCurrentTime());
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }





}
