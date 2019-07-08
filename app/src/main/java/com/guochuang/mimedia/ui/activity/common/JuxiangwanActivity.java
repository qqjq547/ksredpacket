package com.guochuang.mimedia.ui.activity.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.JxwUserInfoUrl;
import com.guochuang.mimedia.mvp.presenter.JuxiangwanPresenter;
import com.guochuang.mimedia.mvp.view.JuxiangwanView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.SystemUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class JuxiangwanActivity extends MvpActivity<JuxiangwanPresenter> implements JuxiangwanView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;

    JxwUserInfoUrl jxwUserInfoUrl;
    boolean isFirstLoad=true;

    @Override
    protected JuxiangwanPresenter createPresenter() {
        return new JuxiangwanPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initViewAndData() {
        jxwUserInfoUrl = (JxwUserInfoUrl)getIntent().getSerializableExtra(Constant.DATA);
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
                IntentUtils.startWebActivity(JuxiangwanActivity.this,view.getTitle(),url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (wvContent.getProgress() == 100) {
                    tvTitle.setText(view.getTitle());
                    srlRefresh.finishRefresh();
                    if (isFirstLoad) {
                        mvpPresenter.addStatistics(jxwUserInfoUrl.getUtoken(), SystemUtil.getDeviceId(), Constant.JXW_FROM, url);
                        isFirstLoad=false;
                    }
                }
            }
        });
        wvContent.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                downloadByBrowser(url);
            }
        });
        wvContent.loadUrl(CommonUtil.getTimeStampUrl(jxwUserInfoUrl.getTaskUrl()));
        srlRefresh.setEnableLoadmore(false);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                wvContent.reload();
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Boolean data) {

    }

    @Override
    public void setError(String msg) {

    }
    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
