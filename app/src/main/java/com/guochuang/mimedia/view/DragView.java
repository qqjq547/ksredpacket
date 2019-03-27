package com.guochuang.mimedia.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.sz.gcyh.KSHongBao.R;

import java.util.Random;

public class DragView extends View {

    private Context mContext;
    private int mWidth;
    private Bitmap mGrayBitmap;
    private Bitmap mSrcBitmap;
    private Bitmap mCenterBitmap;
    private int mRandomValue;
    private int currentPosition;
    private int mSDrcId;
    private int mGrayId;
    private int mCenterId;
    private int mEffectiveWidth;
    private int mGrayPosition;
    private boolean isShowSuccessAnim = false;
    //验证失败的闪烁动画
    private ValueAnimator mFailAnim;
    private ValueAnimator mSuccessAnim;
    private int mSuccessAnimOffset;//动画的offset
    private Paint mSuccessPaint;//画笔
    private Path mSuccessPath;//成功动画 平行四边形Path

    //是否绘制滑块（验证失败闪烁动画用）
    private boolean isDrawMask;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragView);

        mSDrcId = typedArray.getResourceId(R.styleable.DragView_src_Imag, mSDrcId);
        BitmapDrawable srcDrawable = (BitmapDrawable) getResources().getDrawable(mSDrcId);
        mSrcBitmap = srcDrawable.getBitmap();

        mGrayId = typedArray.getResourceId(R.styleable.DragView_gray_Imag, -1);
        BitmapDrawable garyDrawable = (BitmapDrawable) getResources().getDrawable(mGrayId);
        mGrayBitmap = garyDrawable.getBitmap();

        mCenterId = typedArray.getResourceId(R.styleable.DragView_center_Imag, -1);
        BitmapDrawable centerDrawable = (BitmapDrawable) getResources().getDrawable(mCenterId);
        mCenterBitmap = centerDrawable.getBitmap();

        typedArray.recycle();

        randomValue();


    }

    private void createMatchAnim() {
        mFailAnim = ValueAnimator.ofFloat(0, 1);
        mFailAnim.setDuration(100)
                .setRepeatCount(4);
        mFailAnim.setRepeatMode(ValueAnimator.REVERSE);
        //失败的时候先闪一闪动画 斗鱼是 隐藏-显示 -隐藏 -显示
        mFailAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        mFailAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue < 0.5f) {
                    isDrawMask = false;
                } else {
                    isDrawMask = true;
                }
                invalidate();
            }
        });

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        mSuccessAnim = ValueAnimator.ofInt(mWidth + width, 0);
        mSuccessAnim.setDuration(500);
        mSuccessAnim.setInterpolator(new FastOutLinearInInterpolator());
        mSuccessAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSuccessAnimOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mSuccessAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShowSuccessAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowSuccessAnim = false;
//                isMatchMode = false;
            }
        });
        mSuccessPaint = new Paint();
        mSuccessPaint.setShader(new LinearGradient(0, 0, width / 2 * 3, mSrcBitmap.getHeight(), new int[]{
                0x00ffffff, 0x88ffffff}, new float[]{0, 0.5f},
                Shader.TileMode.MIRROR));
        //模仿斗鱼 是一个平行四边形滚动过去
        mSuccessPath = new Path();
        mSuccessPath.moveTo(0, 0);
        mSuccessPath.rLineTo(width, 0);
        mSuccessPath.rLineTo(width / 2, mSrcBitmap.getHeight());
        mSuccessPath.rLineTo(-width, 0);
        mSuccessPath.close();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createMatchAnim();
    }

    private void randomValue() {

        Random random = new Random(System.currentTimeMillis());
        mRandomValue = random.nextInt(100);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        setMeasuredDimension(mWidth, mSrcBitmap.getHeight() + paddingTop + paddingBottom);
        //有效長度[getPaddingLeft()      getPaddingLeft()+  effectiveWidth]  [0   100]
        mEffectiveWidth = mWidth - mGrayBitmap.getWidth() - getPaddingRight() - getPaddingLeft();

        mGrayPosition = getPaddingLeft() + (mRandomValue * (mEffectiveWidth / 100));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mCenterBitmap, mWidth / 2 - mCenterBitmap.getWidth() / 2, getHeight() / 2 - mCenterBitmap.getHeight() / 2, null);

        canvas.drawBitmap(mGrayBitmap, mGrayPosition, getPaddingTop(), null);

        canvas.drawBitmap(mSrcBitmap, getPaddingLeft() + currentPosition, getPaddingTop(), null);

        //验证成功，白光扫过的动画，这一块动画感觉不完美，有提高空间
        if (isShowSuccessAnim) {
            canvas.translate(mSuccessAnimOffset, 0);
            canvas.drawPath(mSuccessPath, mSuccessPaint);
        }

        //绘制滑块
        // isDrawMask  绘制失败闪烁动画用
        if (null != mSrcBitmap && isDrawMask) {
            // 先绘制阴影
//            canvas.drawBitmap(mMaskShadowBitmap, -mCaptchaX + mDragerOffset, 0, mMaskShadowPaint);
            canvas.drawBitmap(mSrcBitmap, getPaddingLeft() + currentPosition,getPaddingTop(), null);
        }


    }


    /**
     * 处理手指在屏幕上的事件
     *
     * @param event
     * @return
     */
    float dowmX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击在滑块区域
                dowmX = event.getX();
                if (dowmX > mSrcBitmap.getWidth()) {
                    //不处理事件
                    return false;
                }


                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动
                float moveX = event.getX();
                currentPosition = (int) moveX;
                if (moveX < 0) {
                    currentPosition = 0;
                }
                if (moveX > mWidth - getPaddingRight() - mSrcBitmap.getWidth()) {
                    currentPosition = mWidth - getPaddingRight() - mSrcBitmap.getWidth();
                }


                break;
            case MotionEvent.ACTION_UP:
                currentPosition = 0;
                //计算两个图标是否重合
                float upX = event.getX();

                if (mGrayPosition - 5 < upX && upX < mGrayPosition + 5) {
                    currentPosition = (int) upX;
                    isShowSuccessAnim = true;
                    mSuccessAnim.start();
                    if (mOnCheckLisenter != null) {
                        mOnCheckLisenter.checkSuccess();
                        Log.e("onTouchEvent: ", "check sceuss");
                    }

                } else {
                    mFailAnim.start();
                }


                break;
        }
        invalidate();

        return true;
    }


    OnCheckLisenter mOnCheckLisenter;

    public void setOnCheckLisenter(OnCheckLisenter onCheckLisenter) {
        mOnCheckLisenter = onCheckLisenter;
    }


    public interface OnCheckLisenter {
        void checkSuccess();
    }
}
