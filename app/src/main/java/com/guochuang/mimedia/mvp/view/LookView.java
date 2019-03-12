package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookSurveyResult;

public interface LookView {
    void setData(LookSurveyResult data);

    void setError(String message);
}
