package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.activity.CityActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sz.gcyh.KSHongBao.R;
import butterknife.BindView;

public class CircleFragment extends MvpFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.wv_circle)
    WebView wvCircle;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_circle;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.tab_circle);
        wvCircle.getSettings().setJavaScriptEnabled(true);
        wvCircle.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvCircle.getSettings().setLoadWithOverviewMode(true);
        wvCircle.getSettings().setSupportZoom(true);
        wvCircle.getSettings().setUseWideViewPort(true);
        wvCircle.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvCircle.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvCircle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                IntentUtils.startWebActivity(getActivity(),view.getTitle(),url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tvTitle.setText(view.getTitle());
                srlRefresh.finishRefresh();
            }
        });
        wvCircle.addJavascriptInterface(new circleInterface(), "browserController");
        wvCircle.loadUrl(CommonUtil.getTimeStampUrl(Constant.URL_TRADINGAREA));
        srlRefresh.setEnableLoadmore(false);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                wvCircle.reload();
            }
        });
    }

    private class circleInterface {
        @JavascriptInterface
        public void goCzGame() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), CityActivity.class));
                }
            });
        }
        @JavascriptInterface
        public void openWin(String openUrl){
            IntentUtils.startWebActivity(getActivity(),"",openUrl);
        }
    }
}
