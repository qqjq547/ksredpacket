package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.model.RedbagTemp;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface TempView {
    void setData(List<RedbagTemp> data);
    void setDeleteTemp(Boolean data);
    void setError(String msg);


}
