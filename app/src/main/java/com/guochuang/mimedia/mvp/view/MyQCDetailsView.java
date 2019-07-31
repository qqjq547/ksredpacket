package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.QcRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;

import java.util.List;

public interface MyQCDetailsView {
    void setError(String message);

    void setQCDetail(List<QcRecord> data);

    void setSubject(List<DictionaryType> data);
}
