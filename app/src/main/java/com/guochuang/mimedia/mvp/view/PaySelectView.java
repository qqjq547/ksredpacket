package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.PayConfig;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface PaySelectView {
    void setData(CalValue data);
    void setConfig(PayConfig data);
    void setError(String msg);

}
