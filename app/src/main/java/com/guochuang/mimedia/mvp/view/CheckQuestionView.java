package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookVideoResult;

public interface CheckQuestionView {
    void setData(LookVideoResult data);
    void setError(String msg);
}
