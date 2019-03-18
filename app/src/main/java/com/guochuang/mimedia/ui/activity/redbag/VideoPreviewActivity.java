package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.guochuang.mimedia.base.BaseActivity;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.mvp.model.VideoPlayerItemInfo;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.MediaHelper;
import com.guochuang.mimedia.view.MyVideoPlayer;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPreviewActivity extends MvpActivity {

    @BindView(R.id.video_player)
    MyVideoPlayer videoPlayer;

    String url;

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
        url = getIntent().getStringExtra(Constant.VIDEO_PATH);
        if (!url.startsWith("http")) {
            url = "file:///" + url;
        }
        new DefaultNavigationBar.Builder(this).setTitle(getResources().getString(R.string.video_preview))
                .setLeftClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MediaHelper.release();
                        finish();
                    }
                }).build();

        //传递给条目里面的MyVideoPlayer
        VideoPlayerItemInfo info = new VideoPlayerItemInfo(0, url);
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




}
