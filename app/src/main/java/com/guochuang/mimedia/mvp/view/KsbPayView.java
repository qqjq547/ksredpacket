package com.guochuang.mimedia.mvp.view;
import com.guochuang.mimedia.mvp.model.PaymentResult;


public interface KsbPayView {
    void setPayResult(PaymentResult data);
    void setError(String msg);
}
