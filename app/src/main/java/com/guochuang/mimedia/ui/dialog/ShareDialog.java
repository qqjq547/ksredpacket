package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.glide.GlideApp;

import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class ShareDialog extends Dialog {

    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_circle)
    TextView tvCircle;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_sinaweibo)
    TextView tvSinaweibo;
    @BindView(R.id.tv_report)
    TextView tvReport;
    String title;
    String url;
    String imageUrl;
    boolean report=false;
    String imagePath;
    String content;

    OnShareResultListener onShareResultListener;
    public interface OnShareResultListener{
        void onSuccess(String platform);
        void onError(String errMsg);
        void onCancel();
    }
    OnReportListener onReportListener;
    public interface OnReportListener{
        void onReport();
    }

    public ShareDialog(@NonNull Context context, String title, String url, String imageUrl) {
        this(context);
        this.title=title;
        this.url=url;
        this.imageUrl=imageUrl;
    }

    public void setReport(boolean report, OnReportListener onReportListener){
        this.report=report;
        this.onReportListener=onReportListener;
        if (report){
            tvReport.setVisibility(View.VISIBLE);
        }else {
            tvReport.setVisibility(View.GONE);
        }
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        tvQq.setVisibility(View.GONE);
        tvSinaweibo.setVisibility(View.GONE);
    }
    public ShareDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_share, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        if (report){
            tvReport.setVisibility(View.VISIBLE);
        }else {
            tvReport.setVisibility(View.GONE);
        }
    }
    public void setOnShareResultListener(OnShareResultListener onShareResultListener) {
        this.onShareResultListener = onShareResultListener;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({
            R.id.tv_wechat,
            R.id.tv_circle,
            R.id.tv_qq,
            R.id.tv_sinaweibo,
            R.id.tv_report,
            R.id.tv_close,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                showShare(Wechat.Name);
                break;
            case R.id.tv_circle:
                showShare(WechatMoments.Name);
                break;
            case R.id.tv_qq:
                showShare(QQ.Name);
                break;
            case R.id.tv_sinaweibo:
                showShare(SinaWeibo.Name);
                break;
            case R.id.tv_report:
                if (onReportListener!=null)
                onReportListener.onReport();
                break;
            case R.id.tv_close:
                cancel();
                break;
        }
        dismiss();
    }
    public void setContent(String content){
        this.content=content;
    }

    private void showShare(final String platform) {
        if (JShareInterface.isClientValid(platform)) {
            final ShareParams shareParams = new ShareParams();
            if (!TextUtils.isEmpty(imagePath)){
                shareParams.setShareType(Platform.SHARE_IMAGE);
                shareParams.setImagePath(imagePath);
                JShareInterface.share(platform, shareParams, shareActionListener);
                return;
            }
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setTitle(title);
            if (TextUtils.isEmpty(content)){
                shareParams.setText(getContext().getString(R.string.app_name));
            }else {
                shareParams.setText(content);
            }
            shareParams.setUrl(url);
            if (TextUtils.isEmpty(imageUrl)){
                shareParams.setImageData(BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.app_icon));
                JShareInterface.share(platform, shareParams, shareActionListener);
                return;
            }
            if (TextUtils.equals(QQ.Name,platform)||TextUtils.equals(QZone.Name,platform)) {
                shareParams.setImageUrl(imageUrl);
                JShareInterface.share(platform, shareParams, shareActionListener);
            }else {
                GlideApp.with(getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                shareParams.setImageData(resource);
                                JShareInterface.share(platform, shareParams, shareActionListener);
                            }
                        });
            }
        } else {
            Toast.makeText(getContext(), R.string.uninstall_this_app, Toast.LENGTH_SHORT).show();
        }

    }

    PlatActionListener shareActionListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            LogUtil.d("onComplete");
            if (onShareResultListener!=null) {
                onShareResultListener.onSuccess(platform.getName());
            }
        }

        @Override
        public void onError(Platform platform, int i, int i1, final Throwable throwable) {
            LogUtil.d("onError:"+throwable.getMessage());
            if (onShareResultListener!=null)
            onShareResultListener.onError(throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            LogUtil.d("onCancel");
            if (onShareResultListener!=null)
           onShareResultListener.onCancel();
        }
    };


}
