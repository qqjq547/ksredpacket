package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.PayConfig;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface PurchaseView {
    void setConfig(PayConfig data);
    void setBuyCity(String data);
    void setUpgradeAgent(Order data);
    void setBuyHonyComb(Order data);
    void setSnatch(Order data);
    void setBuyNestAd(Order data);
    void setPayResult(CalValue data);
    void setError(String msg);

}
