package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.Square;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface SquareView {
    void setData(Page<Square> data);
    void setError(String msg);
}
