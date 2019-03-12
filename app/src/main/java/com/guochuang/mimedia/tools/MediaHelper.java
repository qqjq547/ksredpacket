package com.guochuang.mimedia.tools;

import android.media.MediaPlayer;

/**
 * 多媒体的工具类
 */
public final class MediaHelper {
    public static boolean isPlay;

    private MediaHelper() {
    }

    private static MediaPlayer mPlayer;

    //获取多媒体对象
    public static MediaPlayer getInstance(){
        if(mPlayer == null){
            mPlayer = new MediaPlayer();
        }
        return  mPlayer;
    }

    //播放
    public static void play(){
        if(mPlayer != null){
            mPlayer.start();
            isPlay = true;
        }
    }

    //暂停
    public static void pause(){
        if(mPlayer != null){
            mPlayer.pause();
            isPlay = false;
        }
    }

    //释放
    public static void release(){
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
            isPlay = false;
        }
    }

}
