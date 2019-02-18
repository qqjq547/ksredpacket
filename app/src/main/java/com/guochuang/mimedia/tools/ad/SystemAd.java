package com.guochuang.mimedia.tools.ad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import org.json.JSONObject;

import java.util.List;

public class SystemAd {
    public Context context;

    public SystemAd(Context context) {
        this.context = context;
    }

    private void show(final ViewGroup viewGroup, String sysImaUrl, final String sysGoUrl) {
        if (TextUtils.isEmpty(sysImaUrl)) {
            return;
        }
        GlideApp.with(context).asBitmap().load(sysImaUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @android.support.annotation.Nullable Transition<? super Bitmap> transition) {
                ImageView iv = new ImageView(context);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageBitmap(resource);
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.height = (int) ((resource.getHeight() / (float) resource.getWidth()) * dm.widthPixels);
                iv.setLayoutParams(lp);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(sysGoUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                });
                viewGroup.addView(iv);
            }
        });
    }
}
