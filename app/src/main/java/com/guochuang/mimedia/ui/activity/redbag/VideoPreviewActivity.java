package com.guochuang.mimedia.ui.activity.redbag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.guochuang.mimedia.base.BaseActivity;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.bumptech.glide.Glide;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.mvp.model.VideoPlayerItemInfo;
import com.guochuang.mimedia.tools.BitmapUtils;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.MediaHelper;
import com.guochuang.mimedia.view.MyVideoPlayer;
import com.sz.gcyh.KSHongBao.R;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPreviewActivity extends MvpActivity {

    @BindView(R.id.video_player)
    MyVideoPlayer videoPlayer;

    String url;
    int mWidth, mHeight;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_videopreview;
    }

    @Override
    public void initViewAndData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        url = getIntent().getStringExtra(Constant.VIDEO_PATH);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (!url.startsWith("http")) {
            //设置数据源为该文件对象指定的绝对路径
            mmr.setDataSource(url);

            url = "file:///" + url;
        } else {
            mmr.setDataSource(url, new HashMap<String, String>());
        }

        //获得视频第一帧的Bitmap对象
        Bitmap bitmap = mmr.getFrameAtTime();
        videoPlayer.setBackground(new BitmapDrawable(getResources(), bitmap));
        String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // 视频高度
        String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // 视频宽度

        new DefaultNavigationBar.Builder(this).setTitle(getResources().getString(R.string.video_preview))
                .setLeftClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MediaHelper.release();
                        finish();
                    }
                }).build();

        //传递给条目里面的MyVideoPlayer
        autoResize(Integer.valueOf(width),Integer.valueOf(height));
        VideoPlayerItemInfo info = new VideoPlayerItemInfo(0, url,mWidth,mHeight);
        videoPlayer.setPlayData(info);

        //设置为初始化状态
        videoPlayer.initViewDisplay();


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!MediaHelper.isPlay) {
            MediaHelper.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MediaHelper.isPlay) {
            MediaHelper.pause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaHelper.release();
    }


    private void autoResize(int width, int height) {
        Display currDisplay = getWindowManager().getDefaultDisplay();
        if (width > currDisplay.getWidth() || height > currDisplay.getHeight()) {

            //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放

            float wRatio = (float) width / (float) currDisplay.getWidth();

            float hRatio = (float) height / (float) currDisplay.getHeight();
            //选择大的一个进行缩放
            float ratio = Math.max(wRatio, hRatio);
            mWidth = (int) Math.ceil((float) width / ratio);
            mHeight = (int) Math.ceil((float) height / ratio);
            //设置surfaceView的布局参数
            videoPlayer.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mHeight));
        }
    }

}
