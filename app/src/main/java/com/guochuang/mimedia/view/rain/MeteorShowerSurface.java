package com.guochuang.mimedia.view.rain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * create by yao.cui at 2016/12/1
 */
public class MeteorShowerSurface extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{
    private final String TAG="MeteorShowerSurface";

    private Thread mDrawThread; // 用来绘制的线程
    private Thread mAddThread; // 用于添加红包的线程
    private boolean isGameOver = false;
    private SurfaceHolder mHolder;

    private int mHeight;//该surface的高度
    private int mWidth;//该surface的宽度

    private int mScore;//点中红包数量

    private int mRedCount = 0;//红包数量
    private int mDuration = 0;//红包雨时长

    private SpriteManager mSpriteManager;
    private Context mContext;
    //倒计时
    private CountDownTimer mCountDownTimer;
    private GameListener mGameListener;
    private int mAddPacketInterval;
    private boolean needPaint=true;
    private int bgCircleRadius;
    private int startX=0;
    private int startY=0;
    private Bitmap circleBmp;
    int degree = 0;
    Timer timer = new Timer();
    int incream=0;
    public interface GameListener{
        /**
         * 开始之前调用
         */
        void preGame();

        /**
         * 开始种，倒计时循环调用
         */
        void inGameInterval();

        /**
         * 游戏完成即倒计时结束调用
         * @param score
         */
        void postGame(int score);
    }


    public MeteorShowerSurface(Context context) {
        super(context);
        init(context);
    }

    public MeteorShowerSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MeteorShowerSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void setParams(int duration,int redCount){
        mDuration=duration;
        mRedCount=redCount;
    }

    private void init(Context context){
        this.mContext = context;
        setZOrderOnTop(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mDrawThread = new Thread(new DrawThread());
        mAddThread = new Thread(new AddMeteorThread());
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setOnTouchListener(this);
        bgCircleRadius=CommonUtil.dip2px(context,70);
        circleBmp=BitmapFactory.decodeResource(getResources(),R.drawable.bg_rain_circle);
        circleBmp= scaleBmp(circleBmp, CommonUtil.dip2px(mContext,40),false);
        if (mGameListener != null){
            mGameListener.preGame();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = getMeasuredWidth();
        this.mHeight = getMeasuredHeight();
        mSpriteManager = SpriteManager.getInstance();
        mSpriteManager.init(mContext,mWidth,mHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mHolder=surfaceHolder;
        isGameOver = false;
        if (needPaint) {
            needPaint = false;
            startX=mWidth/2-bgCircleRadius;
            startY=mHeight/2-bgCircleRadius;
            setBackgroundColor(getResources().getColor(R.color.bg_rain));
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isGameOver = true;
        mSpriteManager.stop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    public void start(){
        Canvas canvas = mHolder.lockCanvas();
        handler.sendEmptyMessage(0);
        mHolder.unlockCanvasAndPost(canvas);
        timer.schedule(timerTask, 50, 40);
    }

    private void setCircle(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.WHITE);// 设置红色
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除,大家一看效果就明白了
        canvas.drawCircle(mWidth/2, mHeight/2, bgCircleRadius, p);// 小圆

        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.bg_rain_number);
        Bitmap number = scaleBmp(bmp1, CommonUtil.dip2px(mContext,50),true);
        canvas.drawBitmap(number,startX+CommonUtil.dip2px(mContext,45),startY+CommonUtil.dip2px(mContext,35),p);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.bg_rain_bottom);
        Bitmap bottom = scaleBmp(bmp, CommonUtil.dip2px(mContext,70),true);
        canvas.drawBitmap(bottom,startX+CommonUtil.dip2px(mContext,35),startY+CommonUtil.dip2px(mContext,70),p);

        Paint p2 = new Paint();
        p2.setColor(getResources().getColor(R.color.bg_rain));
        p2.setTextSize(CommonUtil.sp2px(mContext,35));
        p2.setTypeface(Typeface.DEFAULT_BOLD);
        p2.setAntiAlias(true);
        canvas.drawText(String.valueOf(5-degree/180),startX+CommonUtil.dip2px(mContext,60),startY+CommonUtil.dip2px(mContext,70),p2);
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (degree>=720){
//                        timer.cancel();
                    }
                    canvasRotate();
                    break;
                case 1:
                    if (incream>=bgCircleRadius){
                        timer.cancel();
                        timer=null;
                        timerTask=null;
                        setDuration(mDuration).setRedCount(mRedCount).startRain();
                    }
                    canvasTranslate();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void onResume(){
        if (timerTask!=null){
            if (timer==null){
                timer=new Timer();
                setTimeTask();
                timer.schedule(timerTask,50, 40);
            }
        }
    }
    public void onPause(){
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }
   TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            if (degree<720){
                degree=degree+9;
                handler.sendEmptyMessage(0);
            }else {
                incream=incream+bgCircleRadius/20;
                handler.sendEmptyMessage(1);
            }
        }
    };
    public void setTimeTask(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
                if (degree<720){
                    degree=degree+9;
                    handler.sendEmptyMessage(0);
                }else {
                    incream=incream+bgCircleRadius/20;
                    handler.sendEmptyMessage(1);
                }
            }
        };
    }

    private void canvasRotate(){
            Canvas canvas = mHolder.lockCanvas();
            if (canvas==null){
                return;
            }
            canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);//清除屏幕
            setCircle(canvas);
            Matrix matrix = new Matrix();
            matrix.setTranslate(startX-circleBmp.getWidth()/2,mHeight/2-circleBmp.getHeight()/2);
            matrix.postRotate(degree, mWidth/2 ,mHeight/2);
            try {
                canvas.drawBitmap(circleBmp, matrix, null);
            }catch (Exception e){
                e.printStackTrace();
            }
        Matrix matrix1 = new Matrix();
        matrix1.setTranslate(startX-circleBmp.getWidth()/2,mHeight/2-circleBmp.getHeight()/2);
        matrix1.postRotate(degree+180, mWidth/2 ,mHeight/2);
        canvas.drawBitmap(circleBmp, matrix1, null);
        mHolder.unlockCanvasAndPost(canvas);
    }
    private void canvasTranslate(){
        Canvas canvas = mHolder.lockCanvas();
        if (canvas==null){
            return;
        }
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);//清除屏幕
        setCircle(canvas);
        Matrix matrix = new Matrix();
        matrix.postTranslate(startX-circleBmp.getWidth()/2+incream,mHeight/2-circleBmp.getHeight()/2);
        canvas.drawBitmap(circleBmp, matrix, null);

        Matrix matrix1 = new Matrix();
        matrix1.postTranslate(startX-circleBmp.getWidth()/2+(bgCircleRadius*2-incream),mHeight/2-circleBmp.getHeight()/2);
        canvas.drawBitmap(circleBmp, matrix1, null);
        mHolder.unlockCanvasAndPost(canvas);
    }


    /**
     * 设置红包雨时长
     * @param duration 毫秒
     * @return
     */
    private MeteorShowerSurface setDuration(int duration){
        this.mDuration = duration;
        mCountDownTimer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long l) {
                mSpriteManager.updateTime((int)(l/1000));
                if (mGameListener != null){
                    mGameListener.inGameInterval();
                }
            }

            @Override
            public void onFinish() {
                mSpriteManager.updateTime(0);
                isGameOver = true;
                if (mGameListener != null){
                    mGameListener.postGame(mScore);
                }
            }
        };
        return this;
    }

    /**
     * 设置红包数量
     * @param count
     * @return
     */
    private MeteorShowerSurface setRedCount(int count){
        this.mRedCount = count;
        return this;
    }

    public void setmGameListener(GameListener listener){
        this.mGameListener = listener;
    }
    /**
     * 开始绘制线程
     */
    private void startRain(){
        if (mRedCount<=0){
            return;
        }
        mAddPacketInterval = mDuration / mRedCount;
        if (mDrawThread!= null){
            mDrawThread.start();
        }
        if (mAddThread!= null){
            mAddThread.start();
        }

        //开始倒计时
        mCountDownTimer.start();
    }


    private void clean(){
        mSpriteManager.cleanData();
    }

    private void recycle(){
        Log.i(TAG,"=====recycle");
        mSpriteManager.recycle();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                BaseSprite sprite = mSpriteManager.isContains(x,y);
                if (sprite != null){
                    mSpriteManager.addBoom((int)x,(int)y);
                    mScore++;
                    mSpriteManager.updateScore(mScore);
                    sprite.stop();
                }

                break;
        }
        return true;
    }

    class DrawThread implements Runnable {
        @Override
        public void run() {
            while(!isGameOver){
                clean();//清除滚出去的数据
                Canvas canvas = null;
                synchronized (mHolder){
                    canvas = mHolder.lockCanvas();

                    if (canvas== null){
                        isGameOver = true;
                        return;
                    }

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mSpriteManager.draw(canvas);

                    mHolder.unlockCanvasAndPost(canvas);

//                    try {
//                        Thread.sleep(5);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
                }
            }

            mSpriteManager.stop();
        }
    }
    class AddMeteorThread implements Runnable {
        @Override
        public void run() {
            while (!isGameOver){
                mSpriteManager.addMeteorSprite();
                try {
                    Thread.sleep(mAddPacketInterval);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
    protected Bitmap scaleBmp(Bitmap srcBmp, int targetWidth, boolean recycle){
        int height = targetWidth* srcBmp.getHeight()/srcBmp.getWidth() ;
        Bitmap newBmp = Bitmap.createScaledBitmap(srcBmp,targetWidth,height,false);
        if (recycle){
            srcBmp.recycle();
        }
        return newBmp;
    }
}
