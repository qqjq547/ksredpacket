package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.model.KsbTrend;
import com.guochuang.mimedia.mvp.model.MyKsb;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface MyksbView {
    void setData(MyKsb data);
    void setKsbTrend(List<KsbTrend> data);
    void setError(String msg);


}
