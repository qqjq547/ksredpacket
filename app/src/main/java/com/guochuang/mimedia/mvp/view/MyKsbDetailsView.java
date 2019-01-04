package com.guochuang.mimedia.mvp.view;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;

import java.util.List;

public interface MyKsbDetailsView {
    void setData(List<KsbRecord> data);
    void setSubject(List<DictionaryType> data);
    void setError(String msg);
}
