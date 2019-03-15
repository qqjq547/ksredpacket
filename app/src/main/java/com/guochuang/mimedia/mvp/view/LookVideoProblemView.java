package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.mvp.model.LookVideoPBResult;

public interface LookVideoProblemView {
    void setError(String message);

    void setData(LookSurevyResult data);
}
