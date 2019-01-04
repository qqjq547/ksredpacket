package com.guochuang.mimedia.ui.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.SpannableString;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.LuckyResult;
import com.guochuang.mimedia.mvp.presenter.LuckyPresenter;
import com.guochuang.mimedia.mvp.view.LuckyView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LuckyActivity extends MvpActivity<LuckyPresenter> implements LuckyView,SensorEventListener {
    @BindView(R.id.tv_give_up)
    TextView tvGiveUp;
    @BindView(R.id.lin_shake)
    LinearLayout linShake;
    @BindView(R.id.tv_result_coin)
    TextView tvResultCoin;
    @BindView(R.id.tv_result_money)
    TextView tvResultMoney;
    @BindView(R.id.tv_know)
    TextView tvKnow;
    @BindView(R.id.lin_result)
    LinearLayout linResult;

    private static final int START_SHAKE = 0x1;
    private static final int AGAIN_SHAKE = 0x2;
    private static final int END_SHAKE = 0x3;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Vibrator mVibrator;//手机震动
    private SoundPool mSoundPool;//摇一摇音效

    //记录摇动状态
    private boolean isShake = false;
    private MyHandler mHandler;
    private int mWeiChatAudio;
    String redPacketUuid;
    @Override
    protected LuckyPresenter createPresenter() {
        return new LuckyPresenter(this);
    }

    @Override
    public int getLayout() {
        setOrientation(null);
        return R.layout.activity_lucky;
    }

    @Override
    public void initViewAndData() {
        redPacketUuid=getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        setStatusbar(R.color.bg_trans,false);
        mHandler = new MyHandler();

        //初始化SoundPool
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mWeiChatAudio = mSoundPool.load(this, R.raw.shake, 1);

        //获取Vibrator震动服务
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }
    @Override
    protected void onPause() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }

    @OnClick({R.id.tv_give_up, R.id.tv_know})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_give_up:
                finish();
                break;
            case R.id.tv_know:
                finish();
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                isShake = true;
                Thread thread = new Thread() {
                    @Override
                    public void run() {


                        super.run();
                        try {
                            //开始震动 发出提示音 展示动画效果
                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            //再来一次震动提示
                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            mHandler.obtainMessage(END_SHAKE).sendToTarget();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void setData(LuckyResult data) {
        closeLoadingDialog();
        mSensorManager.unregisterListener(this);
        mAccelerometerSensor=null;
        mSensorManager=null;
        if (data!=null) {
            linShake.setVisibility(View.GONE);
            linResult.setVisibility(View.VISIBLE);
            tvResultCoin.setText(String.format(getString(R.string.format_ksb),data.getCoin()));
            tvResultMoney.setText(String.format(getString(R.string.format_yuan),data.getMoney()));
            CommonUtil.playRing(LuckyActivity.this, R.raw.gold);
            sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_SHAKE:
                    //This method requires the caller to hold the permission VIBRATE.
                    mVibrator.vibrate(300);
                    //发出提示音
                    mSoundPool.play(mWeiChatAudio, 1, 1, 0, 0, 1);
                    break;
                case AGAIN_SHAKE:
                    mVibrator.vibrate(300);
                    break;
                case END_SHAKE:
                    //整体效果结束, 将震动设置为false
                    isShake = false;
                    // 展示上下两种图片回来的效果
                    showLoadingDialog(null);
                    mvpPresenter.openLuckyRedPacket(redPacketUuid);
                    break;
            }
        }
    }
}
