package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.RedbagDetail;

public interface CheckQuestionView {
    void setData(LookVideoResult data);
    void setError(String msg);
}
