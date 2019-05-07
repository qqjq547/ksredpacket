package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.VersionMsg;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface WelcomeView {
    void setVersion(VersionMsg data);
    void setError(String msg);

}
