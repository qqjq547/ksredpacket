package com.guochuang.mimedia.tools.glide;

import android.content.Context;
import android.widget.ImageView;

import com.sz.gcyh.KSHongBao.R;

import java.io.File;

/**
 * Created by Administrator on 2017-03-06 0006.
 */

public class GlideImgManager {
    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        GlideApp.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv,int drawableId) {
        //原生 API
        GlideApp.with(context).load(url).error(drawableId).placeholder(drawableId).into(iv);
    }
    public static void loadImageNoHolder(Context context, String url, ImageView iv) {
        //原生 API
        GlideApp.with(context).load(url).into(iv);
    }
    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        GlideApp.with(context).load(url).into(iv);
    }
    public static void loadGifImage(Context context, String url, ImageView iv) {
        GlideApp.with(context).asGif().load(url).into(iv);
    }
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        GlideApp.with(context).load(url).transform(new CircleTransformation()).into(iv);
    }
    public static void loadCircleImage(Context context, String url, ImageView iv,int borderWidth, int borderColor) {
        GlideApp.with(context).load(url).transform(new CircleTransformation(borderWidth,borderColor)).into(iv);
    }
    public static void loadCircleImage(Context context, String url, ImageView iv,int drawableId) {
        GlideApp.with(context).load(url).error(drawableId).placeholder(drawableId).transform(new CircleTransformation()).into(iv);
    }
    public static void loadCornerImage(Context context, String url, ImageView iv,int radius) {
        GlideApp.with(context).load(url).transform(new RadiusTransformation(context,radius)).into(iv);
    }
    public static void loadCornerImage(Context context, String url, ImageView iv,int radius,boolean centerCrop) {
        GlideApp.with(context).load(url).transform(new RadiusTransformation(context,radius,centerCrop)).into(iv);
    }

    public static void loadImage(Context context, final File file, final ImageView imageView) {
        GlideApp.with(context)
                .load(file)
                .into(imageView);

    }
    public static void loadCornerImage(Context context, final File file, final ImageView imageView,int radius) {
        GlideApp.with(context)
                .load(file)
                .transform(new RadiusTransformation(context,radius))
                .into(imageView);

    }
    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        GlideApp.with(context)
                .load(resourceId)
                .into(imageView);
    }
}
