package com.guochuang.mimedia.http.exception;


import com.guochuang.mimedia.tools.LogUtil;


public class ApiException extends Exception {
    private int statusCode = 0;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void printStackTrace() {
        LogUtil.e(statusCode+","+getMessage());
    }

}

