package com.guochuang.mimedia.mvp.view;
import com.guochuang.mimedia.mvp.model.PaymentResult;
import com.guochuang.mimedia.mvp.model.PayeeUser;


public interface KsbPayView {
    void setPayResult(PaymentResult data);
    void setError(String msg);
}
