package com.guochuang.mimedia.http.subscriber;

import rx.Subscriber;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CountDownSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
//        LogUtil.i("计时完成");
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        if (t instanceof Integer) {
            int sec = (Integer) t;
//            LogUtil.i("当前计时：" + CommonUtil.secToTime(sec));
        }
    }
}
