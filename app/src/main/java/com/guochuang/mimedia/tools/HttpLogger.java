package com.guochuang.mimedia.tools;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                LogUtil.json(message);
            }
        }
    }