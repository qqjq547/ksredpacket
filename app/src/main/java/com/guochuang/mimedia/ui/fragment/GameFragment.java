package com.guochuang.mimedia.ui.fragment;

import android.annotation.SuppressLint;
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

public class GameFragment extends MvpFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.wv_game)
    WebView wvGame;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_game;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.tab_game);
        wvGame.getSettings().setJavaScriptEnabled(true);
        wvGame.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvGame.getSettings().setLoadWithOverviewMode(true);
        wvGame.getSettings().setSupportZoom(true);
        wvGame.getSettings().setUseWideViewPort(true);
        wvGame.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvGame.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvGame.setWebViewClient(new WebViewClient() {
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
        wvGame.addJavascriptInterface(new GameInterface(), "browserController");
        wvGame.loadUrl(CommonUtil.getTimeStampUrl(Constant.URL_MIWAN));
        srlRefresh.setEnableLoadmore(false);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                wvGame.reload();
            }
        });
    }

    private class GameInterface {
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
        public void openWin(final String openUrl){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IntentUtils.startWebActivity(getActivity(),"",openUrl);
                }
            });

        }
    }
}
