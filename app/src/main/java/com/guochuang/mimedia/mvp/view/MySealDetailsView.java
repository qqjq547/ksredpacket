package com.guochuang.mimedia.mvp.view;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.SealRecord;

import java.util.List;

public interface MySealDetailsView {
    void setData(List<SealRecord> data);
    void setSubject(List<DictionaryType> data);
    void setError(String msg);
}
