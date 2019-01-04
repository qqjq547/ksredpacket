package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.LuckyResult;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface LuckyView {
    void setData(LuckyResult data);
    void setError(String msg);
}
