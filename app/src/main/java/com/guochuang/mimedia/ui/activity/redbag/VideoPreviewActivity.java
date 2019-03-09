package com.guochuang.mimedia.ui.activity.redbag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.mvp.model.VideoPlayerItemInfo;
import com.guochuang.mimedia.tools.MediaHelper;
import com.guochuang.mimedia.view.MyVideoPlayer;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPreviewActivity extends AppCompatActivity {
    String url = "file:///storage/emulated/0/qqmusic/mv/花姐 - 狂浪_x00290r17vr_1_0 [mqms].mp4";
    MyVideoPlayer videoPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_videopreview);
        videoPlayer = findViewById(R.id.video_player);

        new DefaultNavigationBar.Builder(this).setTitle("视频预览")
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


}
