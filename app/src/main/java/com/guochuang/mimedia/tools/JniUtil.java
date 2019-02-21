package com.guochuang.mimedia.tools;

public class JniUtil {
    static {
        System.loadLibrary("JniHash");
    }

    public native String getSign();
}
