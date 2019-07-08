package com.guochuang.mimedia.tools.glide;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;

/**
 * @author by sunfusheng on 2017/6/6.
 */
public class CircleTransformation extends BitmapTransformation {
    private final String ID = getClass().getName();
    private Paint mBorderPaint;
    private float mBorderWidth;

    public CircleTransformation() {
        super();
    }
    public CircleTransformation(int borderWidth, int borderColor) {
        super();
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;

        Bitmap square = Bitmap.createBitmap(toTransform, x, y, size, size);
        Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return circle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CircleTransformation) {
            return this == obj;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode());
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID.getBytes(CHARSET));
    }
}
