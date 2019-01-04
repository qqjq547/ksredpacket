package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.IncomeStatistics;
import com.guochuang.mimedia.mvp.model.DictionaryType;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface IncomeStatisticsView {
    void setData(IncomeStatistics data);
    void setType(List<DictionaryType> data);
    void setError(String msg);

}
