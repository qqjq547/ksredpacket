package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;

import java.util.List;

public interface MyQCDetailsView {
    void setError(String message);

    void setQCDetail(List<KsbRecord> data);

    void setSubject(List<DictionaryType> data);
}
