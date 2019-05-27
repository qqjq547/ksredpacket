package com.guochuang.mimedia.ui.activity.info;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.tools.AdCollectionView;
import com.guochuang.mimedia.tools.UrlConfig;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Reply;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.presenter.InfoDetailPresenter;
import com.guochuang.mimedia.mvp.view.InfoDetailView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.ToastUtil;
import com.guochuang.mimedia.ui.adapter.ReplyAdapter;
import com.guochuang.mimedia.ui.dialog.InputDialog;
import com.guochuang.mimedia.ui.dialog.RedTextDialog;
import com.guochuang.mimedia.ui.dialog.ReportDialog;
import com.guochuang.mimedia.ui.dialog.ShareDialog;
import com.guochuang.mimedia.view.BadgeView;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoDetailActivity extends MvpActivity<InfoDetailPresenter> implements InfoDetailView {
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.wv_info)
    WebView wvInfo;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.rv_reply)
    RecyclerView rvReply;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.lin_bottom)
    LinearLayout linBottom;
    @BindView(R.id.nsv_content)
    NestedScrollView nsvContent;
    @BindView(R.id.lin_comment_head)
    LinearLayout linCommentHead;
    @BindView(R.id.lin_ad)
    LinearLayout linAd;

    int curPage = 1;
    List<Reply> replyItems = new ArrayList<>();
    ReplyAdapter replyAdapter;
    String articleUuid;
    String replyContent;
    BadgeView badgeView;
    InfoDetail detail;
    InputDialog inputDialog;
    Handler handler = new Handler();
    boolean fromCollect = false;

    @Override
    protected InfoDetailPresenter createPresenter() {
        return new InfoDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_info_detail;
    }

    @Override
    public void initViewAndData() {
        String title = getIntent().getStringExtra(Constant.TITLE);
        articleUuid = getIntent().getStringExtra(Constant.ARTICLEUUID);
        fromCollect = getIntent().getBooleanExtra(Constant.FROM_COLLECT, false);
        tvTitle.setText(title);
        replyAdapter = new ReplyAdapter(replyItems);
        replyAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
        replyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        replyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        rvReply.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvReply.addItemDecoration(new VerticalDecoration(this));
        rvReply.setItemAnimator(new DefaultItemAnimator());
        rvReply.setAdapter(replyAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.commentGet(curPage + 1, Constant.PAGE_SIZE, articleUuid);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.beginRead(articleUuid);
                mvpPresenter.commentGet(1, Constant.PAGE_SIZE, articleUuid);
            }
        });

        mvpPresenter.beginRead(articleUuid);
    }

    private void addAd() {
        // gdt "1108034968", "4080943860199119"
        AdCollectionView adCollectionView = new AdCollectionView(this, linAd);
        if (detail != null && detail.getSystemAdList() != null && detail.getSystemAdList().size() != 0) {
            adCollectionView.init(AdCollectionView.TYPE_BD, AdCollectionView.LOCATION_INFO, detail.getSystemAdList().get(0).getPicture(), detail.getSystemAdList().get(0).getJumpUrl());
        } else {
            adCollectionView.init(AdCollectionView.TYPE_BD, AdCollectionView.LOCATION_INFO, null, null);
        }
    }

    public void initWebView() {
        wvInfo.getSettings().setJavaScriptEnabled(true);
        wvInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvInfo.getSettings().setLoadWithOverviewMode(true);
        wvInfo.getSettings().setSupportZoom(true);
        wvInfo.getSettings().setUseWideViewPort(true);
        wvInfo.getSettings().setBuiltInZoomControls(false);
        wvInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http") && !url.startsWith("https")) {
                    try {
                        // 以下固定写法
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        InfoDetailActivity.this.startActivity(intent);
                    } catch (Exception e) {
                        // 防止没有安装的情况
                        e.printStackTrace();
                    }
                    return true;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
//                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mvpPresenter.commentGet(curPage, Constant.PAGE_SIZE, articleUuid);
//                view.loadUrl("javascript:getDataFromApp(\"" + detail.getTitle()+"=====" +detail.getContent()+"\")");
            }
        });
        wvInfo.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvInfo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void initDotView(int count) {
        if (badgeView == null)
            badgeView = new BadgeView(this, ivComment);
        badgeView.setText(String.valueOf(count));
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.text_white));
        badgeView.setBadgeBackgroundColor(Color.RED);
        badgeView.setTextSize(6);
        int width = CommonUtil.dip2px(this, 8);
        badgeView.setHeight(width);
        badgeView.setBadgeMargin(CommonUtil.dip2px(this, 5), CommonUtil.dip2px(this, 5)); //各边间隔
        if (count > 0) {
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mvpPresenter.readingGenerate(articleUuid);
        }
    };

    public void showShare(boolean isReport) {
        ShareDialog shareDialog = new ShareDialog(this, detail.getTitle(), UrlConfig.getHtmlUrl(UrlConfig.URL_INFOMATION_DETAIL) + "?id=" + articleUuid+"&userAccountUuid="+App.getInstance().getUserInfo().getUserAccountUuid(), null);
        shareDialog.setOnShareResultListener(new ShareDialog.OnShareResultListener() {
            @Override
            public void onSuccess(String platform) {
                mvpPresenter.shareAdd(articleUuid, platform);
            }

            @Override
            public void onError(String errMsg) {
                showShortToast(errMsg);
            }

            @Override
            public void onCancel() {

            }
        });
        shareDialog.setReport(isReport, new ShareDialog.OnReportListener() {
            @Override
            public void onReport() {
                showLoadingDialog(null);
                mvpPresenter.dictionaryGet(Constant.TYPE_REPORT_INFORMATION);
            }
        });
        shareDialog.show();
    }

    @Override
    public void onBackPressed() {
        mvpPresenter.endRead(articleUuid);
        handler.removeCallbacks(runnable);
        if (detail != null) {
            if (fromCollect && detail.getIsFavorited() == 0) {
                setResult(RESULT_OK, getIntent());
                finish();
                return;
            }
        }
        super.onBackPressed();
    }

    @OnClick({R.id.iv_back, R.id.iv_image, R.id.tv_reply, R.id.tv_zan, R.id.iv_comment, R.id.iv_collect, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image:
                if (detail == null)
                    return;
                showShare(true);
                break;
            case R.id.tv_reply:
                if (inputDialog == null) {
                    inputDialog = new InputDialog(this);
                    inputDialog.setOnResultListener(new InputDialog.OnResultListener() {
                        @Override
                        public void onReplyText(String content) {
                            replyContent = content;
                            if (!TextUtils.isEmpty(replyContent)) {
                                showLoadingDialog(null);
                                mvpPresenter.commentAdd(articleUuid, replyContent, 0);
                            }
                        }
                    });
                }
                inputDialog.show();
                break;
            case R.id.tv_zan:
                if (detail == null)
                    return;
                if (detail.getIsPraised() == 0) {
                    mvpPresenter.praiseAdd(articleUuid);
                } else {
                    mvpPresenter.praiseDelete(articleUuid);
                }
                break;
            case R.id.iv_comment:
                nsvContent.scrollTo(0, linCommentHead.getTop());
                break;
            case R.id.iv_collect:
                if (detail == null)
                    return;
                if (detail.getIsFavorited() == 0) {
                    mvpPresenter.favoriteAdd(articleUuid);
                } else {
                    mvpPresenter.favoriteDetele(articleUuid);
                }
                break;
            case R.id.iv_share:
                if (detail == null)
                    return;
                showShare(false);
                break;
        }
    }

    @Override
    public void setData(InfoDetail data) {
        closeLoadingDialog();
        if (data != null) {
            this.detail = data;
            addAd();
            tvZan.setText(String.format(getString(R.string.format_people_zan), data.getPraiseNumber()));
            tvZan.setSelected(data.getIsPraised() > 0);
            ivCollect.setSelected(data.getIsFavorited() > 0);
            initWebView();
            initDotView(data.getCommentNumber());
            String url = UrlConfig.getHtmlUrl(UrlConfig.URL_INFOMATION_DETAIL) + "?id=" + articleUuid + "&isApp=1";

            LogUtil.d(url);
            wvInfo.loadUrl(url);
            if (detail.getIsBonus() == 0) {
                handler.postDelayed(runnable, data.getIsBonusSecond() * 1000);
            }
        }

    }

    @Override
    public void setPageError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setReadEnd(String data) {

    }

    @Override
    public void getComment(Page<Reply> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            replyItems.clear();
        }
        replyItems.addAll(data.getDataList());
        replyAdapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void addFavorite(Integer data) {
        ivCollect.setSelected(true);
        detail.setIsFavorited(1);
        ToastUtil.showSuccess(this, getString(R.string.collect_success), R.drawable.ic_done);
    }

    @Override
    public void deleteFavorite(Boolean data) {
        ivCollect.setSelected(false);
        detail.setIsFavorited(0);
        ToastUtil.showSuccess(this, getString(R.string.cancel_collect), R.drawable.ic_done);
    }

    @Override
    public void addPraise(Integer data) {
        tvZan.setSelected(true);
        detail.setIsPraised(1);
        detail.setPraiseNumber(detail.getPraiseNumber() + 1);
        tvZan.setText(String.format(getString(R.string.format_people_zan), detail.getPraiseNumber()));

    }

    @Override
    public void deletePraise(Boolean data) {
        tvZan.setSelected(false);
        detail.setIsPraised(0);
        detail.setPraiseNumber(detail.getPraiseNumber() - 1);
        tvZan.setText(String.format(getString(R.string.format_people_zan), detail.getPraiseNumber()));
    }

    @Override
    public void addReport(Integer data) {
        closeLoadingDialog();
        showShortToast(R.string.report_success);
    }

    @Override
    public void getReportItem(List<DictionaryType> data) {
        closeLoadingDialog();
        if (data.size() > 0) {
            ReportDialog reportDialog = new ReportDialog(InfoDetailActivity.this, data);
            reportDialog.setOnResultListener(new ReportDialog.OnResultListener() {
                @Override
                public void onReport(List<DictionaryType> items, String content) {
                    showLoadingDialog(null);
                    List<String> codeArr = new ArrayList<>();
                    for (DictionaryType report : items) {
                        codeArr.add(report.getName());
                    }
                    String type = TextUtils.join(",", codeArr);
                    mvpPresenter.reportAdd(articleUuid, content, type);
                }
            });
            reportDialog.show();
        }
    }

    @Override
    public void readingGenerate(String data) {
        new RedTextDialog(this, String.format(getString(R.string.format_reading_awara), data)).show();
    }

    @Override
    public void shareAdd(Integer data) {

    }

    @Override
    public void addComment(Integer data) {
        closeLoadingDialog();
        ToastUtil.showSuccess(this, getString(R.string.post_success), R.drawable.ic_done);
        if (inputDialog != null) {
            inputDialog.clearText();
            inputDialog.dismiss();
        }
        detail.setCommentNumber(detail.getCommentNumber() + 1);
        initDotView(detail.getCommentNumber());
        Reply reply = new Reply();
        reply.setContent(replyContent);
        reply.setAvatar(App.getInstance().getUserInfo().getAvatar());
        reply.setNickName(App.getInstance().getUserInfo().getNickName());
        reply.setCommentTimeStamp(System.currentTimeMillis());
        replyAdapter.addData(0, reply);
    }

}
