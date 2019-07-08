package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.UserStatistics;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface UserStatisticsView {
    void setData(UserStatistics data);
    void setError(String msg);

}
