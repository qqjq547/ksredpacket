package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Category;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface InfoView {
    void setData(List<Category> data);
    void setError(String msg);


}
