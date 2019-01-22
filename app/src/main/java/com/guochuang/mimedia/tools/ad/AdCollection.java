package com.guochuang.mimedia.tools.ad;

public class AdCollection {
    public interface onShowResultListener{
        void onShowSuccess();
        void onFailed(String errMsg);
    }
}
