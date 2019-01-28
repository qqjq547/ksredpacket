package com.guochuang.mimedia.tools.ad;

public interface OnShowResultListener {
        void onShowSuccess();
        void onDismiss();
        void onFailed(String errMsg);
    }