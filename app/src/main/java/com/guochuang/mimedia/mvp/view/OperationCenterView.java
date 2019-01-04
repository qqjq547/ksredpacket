package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.RegionCoreHome;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface OperationCenterView {
    void setData(RegionCoreHome data);
    void setError(String msg);

}
