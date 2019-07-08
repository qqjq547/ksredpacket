package com.guochuang.mimedia.ui.activity.redbag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.view.CountDownView;
import com.sz.gcyh.KSHongBao.R;

import java.util.HashMap;

public class VideoPreviewActivity2 extends  Activity {

    private PlayerView player;
    private Context mContext;
    private View rootView;
    private CountDownView mCountDownView;
    String url;
    boolean gone = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_videopreview2, null);
        setContentView(rootView);

        gone = getIntent().getBooleanExtra(Constant.COUNTDOWN_GONE, gone);
        mCountDownView = findViewById(R.id.count_down_view);
        if(!gone) {

            mCountDownView.setVisibility(View.VISIBLE);
            mCountDownView.start();
        }


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
        final Bitmap bitmap = mmr.getFrameAtTime();
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);



        player = new PlayerView(this, rootView)
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideMenu(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
//                        findViewById(R.id.video_view).setBackground(new BitmapDrawable(getResources(), bitmap));
//                        Glide.with(mContext)
//                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                .placeholder(R.color.cl_default)
//                                .error(R.color.cl_error)
//                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(url)
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        //这里可以简单播放器点击返回键
                       onBackPressed();
                    }
                })
                .startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        Intent intent = getIntent();
        intent.putExtra(Constant.COUNTDOWNTIME,mCountDownView.getCurrentTime());
        setResult(RESULT_OK,intent);
        super.onBackPressed();





    }

}
