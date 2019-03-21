package com.guochuang.mimedia.ui.activity.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.tools.AdCollectionView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.ui.activity.treasure.MyTreasureActivity;
import com.guochuang.mimedia.ui.activity.user.TradePwdActivity;
import com.guochuang.mimedia.ui.activity.user.UpgradeAgentActivity;
import com.guochuang.mimedia.ui.dialog.ShareDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends MvpActivity {
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
    AdCollectionView adCollectionView;
    String headRightType;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initViewAndData() {
        String title = getIntent().getStringExtra(Constant.TITLE);
        String url = getIntent().getStringExtra(Constant.URL);
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
                if (TextUtils.equals(url,"jxaction://close")){
                    finish();
                }else {
                    IntentUtils.startWebActivity(WebActivity.this,view.getTitle(),url);
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
        wvContent.addJavascriptInterface(new JSInterface(this),"browserController");
        wvContent.loadUrl(CommonUtil.getTimeStampUrl(url));
        srlRefresh.setEnableLoadmore(false);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                wvContent.reload();
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (headRightType.equals("rule")){//蜂窝规则
                    IntentUtils.startWebActivity(WebActivity.this,getString(R.string.hongycomb_agreement),Constant.URL_HONYCOMB_RULE);
                }else if(headRightType.equals("share")){//分享夺宝
                    wvContent.loadUrl("javascript:slef.shareDetails()");
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (wvContent.canGoBack()){
            wvContent.goBack();
        }else {
            super.onBackPressed();
        }
    }
    public class JSInterface{
        public JSInterface(Activity activity) {
        }
        @JavascriptInterface
        public void openWin(final String openUrl){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d("openUrl="+openUrl);
                    IntentUtils.startWebActivity(WebActivity.this,"",openUrl);
                }
            });

        }
        @JavascriptInterface
        public void upgradeAgent(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WebActivity.this,UpgradeAgentActivity.class));
                }
            });
        }
        @JavascriptInterface
        public void invitation(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WebActivity.this,ShareActivity.class));
                }
            });
        }
        @JavascriptInterface
        public void activation(final double money,final int payNumber){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IntentUtils.startPurchaseActivity(WebActivity.this,Constant.TYPE_PURCHASE_HONEYCOMB,payNumber,String.valueOf(money));
                }
            });
        }
        @JavascriptInterface
        public void playVideo(){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adCollectionView!=null){
                        adCollectionView.showRewardVideoAD();
                        return;
                    }
                    showLoadingDialog(R.string.loading_video);
                    adCollectionView = new AdCollectionView(WebActivity.this, null);
                    adCollectionView.setAdVideoInitListener(new AdCollectionView.AdVideoInitListener() {
                        @Override
                        public void onInit() {
                            closeLoadingDialog();
                            adCollectionView.showRewardVideoAD();
                        }

                        @Override
                        public void onError(String msg) {
                            closeLoadingDialog();
                            LogUtil.d("onError");
                        }
                    });
                    adCollectionView.setAdVideoListener(new AdCollectionView.AdVideoListener() {
                        @Override
                        public void onVideoFinish() {
                            wvContent.loadUrl("javascript:slef.playFinished()");
                        }

                        @Override
                        public void onVideoError(String msg) {
                            LogUtil.d("onVideoError");
                            showShortToast(msg);
                        }
                    });
                    adCollectionView.init(AdCollectionView.TYPE_VUNGLE, AdCollectionView.LOCATION_CELLULAR, null, null);
                }
            });
        }
        @JavascriptInterface
        public void rule(final String type,final String title){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvText.setText(title);
                    headRightType =type;
                }

            });
        }
        @JavascriptInterface
        public void goBackLastPage(){
            finish();
        }
        @JavascriptInterface
        public void forgetPassword(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WebActivity.this,TradePwdActivity.class));
                }
            });
        }
        @JavascriptInterface
        public void payment(final double money,final int number,final long snatchId,final int unitPrice){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IntentUtils.startPurchaseActivity(WebActivity.this,Constant.TYPE_PURCHASE_SNATCH,snatchId,unitPrice,number,String.valueOf(money));
                }
            });
        }
        @JavascriptInterface
        public void goTreasure(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WebActivity.this,MyTreasureActivity.class));
                }
            });
        }
        @JavascriptInterface
        public void rightTitle(final String title, final String url){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvText.setText(title);
                    tvText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentUtils.startWebActivity(WebActivity.this,title,url);
                        }
                    });
                }
            });
        }
        @JavascriptInterface
        public void copyText(final String content){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CommonUtil.copyMsg(WebActivity.this,content);
                    showShortToast(R.string.copy_success);
                }
            });
        }
        @JavascriptInterface
        public void shareDetails(final String shareImg,final String shareUrl,final String shareTitle,final String shareText){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShareDialog shareDialog=new ShareDialog(WebActivity.this,shareTitle,shareUrl,shareImg);
                    shareDialog.setContent(shareText);
                    shareDialog.onlyWechatPlatom();
                    shareDialog.setOnShareResultListener(new ShareDialog.OnShareResultListener() {
                        @Override
                        public void onSuccess(String platform) {

                        }

                        @Override
                        public void onError(String errMsg) {
                            showShortToast(errMsg);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    shareDialog.show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.REQUEST_PURCHASE:
                    wvContent.loadUrl("javascript:slef.activationSuccess()");
                    wvContent.reload();
                    break;
                    default:
                        wvContent.reload();
                        break;
            }
        }
    }
}
