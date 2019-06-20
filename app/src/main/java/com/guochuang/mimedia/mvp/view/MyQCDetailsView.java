package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;

import java.util.List;

public interface MyQCDetailsView {
    void setError(String message);

    void setQCDetail(List<SealRecord> data);

    void setSubject(List<DictionaryType> data);
}