package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.Order;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface PurchaseView {
    void setBuyCity(String data);
    void setUpgradeAgent(Order data);
    void setBuyHonyComb(Order data);
    void setPayResult(CalValue data);
    void setError(String msg);

}
