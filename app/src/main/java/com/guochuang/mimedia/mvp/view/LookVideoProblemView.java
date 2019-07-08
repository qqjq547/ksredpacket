package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookSurevyResult;

public interface LookVideoProblemView {
    void setError(String message);

    void setData(LookSurevyResult data);
}
