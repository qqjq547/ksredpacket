package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.NestTemplate;

import java.util.List;

public interface BeeNestTempView {
    void setData(List<NestTemplate> data);
    void setDelete(Boolean data);
    void setError(String msg);
}
