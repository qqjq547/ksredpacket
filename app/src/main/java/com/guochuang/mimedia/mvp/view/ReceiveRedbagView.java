package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DrawStatistics;
import com.guochuang.mimedia.mvp.model.RedbagReceived;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface ReceiveRedbagView {
    void setData(List<RedbagReceived> data);
    void setStatistics(DrawStatistics data);
    void setError(String msg);

}
