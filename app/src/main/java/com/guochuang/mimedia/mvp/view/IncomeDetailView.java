package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.IncomeDetail;
import com.guochuang.mimedia.mvp.model.DictionaryType;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface IncomeDetailView {
    void setData(Page<IncomeDetail> data);
    void setType(List<DictionaryType> data);
    void setError(String msg);
}
