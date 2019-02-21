package com.guochuang.mimedia.tools;

public class JniUtil {
    static {
        System.loadLibrary("JniUtil");
    }

    public static native String getSign();
}
